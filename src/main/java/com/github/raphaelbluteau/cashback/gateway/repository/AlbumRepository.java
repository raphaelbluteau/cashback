package com.github.raphaelbluteau.cashback.gateway.repository;

import com.github.raphaelbluteau.cashback.enums.GenreEnum;
import com.github.raphaelbluteau.cashback.gateway.repository.entity.AlbumEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;


public interface AlbumRepository extends PagingAndSortingRepository<AlbumEntity, Long> {

    Page<AlbumEntity> findAllByGenreOrderByName(GenreEnum genre, Pageable pageable);
}
