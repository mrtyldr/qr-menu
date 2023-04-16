package com.wusly.backendmenu.repository;

import com.wusly.backendmenu.domain.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {

}
