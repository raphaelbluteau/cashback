package com.github.raphaelbluteau.cashback.usecase.impl;

import com.github.raphaelbluteau.cashback.exceptions.data.ResourceNotFoundException;
import com.github.raphaelbluteau.cashback.gateway.repository.AlbumRepository;
import com.github.raphaelbluteau.cashback.gateway.repository.CashbackParametersRepository;
import com.github.raphaelbluteau.cashback.gateway.repository.SaleRepository;
import com.github.raphaelbluteau.cashback.gateway.repository.SoldItemRepository;
import com.github.raphaelbluteau.cashback.gateway.repository.entity.AlbumEntity;
import com.github.raphaelbluteau.cashback.gateway.repository.entity.CashbackParametersEntity;
import com.github.raphaelbluteau.cashback.gateway.repository.entity.SaleEntity;
import com.github.raphaelbluteau.cashback.gateway.repository.entity.SoldItemEntity;
import com.github.raphaelbluteau.cashback.usecase.SalesUseCase;
import com.github.raphaelbluteau.cashback.usecase.converter.SaleConverter;
import com.github.raphaelbluteau.cashback.usecase.data.request.AlbumRequest;
import com.github.raphaelbluteau.cashback.usecase.data.response.Sale;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SalesUseCaseImpl implements SalesUseCase {

    private final AlbumRepository albumRepository;
    private final CashbackParametersRepository cashbackParametersRepository;
    private final SaleRepository saleRepository;
    private final SoldItemRepository soldItemRepository;
    private final SaleConverter saleConverter;
    private static final String ALBUM_NOT_FOUND_MESSAGE = "Album for id %s not found";
    private static final String ORDER_NOT_FOUND_MESSAGE = "Order for id %s not found";


    @Override
    public Sale sale(List<AlbumRequest> albumsRequest) throws ResourceNotFoundException {

        List<AlbumEntity> albums = new ArrayList<>();
        for (AlbumRequest albumRequest : albumsRequest) {
            Optional<AlbumEntity> optionalAlbumEntity = albumRepository.findById(albumRequest.getId());
            albums.add(optionalAlbumEntity.orElseThrow(() ->
                    new ResourceNotFoundException(String.format(ALBUM_NOT_FOUND_MESSAGE, albumRequest.getId()))));
        }

        DayOfWeek dayOfWeek = LocalDateTime.now().getDayOfWeek();
        SaleEntity sale = new SaleEntity();
        List<SoldItemEntity> soldItems = new ArrayList<>();
        albums.forEach(album -> {
            CashbackParametersEntity cashbackParameters = cashbackParametersRepository
                    .findByDayOfWeekAndGenre(dayOfWeek, album.getGenre());
            BigDecimal cashback = (cashbackParameters.getPercentage()
                    .divide(BigDecimal.valueOf(100), RoundingMode.UNNECESSARY))
                    .multiply(album.getPrice());
            soldItems.add(SoldItemEntity.builder()
                    .album(album)
                    .cashback(cashback.setScale(2, RoundingMode.CEILING))
                    .build());
        });

        soldItemRepository.saveAll(soldItems);

        sale.setItems(soldItems);

        sale.setCashback(sale.getItems().stream()
                .map(SoldItemEntity::getCashback)
                .reduce(BigDecimal.ZERO, BigDecimal::add));

        return saleConverter.toUseCase(saleRepository.save(sale));
    }

    @Override
    public Page<Sale> findByPeriod(Pageable pageable, LocalDateTime begin, LocalDateTime end) {

        return saleConverter.toUseCase(saleRepository.findByCreatedAtGreaterThanEqualAndCreatedAtLessThanEqualOrderByCreatedAtDesc(begin, end, pageable));
    }

    @Override
    public Sale findById(Long id) throws ResourceNotFoundException {

        Optional<SaleEntity> optionalSaleEntity = saleRepository.findById(id);

        return saleConverter.toUseCase(optionalSaleEntity.orElseThrow(() ->
                new ResourceNotFoundException(String.format(ORDER_NOT_FOUND_MESSAGE, String.valueOf(id)))));
    }
}
