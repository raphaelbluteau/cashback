package com.github.raphaelbluteau.cashback.usecase.data.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Artist {

    private String id;
    private String name;
}
