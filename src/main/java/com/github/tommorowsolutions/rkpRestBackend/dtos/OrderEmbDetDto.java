package com.github.tommorowsolutions.rkpRestBackend.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
public class OrderEmbDetDto {
    private long clientId;
    private long managerId;
    private LocalDate dateOfSigning;
    private Object listOfDetails;
}
