package ru.job4j.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.model.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryDAO {

    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure()
            .build();
    private final SessionFactory sessionFactory = new MetadataSources(registry)
            .buildMetadata()
            .buildSessionFactory();

    private CategoryDAO() {

    }

    private static class CategoryDAOHolder {
        private static final CategoryDAO CATEGORY_DAO = new CategoryDAO();
    }

    public static CategoryDAO instOf() {
        return CategoryDAOHolder.CATEGORY_DAO;
    }

    public List<Category> findAll() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<Category> categories = null;
        try {
            categories = session.createQuery("from Category").getResultList();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return categories;
    }
}
