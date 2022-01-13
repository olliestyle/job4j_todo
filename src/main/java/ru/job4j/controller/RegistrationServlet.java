package ru.job4j.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.job4j.dao.UserDAO;
import ru.job4j.model.User;
import ru.job4j.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class RegistrationServlet extends HttpServlet {

    private static final Gson GSON = new GsonBuilder().create();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json; charset=utf-8");
        OutputStream output = resp.getOutputStream();
        String name = req.getParameter("name");
        if (UserService.instOf().findUserByName(name) != null) {
            String message = "Пользователь с таким именем уже зарегистрирован";
            String json = GSON.toJson(message);
            output.write(json.getBytes(StandardCharsets.UTF_8));
            output.flush();
            output.close();
        } else {
            String password = req.getParameter("password");
            UserService.instOf().addUser(new User(name, password));
            String message = "Пользователь добавлен";
            String json = GSON.toJson(message);
            output.write(json.getBytes(StandardCharsets.UTF_8));
            output.flush();
            output.close();
        }
    }
}
