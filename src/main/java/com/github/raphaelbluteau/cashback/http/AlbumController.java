package com.github.raphaelbluteau.cashback.http;

import com.github.raphaelbluteau.cashback.enums.GenreEnum;
import com.github.raphaelbluteau.cashback.exceptions.data.ResourceNotFoundException;
import com.github.raphaelbluteau.cashback.http.converter.AlbumHttpResponseConverter;
import com.github.raphaelbluteau.cashback.http.data.response.AlbumHttpResponse;
import com.github.raphaelbluteau.cashback.usecase.AlbumUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/albums")
@RequiredArgsConstructor
public class AlbumController {

    private final AlbumUseCase albumUseCase;
    private final AlbumHttpResponseConverter albumHttpResponseConverter;
    private final Environment env;

    @GetMapping
    public Page<AlbumHttpResponse> getAlbums(final @PageableDefault(size = 20, direction = Sort.Direction.ASC, sort = "name") Pageable pageable, @RequestParam GenreEnum genre) {

        return albumHttpResponseConverter.toResponse(albumUseCase.getAlbumsByGenre(genre, pageable));
    }

    @GetMapping("/{id}")
    public AlbumHttpResponse getAlbum(@PathVariable Long id) throws ResourceNotFoundException {

        return albumHttpResponseConverter.toResponse(albumUseCase.findById(id));
    }
}
