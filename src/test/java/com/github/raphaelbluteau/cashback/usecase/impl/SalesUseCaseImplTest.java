package com.github.raphaelbluteau.cashback.usecase.impl;

import com.github.raphaelbluteau.cashback.converter.*;
import com.github.raphaelbluteau.cashback.converter.impl.*;
import com.github.raphaelbluteau.cashback.enums.GenreEnum;
import com.github.raphaelbluteau.cashback.exceptions.data.ResourceNotFoundException;
import com.github.raphaelbluteau.cashback.service.AlbumService;
import com.github.raphaelbluteau.cashback.service.CashbackParametersService;
import com.github.raphaelbluteau.cashback.service.SaleService;
import com.github.raphaelbluteau.cashback.service.SoldItemService;
import com.github.raphaelbluteau.cashback.service.data.Album;
import com.github.raphaelbluteau.cashback.service.data.CashbackParameters;
import com.github.raphaelbluteau.cashback.service.data.Sale;
import com.github.raphaelbluteau.cashback.service.data.SoldItem;
import com.github.raphaelbluteau.cashback.usecase.SalesUseCase;
import com.github.raphaelbluteau.cashback.usecase.data.request.AlbumRequest;
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

    @MockBean
    private SaleService saleService;
    @MockBean
    private SoldItemService soldItemService;
    @MockBean
    private AlbumService albumService;
    @MockBean
    private CashbackParametersService cashbackParametersService;
    private CashbackParametersConverter cashbackParametersConverter;
    private SoldItemConverter soldItemConverter;
    private AlbumConverter albumConverter;
    private ArtistConverter artistConverter;
    private SalesUseCase salesUseCase;
    private Album album;
    private CashbackParameters cashbackParameters;
    private Sale sale;

    @Before
    public void setUp() {

        artistConverter = new ArtistConverterImpl();
        AlbumConverter albumConverter = new AlbumConverterImpl(artistConverter);
        SoldItemConverter soldItemConverter = new SoldItemConverterImpl(albumConverter);
        SaleConverter saleConverter = new SaleConverterImpl(soldItemConverter);
        cashbackParametersConverter = new CashbackParametersConverterImpl();
        salesUseCase = new SalesUseCaseImpl(albumService, soldItemService, saleService, cashbackParametersService,
                cashbackParametersConverter, soldItemConverter, albumConverter, saleConverter);

        album = Album.builder()
                .id("1")
                .name("Album Lorem")
                .price(BigDecimal.TEN)
                .genre(GenreEnum.ROCK)
                .build();

        cashbackParameters = CashbackParameters.builder()
                .dayOfWeek(LocalDateTime.now().getDayOfWeek())
                .genre(GenreEnum.ROCK)
                .id(50L)
                .percentage(BigDecimal.valueOf(20))
                .build();

        sale = Sale.builder()
                .id(10L)
                .cashback(BigDecimal.valueOf(20))
                .items(Collections.singletonList(SoldItem.builder()
                        .id(5L)
                        .album(album)
                        .cashback(BigDecimal.valueOf(20))
                        .build()))
                .createdAt(LocalDateTime.of(2019, 1, 31, 12, 0))
                .build();
    }

    @Test
    public void testSale() throws ResourceNotFoundException {

        given(albumService.findById(anyLong())).willReturn(Optional.of(album));
        given(cashbackParametersService.findByDayOfWeekAndGenre(any(DayOfWeek.class), any(GenreEnum.class)))
                .willReturn(cashbackParameters);
        given(saleService.makeSale(any(Sale.class))).willReturn(sale);

        List<AlbumRequest> albumsRequest = Collections.singletonList(AlbumRequest.builder()
                .id(Long.valueOf(album.getId()))
                .name(album.getName())
                .price(BigDecimal.valueOf(100))
                .build());

        com.github.raphaelbluteau.cashback.usecase.data.response.Sale result = salesUseCase.sale(albumsRequest);

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getId()).isEqualTo(10);
        Assertions.assertThat(result.getCashback()).isLessThanOrEqualTo(BigDecimal.valueOf(20));
        Assertions.assertThat(result.getCreatedAt()).isEqualTo(LocalDateTime.of(2019, 1, 31, 12, 0));
        Assertions.assertThat(result.getItems()).isNotEmpty();
        com.github.raphaelbluteau.cashback.usecase.data.response.SoldItem soldItem =
                result.getItems().iterator().next();
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

        Page<Sale> pageResult = new PageImpl<>(Collections.singletonList(sale));

        given(saleService.findByPeriod(any(Pageable.class), any(LocalDateTime.class), any(LocalDateTime.class)))
                .willReturn(pageResult);

        LocalDateTime begin = LocalDateTime.of(2019, 1, 31, 12, 0);
        LocalDateTime end = LocalDateTime.now();
        Page<com.github.raphaelbluteau.cashback.usecase.data.response.Sale> result =
                salesUseCase.findByPeriod(Pageable.unpaged(), begin, end);

        Assertions.assertThat(result).isNotNull();
        Optional<com.github.raphaelbluteau.cashback.usecase.data.response.Sale> optionalSale =
                result.get().findFirst();
        Assertions.assertThat(optionalSale.isPresent()).isTrue();
    }

    @Test
    public void testFindById() throws ResourceNotFoundException {

        given(saleService.findById(anyLong())).willReturn(sale);

        com.github.raphaelbluteau.cashback.usecase.data.response.Sale result =
                salesUseCase.findById(1L);
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getId()).isEqualTo(10);
        Assertions.assertThat(result.getCashback()).isLessThanOrEqualTo(BigDecimal.valueOf(20));
        Assertions.assertThat(result.getCreatedAt()).isEqualTo(LocalDateTime.of(2019, 1, 31, 12, 0));
        Assertions.assertThat(result.getItems()).isNotEmpty();
        com.github.raphaelbluteau.cashback.usecase.data.response.SoldItem soldItem =
                result.getItems().iterator().next();
        Assertions.assertThat(soldItem).isNotNull();
        Assertions.assertThat(soldItem.getId()).isEqualTo(5);
        Assertions.assertThat(soldItem.getCashback()).isLessThanOrEqualTo(BigDecimal.valueOf(20));
        Assertions.assertThat(soldItem.getAlbum()).isNotNull();
        Assertions.assertThat(soldItem.getAlbum().getGenre()).isEqualTo(GenreEnum.ROCK);
        Assertions.assertThat(soldItem.getAlbum().getName()).isEqualToIgnoringCase("Album Lorem");
        Assertions.assertThat(soldItem.getAlbum().getId()).isEqualTo("1");
    }
}