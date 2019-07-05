package com.github.raphaelbluteau.cashback.usecase.impl;

import com.github.raphaelbluteau.cashback.converter.AlbumConverter;
import com.github.raphaelbluteau.cashback.converter.CashbackParametersConverter;
import com.github.raphaelbluteau.cashback.converter.SaleConverter;
import com.github.raphaelbluteau.cashback.converter.SoldItemConverter;
import com.github.raphaelbluteau.cashback.exceptions.data.ResourceNotFoundException;
import com.github.raphaelbluteau.cashback.service.AlbumService;
import com.github.raphaelbluteau.cashback.service.CashbackParametersService;
import com.github.raphaelbluteau.cashback.service.SaleService;
import com.github.raphaelbluteau.cashback.service.SoldItemService;
import com.github.raphaelbluteau.cashback.service.data.Album;
import com.github.raphaelbluteau.cashback.usecase.SalesUseCase;
import com.github.raphaelbluteau.cashback.usecase.data.request.AlbumRequest;
import com.github.raphaelbluteau.cashback.usecase.data.response.CashbackParameters;
import com.github.raphaelbluteau.cashback.usecase.data.response.Sale;
import com.github.raphaelbluteau.cashback.usecase.data.response.SoldItem;
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

import static java.util.Objects.isNull;

@Component
@RequiredArgsConstructor
public class SalesUseCaseImpl implements SalesUseCase {

    private final AlbumService albumService;
    private final SoldItemService soldItemService;
    private final SaleService saleService;
    private final CashbackParametersService cashbackParametersService;
    private final CashbackParametersConverter cashbackParametersConverter;
    private final SoldItemConverter soldItemConverter;
    private final AlbumConverter albumConverter;
    private final SaleConverter saleConverter;
    private static final String ALBUM_NOT_FOUND_MESSAGE = "Album for id %s not found";
    private static final String ORDER_NOT_FOUND_MESSAGE = "Order for id %s not found";


    @Override
    public Sale sale(List<AlbumRequest> albumsRequest) throws ResourceNotFoundException {

        List<Album> albums = new ArrayList<>();
        for (AlbumRequest albumRequest : albumsRequest) {
            Optional<Album> optionalAlbumEntity = albumService.findById(albumRequest.getId());
            albums.add(optionalAlbumEntity.orElseThrow(() ->
                    new ResourceNotFoundException(String.format(ALBUM_NOT_FOUND_MESSAGE, albumRequest.getId()))));
        }

        DayOfWeek dayOfWeek = LocalDateTime.now().getDayOfWeek();
        Sale sale = new Sale();
        List<SoldItem> soldItems = new ArrayList<>();
        albums.forEach(album -> {
            CashbackParameters cashbackParameters = cashbackParametersConverter.toUseCase(cashbackParametersService.findByDayOfWeekAndGenre(
                    dayOfWeek, album.getGenre()));
            BigDecimal cashback = (cashbackParameters.getPercentage()
                    .divide(BigDecimal.valueOf(100), RoundingMode.FLOOR))
                    .multiply(album.getPrice());
            soldItems.add(SoldItem.builder()
                    .album(albumConverter.toUseCase(album))
                    .cashback(cashback.setScale(2, RoundingMode.CEILING))
                    .build());
        });

        soldItemService.saveAll(soldItemConverter.fromUseCase(soldItems));

        sale.setItems(soldItems);

        final BigDecimal[] cashback = {BigDecimal.ZERO};
        sale.getItems().forEach(item -> cashback[0] = cashback[0].add(item.getCashback()));
        sale.setCashback(cashback[0]);

        return saleConverter.toUseCase(saleService.makeSale(saleConverter.fromUseCase(sale)));
    }

    @Override
    public Page<Sale> findByPeriod(Pageable pageable, LocalDateTime begin, LocalDateTime end) {

        return saleConverter.toUseCasePage(saleService.findByPeriod(pageable, begin, end));
    }

    @Override
    public Sale findById(Long id) throws ResourceNotFoundException {

        Sale sale = saleConverter.toUseCase(saleService.findById(id));

        if (isNull(sale)) {
            new ResourceNotFoundException(String.format(ORDER_NOT_FOUND_MESSAGE, String.valueOf(id)));
        }

        return sale;
    }
}
