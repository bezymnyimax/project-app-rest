package ru.gorbunov.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.gorbunov.service.PersonService;
import ru.gorbunov.service.PersonServiceImpl;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet(name = "PersonController", urlPatterns = {"/person"})
public class PersonController extends HttpServlet {

    public static final String CONTENT_TYPE = "application/json";
    private final PersonService personService = new PersonServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        resp.setContentType(CONTENT_TYPE);

        long personId;
        try {
            personId = Long.parseLong(req.getParameter("id"));
        } catch (NumberFormatException e) {
            sendError(400, "Отсутствует ID пользователя", out, resp);
            return;
        }

        try {
            personService.getPerson(personId, out);
        } catch (RuntimeException e1) {
            sendError(404, e1.getMessage(), out, resp);
        } catch (IOException | SQLException e2) {
            sendError(500, "Ошибка", out, resp);
        } finally {
            out.flush();
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json");

        try {
            personService.createPerson(req.getReader(), out);
        } catch (IOException e1) {
            sendError(400, "Неверный формат Person JSON", out, resp);
        } catch (SQLException e2) {
            sendError(500, "Внутренняя ошибка сервера", out, resp);
        } finally {
            out.flush();
        }

    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getMethod();
        if (!method.equals("PATCH")) {
            super.service(req, resp);
            return;
        }
        this.doPatch(req, resp);
    }

    protected void doPatch(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json");

        try {
            personService.updatePerson(req.getReader(), out);
        } catch (RuntimeException e1) {
            sendError(404, e1.getMessage(), out, resp);
        } catch (IOException e2) {
            sendError(400, "Неверный формат Person JSON", out, resp);
        } catch (SQLException e3) {
            sendError(500, "Внутренняя ошибка сервера", out, resp);
        } finally {
            out.flush();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json");

        long userId;
        try {
            userId = Long.parseLong(req.getParameter("id"));
        } catch (NumberFormatException e) {
            sendError(400, "Отсутствует ID пользователя", out, resp);
            return;
        }

        try {
            personService.deletePerson(userId, out);
        } catch (RuntimeException e1) {
            sendError(404, e1.getMessage(), out, resp);
        } catch (SQLException | IOException e2) {
            sendError(500, "Внутренняя ошибка сервера", out, resp);
        } finally {
            out.flush();
        }
    }

    private void sendError(int status, String error, PrintWriter out, HttpServletResponse resp) {
        resp.setStatus(status);
        out.print(String.format("{\"Ошибка\":\"%s\"}", error));
    }
}
