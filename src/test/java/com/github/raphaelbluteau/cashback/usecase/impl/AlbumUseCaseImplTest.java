package com.github.raphaelbluteau.cashback.usecase.impl;

import com.github.raphaelbluteau.cashback.enums.GenreEnum;
import com.github.raphaelbluteau.cashback.exceptions.data.GatewayException;
import com.github.raphaelbluteau.cashback.exceptions.data.SpotifyException;
import com.github.raphaelbluteau.cashback.gateway.SpotifyGateway;
import com.github.raphaelbluteau.cashback.gateway.data.response.ArtistAlbumGatewayItem;
import com.github.raphaelbluteau.cashback.gateway.data.response.ArtistAlbumGatewayResponse;
import com.github.raphaelbluteau.cashback.gateway.data.response.ArtistGatewayItem;
import com.github.raphaelbluteau.cashback.gateway.data.response.ArtistGatewayResponse;
import com.github.raphaelbluteau.cashback.gateway.impl.SpotifyGatewayImpl;
import com.github.raphaelbluteau.cashback.service.AlbumService;
import com.github.raphaelbluteau.cashback.service.impl.AlbumServiceImpl;
import com.github.raphaelbluteau.cashback.usecase.AlbumUseCase;
import com.github.raphaelbluteau.cashback.usecase.converter.AlbumConverter;
import com.github.raphaelbluteau.cashback.usecase.converter.ArtistConverter;
import com.github.raphaelbluteau.cashback.usecase.converter.impl.AlbumConverterImpl;
import com.github.raphaelbluteau.cashback.usecase.data.response.Album;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class AlbumUseCaseImplTest {

    private AlbumUseCase albumUseCase;

    @Mock
    private AlbumService albumService;
    @Mock
    private SpotifyGateway spotifyGateway;
    @Mock
    private ArtistConverter artistConverter;

    @Before
    public void setUp() throws Exception {
        AlbumConverter albumConverter = new AlbumConverterImpl(artistConverter);
        albumService = Mockito.mock(AlbumServiceImpl.class);
        spotifyGateway = Mockito.mock(SpotifyGatewayImpl.class);
        albumUseCase = new AlbumUseCaseImpl(spotifyGateway, albumService, albumConverter);
    }

    @Test
    public void getAlbumsByGenreFromGateway() throws SpotifyException, GatewayException {

        Mockito.when(spotifyGateway.getAlbumsByArtist(anyString(), anyString(), anyInt()))
                .thenReturn(getAlbumsResponse());
        Mockito.when(spotifyGateway.getArtistByGenre(anyString(), any(GenreEnum.class), anyInt()))
                .thenReturn(getArtistsResponse());

        List<Album> results = albumUseCase.getAlbumsByGenre("accessToken", GenreEnum.POP, 2);

        Assertions.assertThat(results).isNotEmpty();
        Assertions.assertThat(results.size()).isEqualTo(1);
        Assertions.assertThat(results.get(0).getId()).isEqualToIgnoringCase("12");
        Assertions.assertThat(results.get(0).getGenre()).isEqualTo(GenreEnum.POP);
    }

    @Test
    public void getAlbumsByGenre() {

        Mockito.when(albumService.getAlbumsByGenre(any(GenreEnum.class), any(Pageable.class)))
                .thenReturn(getPage());

        Page<Album> results = albumUseCase.getAlbumsByGenre(GenreEnum.POP, Pageable.unpaged());
        Assertions.assertThat(results).isNotEmpty();
        Assertions.assertThat(results.getTotalElements()).isEqualTo(1);
        Album album = results.iterator().next();
        Assertions.assertThat(album.getId()).isEqualToIgnoringCase("1");
        Assertions.assertThat(album.getGenre()).isEqualTo(GenreEnum.POP);
    }

    @Test
    public void findById() {
    }

    private Page<com.github.raphaelbluteau.cashback.service.data.Album> getPage() {

        return new PageImpl<>(Collections.singletonList(com.github.raphaelbluteau.cashback.service.data.Album.builder()
                .id("1")
                .name("Lorem ipsum")
                .price(BigDecimal.TEN)
                .genre(GenreEnum.POP)
                .build()));
    }

    private ArtistAlbumGatewayResponse getAlbumsResponse() {

        return ArtistAlbumGatewayResponse.builder()
                .total(1)
                .href("http://something")
                .items(Collections.singletonList(ArtistAlbumGatewayItem.builder()
                        .id("12")
                        .name("Artist")
                        .type("artist")
                        .uri("uri1")
                        .build()))
                .build();
    }

    private ArtistGatewayResponse getArtistsResponse() {

        return ArtistGatewayResponse.builder()
                .items(Collections.singletonList(ArtistGatewayItem.builder()
                        .id("1")
                        .name("Lorem ipsum")
                        .uri("uri2")
                        .build()))
                .build();
    }
}