package pl.polsl.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import pl.polsl.math.PolybiusSquare;

/**
 * This servlet is responsible for managing decoding and encoding histry.
 *
 * @author Paulina Nieradzik
 * @version 1.0
 */
@WebServlet(name = "HistoryServlet", urlPatterns = {"/History"})
public class HistoryServlet extends HttpServlet {

    /**
     * Instance od PolubiusSquare class.
     */
    private PolybiusSquare polybiusSquare;

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
            polybiusSquare = (PolybiusSquare) context.getAttribute("polybiusSquare");
            String action = request.getParameter("history");
            if (action == null) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Parameter was null");
            } else {
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Servlet HistoryServlet</title>");
                out.println("<style>"
                        + "td {"
                        + "padding-right:20px;}"
                        + "</style>");
                out.println("</head>");
                out.println("<body>");
                if (action.equals("decryption")) {
                    showDecryptionHistory(out);
                } else if (action.equals("encryption")) {
                    showEncryptionHistory(out);
                } else // action == "all"
                {
                    showDecryptionHistory(out);
                    out.println("<hr>");
                    showEncryptionHistory(out);
                }
                out.println("</body>");
                out.println("</html>");
            }
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

    /**
     * Method which display history of decryption
     *
     * @param out print writer
     */
    private void showDecryptionHistory(PrintWriter out) {
        out.println("<h2>DECRYPTION</h2>");
        List<String> historyOfDecode = polybiusSquare.getHistoryOfDecoding();
        if (historyOfDecode.isEmpty()) {
            out.println("Decoding hasn't been done yet!");
        } else {
            out.println("<table>");
            out.println("<tr>");
            out.println("<td><strong>Code</strong> </td> <td> <strong>Decrypted word</strong> </td>");
            out.println("</tr>");
            for (int i = 0; i < historyOfDecode.size(); i += 2) {
                out.println("<tr>");
                out.println("<td>" + historyOfDecode.get(i) + "</td> <td>" + historyOfDecode.get(i + 1) + "</td>");
                out.println("</tr>");
            }
            out.println("</table>");
        }
    }

    /**
     * Method which display history of encryption
     *
     * @param out print writer
     */
    private void showEncryptionHistory(PrintWriter out) {
        out.println("<h2>ENCRYPTION</h2>");
        List<String> historyOfEncode = polybiusSquare.getHistoryOfEncoding();
        if (historyOfEncode.isEmpty()) {
            out.println("Encoding hasn't been done yet!");
        } else {
            out.println("<table>");
            out.println("<tr>");
            out.println("<td><strong> Word</strong> </td><td><strong> Encrypted word</strong> </td>");
            out.println("</tr>");
            for (int i = 0; i < historyOfEncode.size(); i += 2) {
                out.println("<tr>");
                out.println("<td>" + historyOfEncode.get(i) + "</td> </t></t></t> <td>" + historyOfEncode.get(i + 1) + "</td>");
                out.println("</tr>");
            }
            out.println("</table>");
        }
    }
}
