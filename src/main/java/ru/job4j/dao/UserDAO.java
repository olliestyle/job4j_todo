package ru.job4j.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.model.User;

public class UserDAO {

    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure()
            .build();
    private final SessionFactory sessionFactory = new MetadataSources(registry)
            .buildMetadata()
            .buildSessionFactory();

    private UserDAO() {

    }

    private final static class UserDAOHolder {
        private static final UserDAO USER_DAO = new UserDAO();
    }

    public static UserDAO instOf() {
        return UserDAOHolder.USER_DAO;
    }

    public User addUser(User user) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(user);
        session.getTransaction().commit();
        session.close();
        return user;
    }

    public User findUserByName(String name) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        User toFind = null;
        try {
            toFind = (User) session.createQuery("from User where name = :name")
                    .setParameter("name", name)
                    .getSingleResult();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return toFind;
    }
}
