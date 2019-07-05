package com.github.raphaelbluteau.cashback.http.converter.impl;

import com.github.raphaelbluteau.cashback.converter.AlbumConverter;
import com.github.raphaelbluteau.cashback.converter.ArtistConverter;
import com.github.raphaelbluteau.cashback.converter.impl.AlbumConverterImpl;
import com.github.raphaelbluteau.cashback.enums.GenreEnum;
import com.github.raphaelbluteau.cashback.http.data.response.AlbumHttpResponse;
import com.github.raphaelbluteau.cashback.usecase.data.response.Album;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Optional;

@RunWith(JUnit4.class)
public class AlbumHttpResponseConverterImplTest {

    private Album album;
    @MockBean
    private ArtistConverter artistConverter;
    private AlbumConverter albumConverter;

    @Before
    public void setUp() {

        album = Album.builder()
                .id("10")
                .price(BigDecimal.TEN)
                .name("Album Lorem")
                .genre(GenreEnum.POP)
                .artists(Collections.emptyList())
                .build();

        albumConverter = new AlbumConverterImpl(artistConverter);
    }

    @Test
    public void testToPageResponse() {

        Page<Album> useCasePage = new PageImpl<>(Collections.singletonList(album));
        Page<AlbumHttpResponse> results = albumConverter.toResponse(useCasePage);

        Assertions.assertThat(results).isNotNull();
        Optional<AlbumHttpResponse> optionalAlbumResponse = results.get().findFirst();
        Assertions.assertThat(optionalAlbumResponse.isPresent()).isTrue();
        AlbumHttpResponse albumResponse = optionalAlbumResponse.orElse(null);
        Assertions.assertThat(albumResponse).isNotNull();
        Assertions.assertThat(albumResponse.getId()).isEqualTo(10);
        Assertions.assertThat(albumResponse.getName()).isEqualToIgnoringCase("Album Lorem");
        Assertions.assertThat(albumResponse.getGenre()).isEqualTo(GenreEnum.POP);
        Assertions.assertThat(albumResponse.getPrice()).isLessThanOrEqualTo(BigDecimal.TEN);

        Assertions.assertThat(albumConverter.toResponse(Page.empty())).isEmpty();
    }

    @Test
    public void testToResponse() {

        AlbumHttpResponse result = albumConverter.toResponse(album);

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getId()).isEqualTo(10);
        Assertions.assertThat(result.getName()).isEqualToIgnoringCase("Album Lorem");
        Assertions.assertThat(result.getGenre()).isEqualTo(GenreEnum.POP);
        Assertions.assertThat(result.getPrice()).isLessThanOrEqualTo(BigDecimal.TEN);

        Assertions.assertThat(albumConverter.toResponse((Album) null)).isNull();
    }
}