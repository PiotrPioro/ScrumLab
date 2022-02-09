package pl.coderslab.web;

import pl.coderslab.dao.PlanDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/app/plan/delete/confirmed")
public class DeletePlan extends HttpServlet {

    private static final PlanDao planDao = new PlanDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        planDao.delete(Integer.parseInt(request.getParameter("id")));

        response.sendRedirect("/app/plan/list");
    }
}
