package com.github.raphaelbluteau.cashback.usecase.converter.impl;

import com.github.raphaelbluteau.cashback.converter.AlbumConverter;
import com.github.raphaelbluteau.cashback.converter.SoldItemConverter;
import com.github.raphaelbluteau.cashback.converter.impl.SoldItemConverterImpl;
import com.github.raphaelbluteau.cashback.enums.GenreEnum;
import com.github.raphaelbluteau.cashback.gateway.repository.entity.AlbumEntity;
import com.github.raphaelbluteau.cashback.gateway.repository.entity.SoldItemEntity;
import com.github.raphaelbluteau.cashback.usecase.data.response.Album;
import com.github.raphaelbluteau.cashback.usecase.data.response.SoldItem;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
public class SoldItemConverterImplTest {

    private SoldItemConverter soldItemConverter;

    @MockBean
    private AlbumConverter albumConverter;

    private SoldItemEntity soldItemEntity;

    @Before
    public void setUp() {

        soldItemConverter = new SoldItemConverterImpl(albumConverter);
        soldItemEntity = SoldItemEntity.builder()
                .id(13L)
                .cashback(BigDecimal.ONE)
                .album(AlbumEntity.builder()
                        .id(5L)
                        .price(BigDecimal.TEN)
                        .genre(GenreEnum.CLASSIC)
                        .name("Album Ipsum")
                        .build())
                .build();
    }

    @Test
    public void toUseCase() {

        given(albumConverter.toUseCase(any(AlbumEntity.class)))
                .willReturn(Album.builder()
                        .id("99")
                        .price(BigDecimal.TEN)
                        .name("Album Ipsum")
                        .genre(GenreEnum.CLASSIC)
                        .build());

        List<SoldItem> result = soldItemConverter.toUseCase(Collections.singletonList(soldItemEntity));

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).isNotEmpty();
        SoldItem soldItem = result.iterator().next();
        Assertions.assertThat(soldItem).isNotNull();
        Assertions.assertThat(soldItem.getId()).isEqualTo(13);
        Assertions.assertThat(soldItem.getCashback()).isEqualByComparingTo(BigDecimal.ONE);
        Assertions.assertThat(soldItem.getAlbum()).isNotNull();
        Assertions.assertThat(soldItem.getAlbum().getId()).isEqualTo("99");
        Assertions.assertThat(soldItem.getAlbum().getName()).isEqualTo("Album Ipsum");
        Assertions.assertThat(soldItem.getAlbum().getGenre()).isEqualTo(GenreEnum.CLASSIC);
    }

    @Test
    public void testToUseCaseEmptyList() {

        Assertions.assertThat(soldItemConverter.toUseCase(Collections.emptyList())).isEmpty();
    }

    @Test
    public void testToUseCaseNullItem() {

        Assertions.assertThat(soldItemConverter.toUseCase(Collections.singletonList(null)).iterator().next()).isNull();
    }
}