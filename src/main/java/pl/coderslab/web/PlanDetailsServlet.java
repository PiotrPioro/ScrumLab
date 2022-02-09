
package pl.coderslab.web;

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

@WebServlet("/app/plan/details")
public class PlanDetailsServlet extends HttpServlet {

    private static final PlanDao planDao = new PlanDao();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try{
            int planId = Integer.parseInt(request.getParameter("id"));

            Plan plan = planDao.read(planId);
            request.setAttribute("plan", plan);

            HttpSession session = request.getSession();
            Admin admin = (Admin) session.getAttribute("admin");

            request.setAttribute("admin", admin);
            request.setAttribute("planDetails", planDao.getPlanDetails(admin, planId));

        }catch (NumberFormatException e){
            e.printStackTrace();
        }

        getServletContext().getRequestDispatcher("/META-INF/planDetails.jsp")
                .forward(request, response);
    }
}
