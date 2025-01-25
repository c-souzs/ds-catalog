package com.souzs.ds_catalog.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tb_category")
@Getter @Setter @AllArgsConstructor
@NoArgsConstructor @Builder @EqualsAndHashCode
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String name;
}
