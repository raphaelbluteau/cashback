package com.github.raphaelbluteau.cashback.http.converter.impl;

import com.github.raphaelbluteau.cashback.converter.SaleConverter;
import com.github.raphaelbluteau.cashback.converter.SoldItemConverter;
import com.github.raphaelbluteau.cashback.converter.impl.SaleConverterImpl;
import com.github.raphaelbluteau.cashback.http.data.response.SaleHttpResponse;
import com.github.raphaelbluteau.cashback.usecase.data.response.Sale;
import com.github.raphaelbluteau.cashback.usecase.data.response.SoldItem;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

@RunWith(SpringRunner.class)
public class SalesHttpResponseConverterImplTest {

    private SaleConverter saleConverter;
    @MockBean
    private SoldItemConverter soldItemConverter;
    private Sale sale;

    @Before
    public void setUp() {

        sale = Sale.builder()
                .id(30L)
                .cashback(BigDecimal.TEN)
                .createdAt(LocalDateTime.of(2019, 1, 31, 12, 0))
                .items(Collections.singletonList(SoldItem.builder().build()))
                .build();

        saleConverter = new SaleConverterImpl(soldItemConverter);
    }

    @Test
    public void toResponse() {

        SaleHttpResponse result = saleConverter.toResponse(sale);

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getId()).isEqualTo(30);
        Assertions.assertThat(result.getCashback()).isEqualByComparingTo(BigDecimal.TEN);
        Assertions.assertThat(result.getCreatedAt()).isEqualTo(LocalDateTime.of(2019, 1, 31, 12, 0));

        Assertions.assertThat(saleConverter.toResponse((Sale) null)).isNull();

    }

    @Test
    public void toPageResponse() {

        Page<Sale> pageUseCase = new PageImpl<>(Collections.singletonList(sale));

        Page<SaleHttpResponse> result = saleConverter.toResponse(pageUseCase);

        Assertions.assertThat(result).isNotNull();
        Optional<SaleHttpResponse> optionalSaleResponse = result.get().findFirst();
        Assertions.assertThat(optionalSaleResponse.isPresent()).isTrue();
        SaleHttpResponse saleResponse = optionalSaleResponse.orElse(null);
        Assertions.assertThat(saleResponse).isNotNull();
        Assertions.assertThat(saleResponse.getId()).isEqualTo(30);
        Assertions.assertThat(saleResponse.getCashback()).isEqualByComparingTo(BigDecimal.TEN);
        Assertions.assertThat(saleResponse.getCreatedAt()).isEqualTo(LocalDateTime.of(2019, 1, 31, 12, 0));

        Assertions.assertThat(saleConverter.toResponse(Page.empty())).isEmpty();
    }
}