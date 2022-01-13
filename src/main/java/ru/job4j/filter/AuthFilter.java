package ru.job4j.filter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class AuthFilter implements Filter {

    private static final Gson GSON = new GsonBuilder().create();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest sreq, ServletResponse sresp, FilterChain chain) throws IOException, ServletException {
        System.out.println("start filter");
        HttpServletRequest req = (HttpServletRequest) sreq;
        HttpServletResponse resp = (HttpServletResponse) sresp;
        String uri = req.getRequestURI();
        if (uri.endsWith("log.do") || uri.endsWith("reg.do")) {
            System.out.println("uri filter");
            chain.doFilter(sreq, sresp);
            return;
        }
        if (req.getSession().getAttribute("user") == null) {
            System.out.println("session filter");
            String message = "Сессия не найдена";
            String json = GSON.toJson(message);
            resp.setContentType("application/json; charset=utf-8");
            OutputStream output = resp.getOutputStream();
            output.write(json.getBytes(StandardCharsets.UTF_8));
            output.flush();
            output.close();
            return;
        }
        System.out.println(req.getSession().getAttribute("user"));
        System.out.println("endfilter");
        chain.doFilter(sreq, sresp);
    }

    @Override
    public void destroy() {
    }
}
