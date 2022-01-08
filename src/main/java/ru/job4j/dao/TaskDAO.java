package ru.job4j.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.model.Task;

public class TaskDAO {

    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure()
            .build();
    private final SessionFactory sessionFactory = new MetadataSources(registry)
            .buildMetadata()
            .buildSessionFactory();

    public Task addTask(Task task) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(task);
        session.getTransaction().commit();
        session.close();
        return task;
    }
}
