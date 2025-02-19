package com.souzs.ds_catalog.repository;

import com.souzs.ds_catalog.domain.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
