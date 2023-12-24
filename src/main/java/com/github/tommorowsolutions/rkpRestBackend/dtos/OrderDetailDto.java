package com.github.tommorowsolutions.rkpRestBackend.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class OrderDetailDto {
    private long orderId;
    private long serviceId;
    private long guardedObjectId;
    private int quantity;
}
