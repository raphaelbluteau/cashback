package com.github.raphaelbluteau.cashback.gateway.data.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class ArtistAlbumGatewayResponse {

    private String href;
    private List<ArtistAlbumGatewayItem> items;
    private Integer total;
}
