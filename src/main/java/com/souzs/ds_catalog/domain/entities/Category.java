package com.souzs.ds_catalog.domain.entities;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tb_category")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NonNull
    private String name;
}
