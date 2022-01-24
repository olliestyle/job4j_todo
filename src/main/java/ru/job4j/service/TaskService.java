package ru.job4j.service;

import ru.job4j.dao.TaskDAO;
import ru.job4j.model.Task;

import java.util.ArrayList;
import java.util.List;

public class TaskService {

    private final TaskDAO taskDAO = TaskDAO.instOf();

    private TaskService() {

    }

    private static final class TaskServiceHolder {
        private static final TaskService TASK_SERVICE = new TaskService();
    }

    public static TaskService instOf() {
        return TaskServiceHolder.TASK_SERVICE;
    }

    public Task addTask(Task task, String[] catsIds) {
        List<Integer> intIds = new ArrayList<>();
        for (String id: catsIds) {
            intIds.add(Integer.parseInt(id));
        }
        return taskDAO.addTask(task, intIds);
    }

    public List<Task> getAllMissedTasks() {
        return taskDAO.getAllMissedTasks();
    }

    public List<Task> getAllDoneTasks() {
        return taskDAO.getAllDoneTasks();
    }

    public boolean changeTaskStatus(Integer id) {
        return taskDAO.changeTaskStatus(id);
    }
}
