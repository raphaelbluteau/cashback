package com.github.raphaelbluteau.cashback.http;

import com.github.raphaelbluteau.cashback.http.converter.SalesHttpResponseConverter;
import com.github.raphaelbluteau.cashback.http.data.request.AlbumHttpRequest;
import com.github.raphaelbluteau.cashback.http.data.response.SaleHttpResponse;
import com.github.raphaelbluteau.cashback.usecase.SalesUseCase;
import com.github.raphaelbluteau.cashback.usecase.converter.AlbumConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class SalesController {

    private final SalesUseCase salesUseCase;
    private final SalesHttpResponseConverter salesHttpResponseConverter;
    private final AlbumConverter albumConverter;

    @PostMapping
    public SaleHttpResponse order(@RequestBody @Valid List<AlbumHttpRequest> request) {

        // TODO tratar exceções

        return salesHttpResponseConverter.toResponse(salesUseCase.sale(albumConverter.toUseCaseRequest(request)));
    }

    // TODO endpoint que consulta todas as vendas efetuadas de forma paginada, filtrando por datas inicial e final
    // TODO ordenar de forma descrescente pela data da venda

    // TODO endpoint que consulta uma venda por id
}
