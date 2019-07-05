package com.github.raphaelbluteau.cashback.http.converter.impl;

import com.github.raphaelbluteau.cashback.converter.AlbumConverter;
import com.github.raphaelbluteau.cashback.converter.SoldItemConverter;
import com.github.raphaelbluteau.cashback.converter.impl.SoldItemConverterImpl;
import com.github.raphaelbluteau.cashback.enums.GenreEnum;
import com.github.raphaelbluteau.cashback.http.data.response.AlbumHttpResponse;
import com.github.raphaelbluteau.cashback.http.data.response.SoldItemHttpResponse;
import com.github.raphaelbluteau.cashback.usecase.data.response.Album;
import com.github.raphaelbluteau.cashback.usecase.data.response.SoldItem;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;

@RunWith(SpringRunner.class)
public class SoldItemHttpResponseConverterImplTest {

    private SoldItemConverter soldItemConverter;
    @MockBean
    private AlbumConverter albumConverter;

    @Before
    public void setUp() {
        soldItemConverter = new SoldItemConverterImpl(albumConverter) {
        };
    }

    @Test
    public void testToResponse() {

        Album album = Album.builder()
                .id("5")
                .price(BigDecimal.valueOf(50))
                .genre(GenreEnum.CLASSIC)
                .artists(Collections.emptyList())
                .name("Album Ipsum")
                .build();

        List<SoldItem> soldItems = Collections.singletonList(SoldItem.builder()
                .id(10L)
                .cashback(BigDecimal.TEN)
                .album(album)
                .build());

        AlbumHttpResponse albumHttpResponse = AlbumHttpResponse.builder()
                .id(Long.valueOf(album.getId()))
                .price(album.getPrice())
                .genre(album.getGenre())
                .name(album.getName())
                .build();

        Mockito.when(albumConverter.toResponse(any(Album.class))).thenReturn(albumHttpResponse);

        List<SoldItemHttpResponse> result = soldItemConverter.toResponse(soldItems);

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).isNotEmpty();
        SoldItemHttpResponse soldItem = result.iterator().next();
        Assertions.assertThat(soldItem).isNotNull();
        Assertions.assertThat(soldItem.getId()).isEqualTo(10);
        Assertions.assertThat(soldItem.getCashback()).isEqualByComparingTo(BigDecimal.TEN);
        Assertions.assertThat(soldItem.getAlbum()).isNotNull();
        Assertions.assertThat(soldItem.getAlbum().getId()).isEqualTo(5L);
        Assertions.assertThat(soldItem.getAlbum().getPrice()).isEqualByComparingTo(BigDecimal.valueOf(50));
        Assertions.assertThat(soldItem.getAlbum().getGenre()).isEqualTo(GenreEnum.CLASSIC);
        Assertions.assertThat(soldItem.getAlbum().getName()).isEqualToIgnoringCase("Album Ipsum");

        Assertions.assertThat(soldItemConverter.toResponse(null)).isEmpty();
        Assertions.assertThat(soldItemConverter.toResponse(Collections.singletonList(null)).iterator().next()).isNull();
    }
}