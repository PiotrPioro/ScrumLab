package pl.coderslab.web;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/app/plan/details/delete")
public class ConfirmationOfDeletionPlanDetails extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            int id = Integer.parseInt(request.getParameter("id"));
            int planId = Integer.parseInt(request.getParameter("planId"));

            HttpSession session = request.getSession();
            session.setAttribute("deletePlanDetailsId", id);
            session.setAttribute("deletePlanId", planId);

            getServletContext().getRequestDispatcher("/META-INF/planDetailsDeleteConfirmation.jsp")
                    .forward(request, response);

        }catch (NumberFormatException e){
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try{
            HttpSession session = request.getSession();
            int id = (Integer) session.getAttribute("deletePlanDetailsId");
            int planId = (Integer) session.getAttribute("deletePlanId");

            if ("yes".equals(request.getParameter("confirm"))){
                response.sendRedirect("/app/plan/details/delete/confirmed?id=" + id + "&planId=" + planId);
            }else{
                response.sendRedirect("/app/plan/details?id=" + id + "&planId=" + planId);
            }
        }catch (NumberFormatException e){
            e.printStackTrace();
        }
    }
}
