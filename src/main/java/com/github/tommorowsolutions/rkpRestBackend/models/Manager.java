package com.github.tommorowsolutions.rkpRestBackend.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.springframework.format.annotation.DateTimeFormat;
import org.hibernate.annotations.Cascade.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "managers")
public class Manager {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String surname;
    @Column(nullable = false)
    private String name;
    private String patronymic;
    @Column(nullable = false)
    private String education;
    @Column(nullable = false)
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate dateOfStart;
    @Column(nullable = false)
    @Size(min = 21, max = 21)
    private String accountNumber;
    @OneToMany(mappedBy = "manager", orphanRemoval = true,fetch = FetchType.LAZY)
    @JsonBackReference
    private List<Order> orders;

}
