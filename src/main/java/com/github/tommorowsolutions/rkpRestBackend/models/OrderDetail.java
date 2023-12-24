package com.github.tommorowsolutions.rkpRestBackend.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "order_details")
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orderId",nullable = false)
    @JsonManagedReference
    private Order order;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "objectId", nullable = false)
    @JsonManagedReference
    private GuardedObject guardedObject;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "serviceId", nullable = false)
    @JsonManagedReference
    private Service service;
    @Column(nullable = false)
    private int quantity;
}
