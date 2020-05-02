package com.github.raphaelbluteau.cashback.gateway.data.response;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class SpotifyErrorResponse {

    private String error;

    @SerializedName("error_description")
    private String description;

}
