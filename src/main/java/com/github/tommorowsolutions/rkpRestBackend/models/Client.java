package com.github.tommorowsolutions.rkpRestBackend.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "clients")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String surname;
    @Column(nullable = false)
    private String name;
    private String patronymic;
    private String phoneNumber;
    @Column(nullable = false)
    private String typeOfPerson;
    @Column(nullable = false)
    private String address;
    @Column(nullable = false)
    @Size(min = 21, max = 21)
    private String accountNumber;
    @OneToMany(mappedBy = "client",orphanRemoval = true,fetch = FetchType.LAZY)
    @JsonBackReference
    private List<Order> orders;
}
