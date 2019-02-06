package com.github.raphaelbluteau.cashback.gateway.repository.entity;

import com.github.raphaelbluteau.cashback.enums.GenreEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Table(name = "albums")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class AlbumEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @NotNull
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    @NotNull
    private GenreEnum genre;

}
