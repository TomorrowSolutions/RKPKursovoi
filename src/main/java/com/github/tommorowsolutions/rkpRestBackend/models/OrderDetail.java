package com.github.tommorowsolutions.rkpRestBackend.models;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Data;
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
    @ManyToOne
    @JoinColumn(name = "orderId",nullable = false)
    @JsonManagedReference
    private Order order;
    @ManyToOne
    @JoinColumn(name = "objectId", nullable = false)
    @JsonManagedReference
    private GuardedObject guardedObject;
    @ManyToOne
    @JoinColumn(name = "serviceId", nullable = false)
    @JsonManagedReference
    private Service service;
    @Column(nullable = false)
    private int quantity;
}
