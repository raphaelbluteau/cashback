package com.github.raphaelbluteau.cashback.usecase.converter.impl;

import com.github.raphaelbluteau.cashback.converter.AlbumConverter;
import com.github.raphaelbluteau.cashback.converter.ArtistConverter;
import com.github.raphaelbluteau.cashback.converter.impl.AlbumConverterImpl;
import com.github.raphaelbluteau.cashback.enums.GenreEnum;
import com.github.raphaelbluteau.cashback.gateway.data.response.ArtistAlbumGatewayItem;
import com.github.raphaelbluteau.cashback.gateway.repository.entity.AlbumEntity;
import com.github.raphaelbluteau.cashback.http.data.request.AlbumHttpRequest;
import com.github.raphaelbluteau.cashback.usecase.data.request.AlbumRequest;
import com.github.raphaelbluteau.cashback.usecase.data.response.Album;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

@RunWith(JUnit4.class)
public class AlbumConverterImplTest {

    private AlbumConverter albumConverter;
    @Mock
    private ArtistConverter artistConverter;

    @Before
    public void setUp() {

        albumConverter = new AlbumConverterImpl(artistConverter);
    }

    @Test
    public void toUseCaseList() {

        List<Album> result = albumConverter.toUseCase(getAlbumsGateway(), GenreEnum.POP);

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).isNotEmpty();
        Album album = result.iterator().next();
        Assertions.assertThat(album).isNotNull();
        Assertions.assertThat(album.getId()).isEqualToIgnoringCase("10");
        Assertions.assertThat(album.getName()).isEqualToIgnoringCase("Album Ipsum");
        Assertions.assertThat(album.getGenre()).isEqualTo(GenreEnum.POP);
        Assertions.assertThat(album.getPrice()).isNull();
    }

    @Test
    public void testToUseCaseListEmpty() {

        Assertions.assertThat(albumConverter.toUseCase(Collections.emptyList(), GenreEnum.POP)).isEmpty();
    }

    @Test
    public void testToUseCaseNullItem() {

        Assertions.assertThat(albumConverter.toUseCase(Collections.singletonList(null), GenreEnum.POP).iterator().next()).isNull();
    }

    @Test
    public void toUseCaseRequest() {

        List<AlbumHttpRequest> albumRequest = Collections.singletonList(AlbumHttpRequest.builder()
                .id(9L)
                .build());

        List<AlbumRequest> result = albumConverter.toUseCaseRequest(albumRequest);

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).isNotEmpty();
        AlbumRequest request = result.iterator().next();
        Assertions.assertThat(request).isNotNull();
        Assertions.assertThat(request.getId()).isEqualTo(9);
    }

    @Test
    public void toUseCaseRequestEmptyOrNull() {

        Assertions.assertThat(albumConverter.toUseCaseRequest(Collections.emptyList())).isEmpty();
        Assertions.assertThat(albumConverter.toUseCaseRequest(Collections.singletonList(null)).iterator().next()).isNull();
    }

    @Test
    public void toEntityList() {

        List<Album> albums = Collections.singletonList(Album.builder()
                .id("5")
                .name("Album Lorem")
                .genre(GenreEnum.POP)
                .price(BigDecimal.TEN)
                .build());

        List<AlbumEntity> result = albumConverter.toEntity(albums, GenreEnum.POP);

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).isNotEmpty();
        AlbumEntity album = result.iterator().next();
        Assertions.assertThat(album).isNotNull();
        Assertions.assertThat(album.getId()).isNull();
        Assertions.assertThat(album.getGenre()).isEqualTo(GenreEnum.POP);
        Assertions.assertThat(album.getName()).isEqualToIgnoringCase("Album Lorem");
        Assertions.assertThat(album.getPrice()).isEqualByComparingTo(BigDecimal.TEN);
    }

    @Test
    public void toEntityListEmpty() {

        Assertions.assertThat(albumConverter.toEntity(Collections.emptyList(), GenreEnum.ROCK)).isEmpty();
    }

    @Test
    public void toEntityListNullItem() {

        Assertions.assertThat(albumConverter.toEntity(Collections.singletonList(null), GenreEnum.ROCK).iterator().next()).isNull();
    }

    @Test
    public void testToUseCase() {

        AlbumEntity albumEntity = AlbumEntity.builder()
                .id(1L)
                .name("Album lorem")
                .price(BigDecimal.TEN)
                .genre(GenreEnum.ROCK)
                .build();

        Album result = albumConverter.toUseCase(albumEntity);
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getId()).isEqualTo("1");
        Assertions.assertThat(result.getName()).isEqualToIgnoringCase("Album lorem");
        Assertions.assertThat(result.getGenre()).isEqualTo(GenreEnum.ROCK);
        Assertions.assertThat(result.getPrice()).isEqualByComparingTo(BigDecimal.TEN);

    }

    @Test
    public void testToUseCaseNull() {

        Assertions.assertThat(albumConverter.toUseCase((AlbumEntity) null)).isNull();
    }

    @Test
    public void testToUseCasePage() {

        Page<AlbumEntity> pageAlbums = new PageImpl<>(Collections.singletonList(AlbumEntity.builder()
                .id(12L)
                .name("Album name")
                .price(BigDecimal.ONE)
                .genre(GenreEnum.MPB)
                .build()));

        Page<Album> result = albumConverter.toUseCase(pageAlbums);

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).isNotEmpty();

        Album album = result.iterator().next();

        Assertions.assertThat(album).isNotNull();
        Assertions.assertThat(album.getId()).isEqualToIgnoringCase("12");
    }

    @Test
    public void testToUseCasePageEmpty() {

        Assertions.assertThat(albumConverter.toUseCase(Page.empty())).isEmpty();
    }

    private List<ArtistAlbumGatewayItem> getAlbumsGateway() {

        return Collections.singletonList(ArtistAlbumGatewayItem.builder()
                .id("10")
                .name("Album Ipsum")
                .uri("uri")
                .type("album")
                .build());
    }
}