package com.gdgguadalajara.pos.category.application;

import com.gdgguadalajara.pos.category.model.Category;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class DeleteCategory {
    
    public void run(Category category) {
        category.delete();
    }
}
