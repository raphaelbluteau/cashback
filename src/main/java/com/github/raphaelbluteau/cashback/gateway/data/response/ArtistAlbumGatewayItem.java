package com.github.raphaelbluteau.cashback.gateway.data.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArtistAlbumGatewayItem {

    private String id;
    private String name;
    private String type;
    private String uri;
}
