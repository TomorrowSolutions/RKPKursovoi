package com.github.tommorowsolutions.rkpRestBackend.dtos;

import com.github.tommorowsolutions.rkpRestBackend.models.OrderDetail;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class OrderDto {
    private long clientId;
    private long managerId;
    private LocalDate dateOfSigning;
}
