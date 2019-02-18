package com.github.raphaelbluteau.cashback.http;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.raphaelbluteau.cashback.enums.GenreEnum;
import com.github.raphaelbluteau.cashback.http.converter.SalesHttpResponseConverter;
import com.github.raphaelbluteau.cashback.http.data.request.AlbumHttpRequest;
import com.github.raphaelbluteau.cashback.http.data.response.AlbumHttpResponse;
import com.github.raphaelbluteau.cashback.http.data.response.SaleHttpResponse;
import com.github.raphaelbluteau.cashback.http.data.response.SoldItemHttpResponse;
import com.github.raphaelbluteau.cashback.usecase.SalesUseCase;
import com.github.raphaelbluteau.cashback.usecase.converter.AlbumConverter;
import com.github.raphaelbluteau.cashback.usecase.data.response.Album;
import com.github.raphaelbluteau.cashback.usecase.data.response.Sale;
import com.github.raphaelbluteau.cashback.usecase.data.response.SoldItem;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(SalesController.class)
public class SalesControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    SalesUseCase salesUseCase;

    @MockBean
    AlbumConverter albumConverter;

    @MockBean
    SalesHttpResponseConverter salesHttpResponseConverter;

    private List<AlbumHttpRequest> albumsRequest;
    private Page<SaleHttpResponse> salesPageHttpResponse;
    private Page<Sale> salesPageUseCase;

    @Before
    public void setUp() throws Exception {

        albumsRequest = Collections.singletonList(AlbumHttpRequest.builder()
                .id(10L)
                .build());

        salesPageHttpResponse = new PageImpl<>(getSaleHttpResponses());
        salesPageUseCase = new PageImpl<>(getSalePageUseCase());

    }

    @Test
    public void testOrder() throws Exception {

        mockMvc.perform(post("/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(albumsRequest)))
                .andExpect(status().isCreated());
    }

    @Test
    public void testGetOrders() throws Exception {

        LocalDateTime begin = LocalDateTime.of(2019, 1, 1, 10, 0);
        LocalDateTime end = LocalDateTime.now();

        Mockito.when(salesUseCase.findByPeriod(any(Pageable.class), any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(salesPageUseCase);
        Mockito.when(salesHttpResponseConverter.toResponse(any(Page.class)))
                .thenReturn(salesPageHttpResponse);

        mockMvc.perform(get("/orders")
                .param("begin", begin.format(DateTimeFormatter.ISO_DATE_TIME))
                .param("end", end.format(DateTimeFormatter.ISO_DATE_TIME))).andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value("1"))
                .andExpect(jsonPath("$.content[0].createdAt").value("2019-01-01T12:00:00"))
                .andExpect(jsonPath("$.content[0].cashback").value("10"))
                .andExpect(jsonPath("$.content[0].items[0].id").value("10"))
                .andExpect(jsonPath("$.content[0].items[0].cashback").value("10"))
                .andExpect(jsonPath("$.content[0].items[0].album.id").value("30"))
                .andExpect(jsonPath("$.content[0].items[0].album.name").value("Album Lorem"))
                .andExpect(jsonPath("$.content[0].items[0].album.price").value("70"))
                .andExpect(jsonPath("$.content[0].items[0].album.genre").value("POP"))
                .andExpect(jsonPath("$.content[1].id").value("2"))
                .andExpect(jsonPath("$.content[1].cashback").value("20"))
                .andExpect(jsonPath("$.content[1].items[0].id").value("12"))
                .andExpect(jsonPath("$.content[1].createdAt").value("2019-01-31T12:00:00"))
                .andExpect(jsonPath("$.content[1].items[0].cashback").value("20"))
                .andExpect(jsonPath("$.content[1].items[0].album.id").value("40"))
                .andExpect(jsonPath("$.content[1].items[0].album.name").value("Album Ipsum"))
                .andExpect(jsonPath("$.content[1].items[0].album.price").value("100"))
                .andExpect(jsonPath("$.content[1].items[0].album.genre").value("ROCK"));
    }

    @Test
    public void testGetOrder() throws Exception {

        Mockito.when(salesUseCase.findById(anyLong())).thenReturn(getSale1());
        Mockito.when(salesHttpResponseConverter.toResponse(any(Sale.class)))
                .thenReturn(getSaleHttpResponse1());

        mockMvc.perform(get("/orders/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.createdAt").value("2019-01-01T12:00:00"))
                .andExpect(jsonPath("$.cashback").value("10"))
                .andExpect(jsonPath("$.items[0].id").value("10"))
                .andExpect(jsonPath("$.items[0].cashback").value("10"))
                .andExpect(jsonPath("$.items[0].album.id").value("30"))
                .andExpect(jsonPath("$.items[0].album.name").value("Album Lorem"))
                .andExpect(jsonPath("$.items[0].album.price").value("70"))
                .andExpect(jsonPath("$.items[0].album.genre").value("POP"));
    }

    private List<Sale> getSalePageUseCase() {

        return Arrays.asList(getSale1(), getSale2());
    }

    private Sale getSale1() {

        SoldItem soldItem1 = SoldItem.builder()
                .id(10L)
                .album(Album.builder()
                        .id("30")
                        .name("Album Lorem")
                        .genre(GenreEnum.POP)
                        .price(BigDecimal.valueOf(70))
                        .build())
                .cashback(BigDecimal.TEN)
                .build();

        return Sale.builder()
                .id(1L)
                .cashback(BigDecimal.TEN)
                .createdAt(LocalDateTime.of(2019, 1, 1, 12, 0))
                .items(Collections.singletonList(soldItem1))
                .build();
    }

    private Sale getSale2() {

        SoldItem soldItem2 = SoldItem.builder()
                .id(12L)
                .album(Album.builder()
                        .id("40")
                        .name("Album Ipsum")
                        .genre(GenreEnum.ROCK)
                        .price(BigDecimal.valueOf(100))
                        .build())
                .cashback(BigDecimal.valueOf(20))
                .build();

        return Sale.builder()
                .id(2L)
                .cashback(BigDecimal.valueOf(20))
                .createdAt(LocalDateTime.of(2019, 1, 31, 12, 0))
                .items(Collections.singletonList(soldItem2))
                .build();
    }

    private List<SaleHttpResponse> getSaleHttpResponses() {

        return Arrays.asList(getSaleHttpResponse1(), getSaleHttpResponse2());
    }

    private SaleHttpResponse getSaleHttpResponse1() {

        SoldItemHttpResponse soldItem1 = SoldItemHttpResponse.builder()
                .id(10L)
                .cashback(BigDecimal.TEN)
                .album(AlbumHttpResponse.builder()
                        .id(30L)
                        .name("Album Lorem")
                        .genre(GenreEnum.POP)
                        .price(BigDecimal.valueOf(70))
                        .build())
                .build();

        return SaleHttpResponse.builder()
                .id(1L)
                .cashback(BigDecimal.TEN)
                .createdAt(LocalDateTime.of(2019, 1, 1, 12, 0))
                .items(Collections.singletonList(soldItem1))
                .build();
    }

    private SaleHttpResponse getSaleHttpResponse2() {

        SoldItemHttpResponse soldItem2 = SoldItemHttpResponse.builder()
                .id(12L)
                .cashback(BigDecimal.valueOf(20))
                .album(AlbumHttpResponse.builder()
                        .id(40L)
                        .name("Album Ipsum")
                        .genre(GenreEnum.ROCK)
                        .price(BigDecimal.valueOf(100))
                        .build())
                .build();

        return SaleHttpResponse.builder()
                .id(2L)
                .cashback(BigDecimal.valueOf(20))
                .createdAt(LocalDateTime.of(2019, 1, 31, 12, 0))
                .items(Collections.singletonList(soldItem2))
                .build();
    }

}