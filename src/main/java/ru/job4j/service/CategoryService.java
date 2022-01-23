package ru.job4j.service;

import ru.job4j.dao.CategoryDAO;
import ru.job4j.model.Category;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

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

    public List<Category> findByIds(String[] ids) {
        List<Integer> intIds = new ArrayList<>();
        for (String id: ids) {
            intIds.add(Integer.parseInt(id));
        }
        return categoryDAO.findByIds(intIds);
    }
}
