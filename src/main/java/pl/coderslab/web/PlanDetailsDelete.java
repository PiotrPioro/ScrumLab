package pl.coderslab.web;

import pl.coderslab.dao.RecipePlanDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/app/plan/details/delete/confirmed")
public class PlanDetailsDelete extends HttpServlet {

    private static final RecipePlanDao recipePlanDao = new RecipePlanDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try{
            int planId = Integer.parseInt(request.getParameter("planId"));
            int recipePlanId = Integer.parseInt(request.getParameter("id"));

            recipePlanDao.delete(recipePlanId);
            response.sendRedirect("/app/plan/details?id=" + planId);

        }catch (NumberFormatException e){
            e.printStackTrace();
        }


    }
}
