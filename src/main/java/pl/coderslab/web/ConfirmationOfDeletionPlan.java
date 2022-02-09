package pl.coderslab.web;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/app/plan/delete")
public class ConfirmationOfDeletionPlan extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            int id = Integer.parseInt(request.getParameter("id"));
            HttpSession session = request.getSession();
            session.setAttribute("deletePlanId", id);

            getServletContext().getRequestDispatcher("/META-INF/planDeleteConfirmation.jsp")
                    .forward(request, response);

        }catch (NumberFormatException e){
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try{
            HttpSession session = request.getSession();
            int id = (Integer) session.getAttribute("deletePlanId");

            if ("yes".equals(request.getParameter("confirm"))) {
                response.sendRedirect("/app/plan/delete/confirmed?id=" + id);
            }else{
                response.sendRedirect("/app/plan/list");
            }
        }catch (NumberFormatException e){
            e.printStackTrace();
        }
    }
}
