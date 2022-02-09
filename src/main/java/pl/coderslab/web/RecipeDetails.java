package pl.coderslab.web;

import pl.coderslab.dao.RecipeDao;
import pl.coderslab.model.Recipe;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/app/recipe/details")
public class RecipeDetails extends HttpServlet {
    private static final RecipeDao recipeDao = new RecipeDao();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String id = req.getParameter("id");
        Recipe recipe = recipeDao.read(Integer.parseInt(id));
        req.setAttribute("recipe", recipe);

        getServletContext().getRequestDispatcher("/META-INF/recipe-details.jsp")
                .forward(req, resp);
    }

}