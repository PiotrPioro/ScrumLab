package pl.coderslab.web;

import pl.coderslab.dao.DayNameDao;
import pl.coderslab.dao.PlanDao;
import pl.coderslab.dao.RecipeDao;
import pl.coderslab.dao.RecipePlanDao;
import pl.coderslab.model.DayName;
import pl.coderslab.model.Plan;
import pl.coderslab.model.Recipe;
import pl.coderslab.model.RecipePlan;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


@WebServlet("/app/recipe/plan/add")
public class PlanDetailsAdd extends HomeServlet{

    private static final PlanDao planDao = new PlanDao();
    private static final DayNameDao dayNameDao = new DayNameDao();
    private static final RecipeDao recipeDao = new RecipeDao();
    private static final RecipePlanDao recipePlanDao = new RecipePlanDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        List<Plan> planList = planDao.findAll();
        List<DayName> dayList = dayNameDao.findAll();
        List<Recipe> recipeList = recipeDao.findAll();

        request.setAttribute("planList", planList);
        request.setAttribute("dayNameList", dayList);
        request.setAttribute("recipeList", recipeList);

        getServletContext().getRequestDispatcher("/META-INF/schedule-details-add.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try{
            recipePlanDao.create(new RecipePlan(Integer.parseInt(request.getParameter("recipeId")), request.getParameter("mealName"), Integer.parseInt(request.getParameter("mealDisplayOrder")), Integer.parseInt(request.getParameter("dayNameId")), Integer.parseInt(request.getParameter("planId"))));

            response.sendRedirect("/app/plan/list");

        }catch (NumberFormatException e){
            e.printStackTrace();
        }
    }
}
