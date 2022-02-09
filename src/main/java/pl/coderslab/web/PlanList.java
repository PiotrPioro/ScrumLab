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
import java.util.List;

@WebServlet("/app/plan/list")
public class PlanList extends HttpServlet {

    private static final PlanDao planDao = new PlanDao();


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        Admin admin = (Admin) session.getAttribute("admin");
        request.setAttribute("admin", admin);

        List<Plan> planList;
        planList = planDao.readAllPlansByUserId(admin.getId());

        request.setAttribute("schedules", planList);

        getServletContext().getRequestDispatcher("/META-INF/schedulesList.jsp")
                .forward(request, response);
    }
}
