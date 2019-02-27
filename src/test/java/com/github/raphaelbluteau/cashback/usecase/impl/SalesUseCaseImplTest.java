package com.github.raphaelbluteau.cashback.usecase.impl;

import com.github.raphaelbluteau.cashback.enums.GenreEnum;
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
import com.github.raphaelbluteau.cashback.usecase.converter.AlbumConverter;
import com.github.raphaelbluteau.cashback.usecase.converter.ArtistConverter;
import com.github.raphaelbluteau.cashback.usecase.converter.SaleConverter;
import com.github.raphaelbluteau.cashback.usecase.converter.SoldItemConverter;
import com.github.raphaelbluteau.cashback.usecase.converter.impl.AlbumConverterImpl;
import com.github.raphaelbluteau.cashback.usecase.converter.impl.SaleConverterImpl;
import com.github.raphaelbluteau.cashback.usecase.converter.impl.SoldItemConverterImpl;
import com.github.raphaelbluteau.cashback.usecase.data.request.AlbumRequest;
import com.github.raphaelbluteau.cashback.usecase.data.response.Sale;
import com.github.raphaelbluteau.cashback.usecase.data.response.SoldItem;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
public class SalesUseCaseImplTest {

    private SalesUseCase salesUseCase;
    @MockBean
    private AlbumRepository albumRepository;
    @MockBean
    private ArtistConverter artistConverter;
    @MockBean
    private CashbackParametersRepository cashbackParametersRepository;
    @MockBean
    private SaleRepository saleRepository;
    @MockBean
    private SoldItemRepository soldItemRepository;
    private AlbumEntity albumEntity;
    private CashbackParametersEntity cashbackParameters;
    private SaleEntity saleEntity;

    @Before
    public void setUp() {

        AlbumConverter albumConverter = new AlbumConverterImpl(artistConverter);
        SoldItemConverter soldItemConverter = new SoldItemConverterImpl(albumConverter);
        SaleConverter saleConverter = new SaleConverterImpl(soldItemConverter);
        salesUseCase = new SalesUseCaseImpl(albumRepository, cashbackParametersRepository,
                saleRepository, soldItemRepository, saleConverter);

        albumEntity = AlbumEntity.builder()
                .id(1L)
                .name("Album Lorem")
                .price(BigDecimal.TEN)
                .genre(GenreEnum.ROCK)
                .build();

        cashbackParameters = CashbackParametersEntity.builder()
                .dayOfWeek(LocalDateTime.now().getDayOfWeek())
                .genre(GenreEnum.ROCK)
                .id(50L)
                .percentage(BigDecimal.valueOf(20))
                .build();

        saleEntity = SaleEntity.builder()
                .id(10L)
                .cashback(BigDecimal.valueOf(20))
                .items(Collections.singletonList(SoldItemEntity.builder()
                        .id(5L)
                        .album(albumEntity)
                        .cashback(BigDecimal.valueOf(20))
                        .build()))
                .createdAt(LocalDateTime.of(2019, 1, 31, 12, 0))
                .build();
    }

    @Test
    public void testSale() throws ResourceNotFoundException {

        given(albumRepository.findById(anyLong())).willReturn(Optional.of(albumEntity));
        given(cashbackParametersRepository.findByDayOfWeekAndGenre(any(DayOfWeek.class), any(GenreEnum.class)))
                .willReturn(cashbackParameters);
        given(saleRepository.save(any(SaleEntity.class))).willReturn(saleEntity);

        List<AlbumRequest> albumsRequest = Collections.singletonList(AlbumRequest.builder()
                .id(albumEntity.getId())
                .name(albumEntity.getName())
                .price(BigDecimal.valueOf(100))
                .build());

        Sale result = salesUseCase.sale(albumsRequest);

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getId()).isEqualTo(10);
        Assertions.assertThat(result.getCashback()).isLessThanOrEqualTo(BigDecimal.valueOf(20));
        Assertions.assertThat(result.getCreatedAt()).isEqualTo(LocalDateTime.of(2019, 1, 31, 12, 0));
        Assertions.assertThat(result.getItems()).isNotEmpty();
        SoldItem soldItem = result.getItems().iterator().next();
        Assertions.assertThat(soldItem).isNotNull();
        Assertions.assertThat(soldItem.getId()).isEqualTo(5);
        Assertions.assertThat(soldItem.getCashback()).isLessThanOrEqualTo(BigDecimal.valueOf(20));
        Assertions.assertThat(soldItem.getAlbum()).isNotNull();
        Assertions.assertThat(soldItem.getAlbum().getGenre()).isEqualTo(GenreEnum.ROCK);
        Assertions.assertThat(soldItem.getAlbum().getName()).isEqualToIgnoringCase("Album Lorem");
        Assertions.assertThat(soldItem.getAlbum().getId()).isEqualTo("1");
    }

    @Test
    public void testFindByPeriod() {

        Page<SaleEntity> pageResult = new PageImpl<>(Collections.singletonList(saleEntity));

        given(saleRepository.findByCreatedAtGreaterThanEqualAndCreatedAtLessThanEqualOrderByCreatedAtDesc(any(LocalDateTime.class), any(LocalDateTime.class), any(Pageable.class)))
                .willReturn(pageResult);

        LocalDateTime begin = LocalDateTime.of(2019, 1, 31, 12, 0);
        LocalDateTime end = LocalDateTime.now();
        Page<Sale> result = salesUseCase.findByPeriod(Pageable.unpaged(), begin, end);

        Assertions.assertThat(result).isNotNull();
        Optional<Sale> optionalSale = result.get().findFirst();
        Assertions.assertThat(optionalSale.isPresent()).isTrue();
    }

    @Test
    public void testFindById() throws ResourceNotFoundException {

        given(saleRepository.findById(anyLong())).willReturn(Optional.of(saleEntity));

        Sale result = salesUseCase.findById(1L);
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getId()).isEqualTo(10);
        Assertions.assertThat(result.getCashback()).isLessThanOrEqualTo(BigDecimal.valueOf(20));
        Assertions.assertThat(result.getCreatedAt()).isEqualTo(LocalDateTime.of(2019, 1, 31, 12, 0));
        Assertions.assertThat(result.getItems()).isNotEmpty();
        SoldItem soldItem = result.getItems().iterator().next();
        Assertions.assertThat(soldItem).isNotNull();
        Assertions.assertThat(soldItem.getId()).isEqualTo(5);
        Assertions.assertThat(soldItem.getCashback()).isLessThanOrEqualTo(BigDecimal.valueOf(20));
        Assertions.assertThat(soldItem.getAlbum()).isNotNull();
        Assertions.assertThat(soldItem.getAlbum().getGenre()).isEqualTo(GenreEnum.ROCK);
        Assertions.assertThat(soldItem.getAlbum().getName()).isEqualToIgnoringCase("Album Lorem");
        Assertions.assertThat(soldItem.getAlbum().getId()).isEqualTo("1");
    }
}