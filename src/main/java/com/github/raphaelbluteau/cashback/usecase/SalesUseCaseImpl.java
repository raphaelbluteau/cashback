package com.github.raphaelbluteau.cashback.usecase;

import com.github.raphaelbluteau.cashback.gateway.repository.entity.SaleEntity;
import com.github.raphaelbluteau.cashback.usecase.data.request.AlbumRequest;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SalesUseCaseImpl implements SalesUseCase {

    @Override
    public SaleEntity sale(List<AlbumRequest> albumsRequest) {

        // TODO localizar álbuns na base
        // TODO calcular cashback para cada álbum
        // TODO guardar cashback total
        // TODO armazenar na base de dados

        return null;
    }
}
