package pl.coderslab.web;

import pl.coderslab.dao.AdminDao;
import pl.coderslab.dao.PlanDao;
import pl.coderslab.model.Admin;
import pl.coderslab.model.Plan;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/app/plan/add")
public class PlanAdd extends HttpServlet {

    PlanDao planDao = new PlanDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try{
            HttpSession session = request.getSession();
            Admin admin = (Admin) session.getAttribute("admin");
            request.setAttribute("admin", admin);

        }catch (NullPointerException e){
            e.printStackTrace();
        }

        getServletContext().getRequestDispatcher("/META-INF/scheduleAdd.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try{
            HttpSession session = request.getSession();
            Admin admin = (Admin) session.getAttribute("admin");

            planDao.create(new Plan(request.getParameter("name"), request.getParameter("description"), admin));

        }catch (NumberFormatException e){
            e.printStackTrace();
        }

        response.sendRedirect("/app/plan/list");
    }
}
