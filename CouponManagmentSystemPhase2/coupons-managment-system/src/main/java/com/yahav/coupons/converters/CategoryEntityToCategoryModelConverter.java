package com.yahav.coupons.converters;

import com.sun.istack.NotNull;
import com.yahav.coupons.entities.CategoryEntity;
import com.yahav.coupons.models.CategoryModel;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CategoryEntityToCategoryModelConverter implements Converter<CategoryEntity, CategoryModel> {

    @Override
    public CategoryModel convert(@NotNull CategoryEntity categoryEntity) {
        CategoryModel categoryModel = new CategoryModel();
        categoryModel.setId(categoryEntity.getId());
        categoryModel.setName(categoryEntity.getName());
        return categoryModel;
    }
}
