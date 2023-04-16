package com.wusly.backendmenu.service;

import com.wusly.backendmenu.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public boolean existsById(UUID id){
        return categoryRepository.existsById(id);
    }
}
