package com.wusly.backendmenu.controller.category;

import com.wusly.backendmenu.controller.model.Response;
import com.wusly.backendmenu.domain.category.AddCategoryCommand;
import com.wusly.backendmenu.domain.category.CategoryResponse;
import com.wusly.backendmenu.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/category")
@RequiredArgsConstructor
@CrossOrigin
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping("")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void addCategory(@RequestBody AddCategoryCommand command, Principal principal){
        categoryService.addCategory(command,principal.getName());
    }

    @GetMapping("")
    Response<List<CategoryResponse>> categories(Principal principal){
        return Response.of(categoryService.getCategories(principal.getName()));
    }
    @DeleteMapping("/{id}")
    void deleteCategory(@PathVariable UUID id, Principal principal){
        categoryService.delete(id,principal.getName());
    }
}
