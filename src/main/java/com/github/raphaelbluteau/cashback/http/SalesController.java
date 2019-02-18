package com.github.raphaelbluteau.cashback.http;

import com.github.raphaelbluteau.cashback.exceptions.data.ResourceNotFoundException;
import com.github.raphaelbluteau.cashback.http.converter.SalesHttpResponseConverter;
import com.github.raphaelbluteau.cashback.http.data.request.AlbumHttpRequest;
import com.github.raphaelbluteau.cashback.http.data.response.SaleHttpResponse;
import com.github.raphaelbluteau.cashback.usecase.SalesUseCase;
import com.github.raphaelbluteau.cashback.usecase.converter.AlbumConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class SalesController {

    private final SalesUseCase salesUseCase;
    private final SalesHttpResponseConverter salesHttpResponseConverter;
    private final AlbumConverter albumConverter;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public SaleHttpResponse order(@RequestBody @Valid List<AlbumHttpRequest> request) throws ResourceNotFoundException {

        return salesHttpResponseConverter.toResponse(salesUseCase.sale(albumConverter.toUseCaseRequest(request)));
    }

    @GetMapping
    public Page<SaleHttpResponse> getOrders(Pageable pageable, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime begin,
                                            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {

        return salesHttpResponseConverter.toResponse(salesUseCase.findByPeriod(pageable, begin, end));
    }

    @GetMapping("/{id}")
    public SaleHttpResponse getOrder(@PathVariable Long id) throws ResourceNotFoundException {

        return salesHttpResponseConverter.toResponse(salesUseCase.findById(id));
    }

}
