package com.github.raphaelbluteau.cashback.exceptions.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SpotifyErrorWrapper {

    private SpotifyError error;

}
