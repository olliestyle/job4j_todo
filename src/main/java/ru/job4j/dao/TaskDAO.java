package ru.job4j.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.model.Task;

import java.util.List;

public class TaskDAO {

    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure()
            .build();
    private final SessionFactory sessionFactory = new MetadataSources(registry)
            .buildMetadata()
            .buildSessionFactory();

    private TaskDAO() {

    }

    private static class TaskDAOHolder {
        private static final TaskDAO TASK_DAO = new TaskDAO();
    }

    public static TaskDAO instOf() {
        return TaskDAOHolder.TASK_DAO;
    }

    public Task addTask(Task task) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(task);
        session.getTransaction().commit();
        session.close();
        return task;
    }

    public List<Task> getAllMissedTasks() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<Task> allTasks = session.createQuery("select task from Task task where task.done = false", Task.class).getResultList();
        session.getTransaction().commit();
        session.close();
        return allTasks;
    }

    public List<Task> getAllDoneTasks() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<Task> allTasks = session.createQuery("select task from Task task where task.done = true", Task.class).getResultList();
        session.getTransaction().commit();
        session.close();
        return allTasks;
    }

    public boolean changeTaskStatus(Integer id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        int updated = session.createQuery("update Task t set t.done = true where t.id = :id")
                .setParameter("id", id)
                .executeUpdate();
        session.getTransaction().commit();
        session.close();
        return updated > 0;
    }
}
