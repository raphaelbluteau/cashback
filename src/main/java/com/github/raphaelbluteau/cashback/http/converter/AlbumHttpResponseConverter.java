package com.github.raphaelbluteau.cashback.http.converter;

import com.github.raphaelbluteau.cashback.http.data.response.AlbumHttpResponse;
import com.github.raphaelbluteau.cashback.usecase.data.response.Album;
import org.springframework.data.domain.Page;

public interface AlbumHttpResponseConverter {

    Page<AlbumHttpResponse> toResponse(Page<Album> albums);

    AlbumHttpResponse toResponse(Album album);
}
