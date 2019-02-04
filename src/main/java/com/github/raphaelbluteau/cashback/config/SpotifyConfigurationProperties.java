package com.github.raphaelbluteau.cashback.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "spotify.config")
@Configuration("employeeProperties")
@Data
public class SpotifyConfigurationProperties {

    private String authUrl;
    private String clientId;
    private String clientSecret;
    private String grantType;
    private String searchUrl;
    private String searchType;
    private String artistsUrl;
}
