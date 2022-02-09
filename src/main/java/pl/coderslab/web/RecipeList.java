package pl.coderslab.web;

import pl.coderslab.dao.RecipeDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/recipe/list")
public class RecipeList extends HttpServlet {

    private static final RecipeDao recipeDao = new RecipeDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setAttribute("recipes", recipeDao.findAll());

        getServletContext().getRequestDispatcher("/META-INF/recipelist.jsp")
                .forward(request, response);
    }
}
