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

@WebServlet("/app/plan/edit")
public class PlanEdit extends HttpServlet {

    private static final PlanDao planDao = new PlanDao();
    private static final AdminDao adminDao = new AdminDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try{

            int id = Integer.parseInt(request.getParameter("id"));
            Plan plan = planDao.read(id);
            request.setAttribute("plan", plan);

        }catch (NumberFormatException e){
            e.printStackTrace();
        }

        getServletContext().getRequestDispatcher("/META-INF/planedit.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        try{
            Admin loggedInAdmin = (Admin) session.getAttribute("admin");
            int id =  loggedInAdmin.getId();
            Admin admin = adminDao.read(id);

            Plan plan = new Plan(Integer.parseInt(request.getParameter("id")), request.getParameter("name"), request.getParameter("description"), admin);
            planDao.update(plan);
        }catch (NumberFormatException e){
            e.printStackTrace();
        }

        response.sendRedirect("/app/plan/list");
    }
}
