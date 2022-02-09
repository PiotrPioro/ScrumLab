package pl.coderslab.web;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/app/recipe/delete")
public class ConfirmationOfDeletionRecipe extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            int id = Integer.parseInt(request.getParameter("id"));
            HttpSession session = request.getSession();
            session.setAttribute("deleteRecipeId", id);

            getServletContext().getRequestDispatcher("/META-INF/recipeDeleteConfirmation.jsp")
                    .forward(request, response);

        }catch (NumberFormatException e){
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try{
            HttpSession session = request.getSession();
            int id = (Integer) session.getAttribute("deleteRecipeId");

            if ("yes".equals(request.getParameter("confirm"))){
                response.sendRedirect("/app/recipe/delete/confirmed?id=" + id);
            }else{
                response.sendRedirect("/recipe/list");
            }
        }catch (NumberFormatException e){
            e.printStackTrace();
        }
    }
}
