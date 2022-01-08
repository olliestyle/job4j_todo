package ru.job4j.service;

import ru.job4j.dao.TaskDAO;
import ru.job4j.model.Task;

public class TaskService {

    private final TaskDAO taskDAO = new TaskDAO();

    private TaskService() {

    }

    private static final class TaskServiceHolder {
        private static final TaskService TASK_SERVICE = new TaskService();
    }

    public static TaskService instOf() {
        return TaskServiceHolder.TASK_SERVICE;
    }

    public Task addTask(Task task) {
        return taskDAO.addTask(task);
    }
}
