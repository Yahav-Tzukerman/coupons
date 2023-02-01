package com.yahav.coupons.repositories;

import com.yahav.coupons.entities.CategoryEntity;
import com.yahav.coupons.models.CategoryModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

    @Query("SELECT new com.yahav.coupons.models.CategoryModel(c.id, c.name) " +
            "FROM CategoryEntity c WHERE c.id = :id")
    CategoryModel getCategory(@Param("id") long id);

    CategoryEntity findByName(@Param("name") String name);

    @Query("SELECT new com.yahav.coupons.models.CategoryModel(c.id, c.name) " +
            "FROM CategoryEntity c")
    List<CategoryModel> getCategories();

    boolean existsByName(String name);
}
