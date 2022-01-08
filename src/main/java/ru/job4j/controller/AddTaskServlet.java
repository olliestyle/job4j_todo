package ru.job4j.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.job4j.model.Task;
import ru.job4j.service.TaskService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class AddTaskServlet extends HttpServlet {

    private final List<Task> taskList = new ArrayList<>();
    private static final Gson GSON = new GsonBuilder().create();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("hello in doGet addtask");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String description = req.getParameter("description");
        Task taskToAdd = TaskService.instOf().addTask(new Task(
                description,
                Timestamp.from(Instant.now()),
                false));
        taskList.add(taskToAdd);
        resp.setContentType("application/json; charset=utf-8");
        OutputStream output = resp.getOutputStream();
        String json = GSON.toJson(taskToAdd);
        output.write(json.getBytes(StandardCharsets.UTF_8));
        output.flush();
        output.close();
    }
}
