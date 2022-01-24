package ru.job4j.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.model.Category;
import ru.job4j.model.Task;
import ru.job4j.service.CategoryService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

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

    private <T> T tx(final Function<Session, T> command) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            T rsl = command.apply(session);
            session.getTransaction().commit();
            return rsl;
        } catch (final Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    public Task addTask(Task task, List<Integer> catsIds) {
        tx(session ->  {
            List<Category> categories = session.createQuery("select c from Category c where c.id in :ids").setParameter("ids", catsIds).getResultList();
            task.setCategories(new HashSet<>(categories));
            return session.save(task);
        });
        return task;
    }

    public List<Task> getAllMissedTasks() {
        return tx(session -> session.createQuery("select distinct task from Task task join fetch task.categories where task.done = false").list());
    }

    public List<Task> getAllDoneTasks() {
        return tx(session -> session.createQuery("select distinct task from Task task join fetch task.categories where task.done = true", Task.class).getResultList());
    }

    public boolean changeTaskStatus(Integer id) {
        return tx(session -> session.createQuery("update Task t set t.done = true where t.id = :id")
                .setParameter("id", id)
                .executeUpdate() > 0);
    }
}
