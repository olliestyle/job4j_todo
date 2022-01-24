package ru.job4j.service;

import ru.job4j.dao.CategoryDAO;
import ru.job4j.model.Category;

import java.util.List;

public class CategoryService {
    private final CategoryDAO categoryDAO = CategoryDAO.instOf();

    private CategoryService() {

    }

    private static final class CategoryServiceHolder {
        private static final CategoryService CATEGORY_SERVICE = new CategoryService();
    }

    public static CategoryService instOf() {
        return CategoryServiceHolder.CATEGORY_SERVICE;
    }

    public List<Category> findAll() {
        return categoryDAO.findAll();
    }
}
