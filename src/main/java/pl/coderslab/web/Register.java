package pl.coderslab.web;

import pl.coderslab.dao.AdminDao;
import pl.coderslab.model.Admin;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/register")
public class Register extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/META-INF/registration.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

         String firstName = request.getParameter("name");
         String lastName = request.getParameter("surname");
         String email = request.getParameter("email");
         if (request.getParameter("password").equals(request.getParameter("repassword"))) {
             String password = request.getParameter("password");

             AdminDao adminDao = new AdminDao();
             adminDao.create(new Admin(firstName, lastName,email,password, 1, 1));
             response.sendRedirect("/login");

         }else {
             response.sendRedirect("/register");
         }
    }
}
