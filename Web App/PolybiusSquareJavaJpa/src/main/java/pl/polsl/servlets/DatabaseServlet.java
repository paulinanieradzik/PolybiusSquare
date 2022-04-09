package pl.polsl.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import pl.polsl.controller.HistoryDateEntityJpaController;
import pl.polsl.controller.TranslationEntityJpaController;
import pl.polsl.math.HistoryDateEntity;
import pl.polsl.math.TranslationEntity;

/**
 * This servlet is responsible for showing history from database.
 *
 * @author Paulina Nieradzik
 * @version 1.0
 */
@WebServlet(name = "DatabaseServlet", urlPatterns = {"/Database"})
public class DatabaseServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            ServletContext context = getServletContext();
            EntityManagerFactory emf = (EntityManagerFactory) context.getAttribute("entityManagerFactory");
            TranslationEntityJpaController translationController = (TranslationEntityJpaController) context.getAttribute("translationController");
            HistoryDateEntityJpaController historyDataController = (HistoryDateEntityJpaController) context.getAttribute("historyDataController");
            if (emf == null) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "No database connection");
                return;
            }
            List<TranslationEntity> translationEntities = translationController.findTranslationEntityEntities();
            List<HistoryDateEntity> historyDateEntities = historyDataController.findHistoryDateEntityEntities();
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet DatabaseServlet</title>");
            out.println("<style>"
                    + "td {padding-left:20px;"
                    + "padding-right:20px;}"
                    + "</style>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Table with history of translation</h1>");
            out.println("<table>");
            out.println("<tr>");
            out.println("<td><strong>Id</strong></td> <td><strong> Given word</strong></td> <td> <strong>Translated word</strong> </td>");
            out.println("</tr>");
            for (TranslationEntity entity : translationEntities) {
                out.println("<tr>");
                out.println("<td>" + entity.getId() + "</td> <td>" + entity.getGivenWord() + "</td> <td>" + entity.getTranslatedWord() + "</td>");
                out.println("</tr>");
            }
            out.println("</table>");
            out.println("<br>");
            out.println("<h1>Table with date of translation</h1>");
            out.println("<table>");
            out.println("<tr>");
            out.println("<td><strong>Id</strong></td><td><strong>Date</strong></td><td><strong>Id translation</strong></td>");
            out.println("</tr>");
            for (HistoryDateEntity entity : historyDateEntities) {
                out.println("<tr>");
                out.println("<td>" + entity.getId() + "</td> <td>" + entity.getDate() + "</td> <td>" + entity.getIdTranslation() + "</td>");
                out.println("</tr>");
            }
            out.println("</table>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
