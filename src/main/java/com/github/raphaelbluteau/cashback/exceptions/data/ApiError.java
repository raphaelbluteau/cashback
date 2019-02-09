package com.github.raphaelbluteau.cashback.exceptions.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiError {

    private String status;

    private String message;

    private String developerMessage;
}
