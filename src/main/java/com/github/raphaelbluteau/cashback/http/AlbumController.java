package com.github.raphaelbluteau.cashback.http;

import com.github.raphaelbluteau.cashback.usecase.AlbumUseCase;
import com.github.raphaelbluteau.cashback.usecase.data.response.Album;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/albums")
@RequiredArgsConstructor
public class AlbumController {

    private final AlbumUseCase albumUseCase;
    private final Environment env;

    @GetMapping
    public List<Album> getAlbums() throws Exception {

        // TODO filtrar albums por genero e ordenando de forma crescente pelo nome
        // TODO retornar com paginação
        // TODO convert albums

        return Collections.emptyList();
    }
}
