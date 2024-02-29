package com.springboot.blog.service.impl;

import com.springboot.blog.entity.Category;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.CategoryDto;
import com.springboot.blog.repository.CategoryRepository;
import com.springboot.blog.service.CategoryService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;
    private ModelMapper modelMapper;

    @Override
    public CategoryDto addCategory(CategoryDto categoryDto) {
        Category savedCategory = categoryRepository.save(modelMapper.map(categoryDto, Category.class));
        return modelMapper.map(savedCategory, CategoryDto.class);
    }

    @Override
    public CategoryDto getCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "ID", categoryId));
        return modelMapper.map(category, CategoryDto.class);
    }

    @Override
    public List<CategoryDto> getCategories() {
        return categoryRepository.findAll().stream().map(category -> modelMapper.map(category, CategoryDto.class)).collect(Collectors.toList());
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Long id) {
        Category searchedCategory = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException ("Category", "ID", id));
        searchedCategory.setName(categoryDto.getName());
        searchedCategory.setDescription(categoryDto.getDescription());
        searchedCategory.setId(id);
        Category savedCategory = categoryRepository.save(searchedCategory);
        return modelMapper.map(savedCategory, CategoryDto.class);


    }

    @Override
    public void deleteCategory(Long id) {
        categoryRepository.delete(categoryRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Category", "ID", id)));
    }
}
