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

@WebServlet("/app/recipe/add")
public class RecipeAdd extends HttpServlet {

    private static final RecipeDao recipeDao = new RecipeDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        getServletContext().getRequestDispatcher("/META-INF/recipe-add.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();

        try{
            Admin admin = (Admin) session.getAttribute("admin");
            int adminId = admin.getId();
            recipeDao.create(new Recipe(request.getParameter("name"), request.getParameter("ingredients"), request.getParameter("description"), Integer.parseInt(request.getParameter("prepTime")), request.getParameter("preparation"), adminId));
        }catch (NumberFormatException e){
            e.printStackTrace();
        }

        response.sendRedirect("/recipe/list");
    }
}
