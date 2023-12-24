package com.github.tommorowsolutions.rkpRestBackend.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "guarded_objects")
public class GuardedObject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private String name;
    private String image;
    @Column(nullable = false)
    private String address;
    @OneToMany(mappedBy = "guardedObject", orphanRemoval = true,fetch = FetchType.LAZY)
    @JsonBackReference
    @JsonIgnore
    private List<OrderDetail> orderDetails;
}
