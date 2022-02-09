package pl.coderslab.web;

import pl.coderslab.dao.RecipeDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/app/recipe/delete/confirmed")
public class DeleteRecipe extends HttpServlet {

    private static final RecipeDao recipeDao = new RecipeDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try{
            int id = Integer.parseInt(request.getParameter("id"));
            recipeDao.delete(id);
        }catch (NumberFormatException e){
            e.printStackTrace();
        }

        response.sendRedirect("/recipe/list");
    }
}
