package com.github.raphaelbluteau.cashback.gateway.data.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArtistGatewayItem {

    private String id;
    private String name;
    private String uri;
}
