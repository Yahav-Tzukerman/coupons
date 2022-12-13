package com.yahav.coupons.services;

import com.yahav.coupons.converters.CategoryEntityToCategoryModelConverter;
import com.yahav.coupons.converters.CategoryModelToCategoryEntityConverter;
import com.yahav.coupons.entities.CategoryEntity;
import com.yahav.coupons.enums.ErrorType;
import com.yahav.coupons.exceptions.ServerException;
import com.yahav.coupons.models.CategoryModel;
import com.yahav.coupons.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryModelToCategoryEntityConverter categoryModelToCategoryEntityConverter;
    private final CategoryEntityToCategoryModelConverter categoryEntityToCategoryModelConverter;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository
            , @Lazy CategoryModelToCategoryEntityConverter categoryModelToCategoryEntityConverter
            , @Lazy CategoryEntityToCategoryModelConverter categoryEntityToCategoryModelConverter) {
        this.categoryRepository = categoryRepository;
        this.categoryModelToCategoryEntityConverter = categoryModelToCategoryEntityConverter;
        this.categoryEntityToCategoryModelConverter = categoryEntityToCategoryModelConverter;
    }

    public CategoryModel addCategory(CategoryModel categoryModel) throws ServerException {
        if (categoryRepository.existsByName(categoryModel.getName().toUpperCase())) {
            throw new ServerException(ErrorType.CATEGORY_ALREADY_EXIST);
        }
        return saveCategory(categoryModel);
    }

    public CategoryModel updateCategory(CategoryModel categoryModel) throws ServerException {
        if (!categoryRepository.existsById(categoryModel.getId())) {
            throw new ServerException(ErrorType.CATEGORY_DOES_NOT_EXIST);
        }
        return saveCategory(categoryModel);
    }

    private CategoryModel saveCategory(CategoryModel categoryModel) throws ServerException {
        if (categoryModel.getName().length() < 2) {
            throw new ServerException(ErrorType.INVALID_FILED, "Category name is too short");
        }
        categoryModel.setName(categoryModel.getName().toUpperCase());
        CategoryEntity categoryEntity = categoryModelToCategoryEntityConverter.convert(categoryModel);
        if (categoryEntity == null) {
            throw new ServerException(ErrorType.GENERAL_ERROR);
        }
        categoryEntity = categoryRepository.save(categoryEntity);
        return categoryEntityToCategoryModelConverter.convert(categoryEntity);
    }

    public CategoryModel getCategory(long id) {
        return categoryRepository.getCategory(id);
    }

    public CategoryEntity getCategoryEntityByName(String name) {
        return categoryRepository.findByName(name);
    }

    public List<CategoryModel> getCategories() {
        return categoryRepository.findAll()
                                 .stream()
                                 .map(categoryEntityToCategoryModelConverter::convert)
                                 .collect(Collectors.toList());
    }

    public void removeCategory(long id) {
        categoryRepository.deleteById(id);
    }
}
