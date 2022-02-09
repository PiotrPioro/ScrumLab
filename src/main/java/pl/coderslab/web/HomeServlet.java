package pl.coderslab.web;

import pl.coderslab.dao.PlanDao;
import pl.coderslab.dao.RecipeDao;
import pl.coderslab.model.Admin;
import pl.coderslab.model.Plan;
import pl.coderslab.model.PlanDetails;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {

    private static final RecipeDao recipeDao = new RecipeDao();
    private static final PlanDao planDao = new PlanDao();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try{
            HttpSession session = request.getSession();
            Admin admin = (Admin) session.getAttribute("admin");
            int adminId = admin.getId();

            int countRecipes = recipeDao.numberOfRecipesByAdminId(adminId);
            int countPlans = planDao.countPlans(adminId);
            Plan plan = planDao.getLastPlanByUserId(adminId);
            PlanDetails planDetails = planDao.getPlanDetails(admin, plan.getId());

            request.setAttribute("countRecipes", countRecipes);
            request.setAttribute("countPlans", countPlans);
            request.setAttribute("planDetails", planDetails);

            getServletContext().getRequestDispatcher("/META-INF/home.jsp")
                    .forward(request, response);

        }catch (NumberFormatException e){
            e.printStackTrace();
        }
    }
}