package controllers;

import model.*;
import persistance.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(value = "/login", loadOnStartup = 1, initParams = {
        @WebInitParam(name = "admin1", value = "admin1;1"),
        @WebInitParam(name = "admin2", value = "admin2;1")
})
public class LoginController extends HttpServlet {

    private final UserDao USER_DAO = new UserDaoImpl();

    @Override
    public void init() throws ServletException {
        String[] firstUser = getInitParameter("admin1").split(";");
        String[] secondUser = getInitParameter("admin2").split(";");

        USER_DAO.create(new User(firstUser[0], firstUser[1], Role.ADMIN));
        USER_DAO.create(new User(secondUser[0], secondUser[1], Role.ADMIN));
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String login = request.getParameter("login");
        String password = request.getParameter("password");

        User user = null;

        try {
            if (login == null || login.isEmpty()) {
                throw new RuntimeException("Invalid user login");
            }
            if (password == null || password.isEmpty()) {
                throw new RuntimeException("Invalid user password");
            }
            user = USER_DAO.getUserByLoginAndPassword(login, password);
            if (user == null) {
                if (USER_DAO.isLoginExists(login)) {
                    throw new RuntimeException("User already exists");
                }
            }

        } catch (RuntimeException e) {
            request.getSession().setAttribute("error", e.getMessage());
            response.sendRedirect("/index.jsp");
            return;
        }

        HttpSession session = request.getSession();
        session.setAttribute("user", user);
        session.setMaxInactiveInterval(600);
        assert user != null;
        if (user.getRole() == Role.ADMIN) {
            request.setAttribute("users", USER_DAO.getUsers());
        }
        getServletContext().getRequestDispatcher("/main.jsp").forward(request, response);
    }
}
