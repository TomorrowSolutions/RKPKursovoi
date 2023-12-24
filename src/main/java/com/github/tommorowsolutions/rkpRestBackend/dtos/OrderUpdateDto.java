package com.github.tommorowsolutions.rkpRestBackend.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
public class OrderUpdateDto {
    private Long id;
    private Long managerId;
    private Long clientId;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate dateOfSigning;
    @DateTimeFormat (pattern="yyyy-MM-dd")
    private LocalDate dateOfComplete;
    private Double price;

}

