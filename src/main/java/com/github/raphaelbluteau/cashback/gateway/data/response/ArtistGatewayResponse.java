package com.github.raphaelbluteau.cashback.gateway.data.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArtistGatewayResponse {

    private List<ArtistGatewayItem> items;
}
