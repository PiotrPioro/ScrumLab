package pl.coderslab.web;

import pl.coderslab.dao.RecipeDao;
import pl.coderslab.model.Admin;
import pl.coderslab.model.Recipe;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/app/recipe/edit")
public class RecipeEdit extends HttpServlet {

    private static final RecipeDao recipeDao = new RecipeDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String id = request.getParameter("id");
        Recipe read = recipeDao.read(Integer.parseInt(id));
        request.setAttribute("recipe", read);

        getServletContext().getRequestDispatcher("/META-INF/edit.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try{
            HttpSession session = request.getSession();
            Admin admin = (Admin) session.getAttribute("admin");

            int prepTime = Integer.parseInt(request.getParameter("preparationTime"));
            Recipe recipe = new Recipe(Integer.parseInt(request.getParameter("id")), request.getParameter("name"), request.getParameter("ingredients"), request.getParameter("description"), prepTime, request.getParameter("preparation"), admin.getId());
            recipeDao.update(recipe);

        }catch (NumberFormatException e){
            e.printStackTrace();
        }

        response.sendRedirect("/recipe/list");
    }
}
