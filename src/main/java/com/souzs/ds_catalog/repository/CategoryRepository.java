package com.souzs.ds_catalog.repository;

import com.souzs.ds_catalog.domain.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
