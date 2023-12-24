package com.github.tommorowsolutions.rkpRestBackend.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "managerId",nullable = false)
    @JsonManagedReference
    private Manager manager;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clientId",nullable = false)
    @JsonManagedReference
    private Client client;
    @Column(nullable = false)
    private LocalDate dateOfSigning;
    //@Column(nullable = false)
    private LocalDate dateOfComplete;
    //@Column(nullable = false)
    private double price;
    @OneToMany(mappedBy = "order", orphanRemoval = true,fetch = FetchType.LAZY)
    @JsonBackReference
    private List<OrderDetail> orderDetails;
}
