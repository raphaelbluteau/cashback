package com.github.raphaelbluteau.cashback.usecase.converter.impl;

import com.github.raphaelbluteau.cashback.converter.SaleConverter;
import com.github.raphaelbluteau.cashback.converter.SoldItemConverter;
import com.github.raphaelbluteau.cashback.converter.impl.SaleConverterImpl;
import com.github.raphaelbluteau.cashback.enums.GenreEnum;
import com.github.raphaelbluteau.cashback.gateway.repository.entity.AlbumEntity;
import com.github.raphaelbluteau.cashback.gateway.repository.entity.SaleEntity;
import com.github.raphaelbluteau.cashback.gateway.repository.entity.SoldItemEntity;
import com.github.raphaelbluteau.cashback.usecase.data.response.Album;
import com.github.raphaelbluteau.cashback.usecase.data.response.Sale;
import com.github.raphaelbluteau.cashback.usecase.data.response.SoldItem;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
public class SaleConverterImplTest {

    private SaleConverter saleConverter;

    @MockBean
    private SoldItemConverter soldItemConverter;

    private SaleEntity saleEntity;

    @Before
    public void setUp() {

        saleConverter = new SaleConverterImpl(soldItemConverter);
        saleEntity = getMockedSaleEntity();

    }

    @Test
    public void testToUseCase() {

        given(soldItemConverter.toUseCase(anyList())).willReturn(getMockedSoldItems());

        Sale result = saleConverter.toUseCase(saleEntity);

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getId()).isEqualTo(2);
        Assertions.assertThat(result.getCreatedAt()).isEqualTo(LocalDateTime.of(2019, 1, 1, 12, 0));
        Assertions.assertThat(result.getCashback()).isEqualByComparingTo(BigDecimal.TEN);
        Assertions.assertThat(result.getItems()).isNotEmpty();

        SoldItem soldItem = result.getItems().iterator().next();
        Assertions.assertThat(soldItem).isNotNull();
        Assertions.assertThat(soldItem.getId()).isEqualTo(10);
        Assertions.assertThat(soldItem.getCashback()).isEqualByComparingTo(BigDecimal.TEN);
        Assertions.assertThat(soldItem.getAlbum()).isNotNull();
        Assertions.assertThat(soldItem.getAlbum().getId()).isEqualTo("20");
        Assertions.assertThat(soldItem.getAlbum().getGenre()).isEqualTo(GenreEnum.ROCK);
        Assertions.assertThat(soldItem.getAlbum().getName()).isEqualToIgnoringCase("Album Lorem");

    }

    @Test
    public void testToUseCaseNull() {

        Assertions.assertThat(saleConverter.toUseCase((SaleEntity) null)).isNull();
    }

    @Test
    public void testToUseCasePage() {

        given(soldItemConverter.toUseCase(anyList())).willReturn(getMockedSoldItems());

        Page<SaleEntity> saleEntityPage = new PageImpl<>(Collections.singletonList(saleEntity));

        Page<Sale> result = saleConverter.toUseCase(saleEntityPage);

        Assertions.assertThat(result).isNotEmpty();
        Sale sale = result.iterator().next();
        Assertions.assertThat(sale).isNotNull();
        Assertions.assertThat(sale.getCashback()).isEqualByComparingTo(BigDecimal.TEN);
        Assertions.assertThat(sale.getCreatedAt()).isEqualTo(LocalDateTime.of(2019, 1, 1, 12, 0));
        Assertions.assertThat(sale.getId()).isEqualTo(2);
    }

    @Test
    public void testToUseCasePageEmpty() {

        Assertions.assertThat(saleConverter.toUseCase(Page.empty())).isEmpty();
    }

    private SaleEntity getMockedSaleEntity() {
        return SaleEntity.builder()
                .id(2L)
                .createdAt(LocalDateTime.of(2019, 1, 1, 12, 0))
                .cashback(BigDecimal.TEN)
                .items(Collections.singletonList(SoldItemEntity.builder()
                        .id(10L)
                        .cashback(BigDecimal.TEN)
                        .album(AlbumEntity.builder()
                                .id(20L)
                                .genre(GenreEnum.ROCK)
                                .price(BigDecimal.valueOf(100))
                                .name("Album Lorem")
                                .build())
                        .build()))
                .build();
    }

    private List<SoldItem> getMockedSoldItems() {

        return Collections.singletonList(SoldItem.builder()
                .id(10L)
                .cashback(BigDecimal.TEN)
                .album(Album.builder()
                        .price(BigDecimal.valueOf(100))
                        .id("20")
                        .genre(GenreEnum.ROCK)
                        .name("Album Lorem")
                        .build())
                .build());
    }
}