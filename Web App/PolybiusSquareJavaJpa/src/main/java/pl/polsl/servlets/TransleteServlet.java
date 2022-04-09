package pl.polsl.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Stream;
import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import pl.polsl.controller.HistoryDateEntityJpaController;
import pl.polsl.controller.TranslationEntityJpaController;
import pl.polsl.math.HistoryDateEntity;
import pl.polsl.math.PolybiusException;
import pl.polsl.math.PolybiusSquare;
import pl.polsl.math.TranslationEntity;

/**
 * This servlet is responsible for managing decoding and encoding.
 *
 * @author Paulina Nieradzik
 * @version 1.0
 */
@WebServlet(name = "TransleteServlet", urlPatterns = {"/Translate"})
public class TransleteServlet extends HttpServlet {

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
            PolybiusSquare polybiusSquare = (PolybiusSquare) context.getAttribute("polybiusSquare");

            String action = request.getParameter("type");
            if (action == null) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Parameter was null");
            } else {
                EntityManagerFactory emf = (EntityManagerFactory) context.getAttribute("entityManagerFactory");
                TranslationEntityJpaController translationController;
                HistoryDateEntityJpaController historyDataController;
                String word = request.getParameter("word");
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Servlet NewServlet</title>");
                out.println("</head>");
                out.println("<body>");
                Cookie[] cookies = request.getCookies();
                String lastWord = "No previous translate";
                if (cookies != null) {
                    for (Cookie cookie : cookies) {
                        if (cookie.getName().equals("lastWord")) {
                            lastWord = cookie.getValue();
                            break;
                        }
                    }
                }
                if (word.length() < 1) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "First type the word");
                } else {
                    if (action.equals("decryption")) {
                        String decodedWord;
                        List<String> newArgs = new ArrayList<>();
                        List<Integer> givenWordIntegerArray = new ArrayList<>();
                        Stream<String> givenWordArray = Stream.of(word.split(" "));
                        givenWordArray.forEach(letterCode -> newArgs.add(letterCode));
                        boolean matches = true;
                        int wordLenght = newArgs.size();
                        for (int i = 0; i < wordLenght; i++) {
                            if (!newArgs.get(i).matches("\\d\\d")) {
                                matches = false;
                            }
                        }
                        if (matches) {
                            newArgs.stream()
                                    .mapToInt(arg -> Integer.parseInt(arg))
                                    .forEach(arg -> givenWordIntegerArray.add(arg));
                            try {
                                decodedWord = polybiusSquare.decryption(givenWordIntegerArray);
                                if (emf == null) {
                                    out.println("Warning! <br>Results of translation are not stored in database.<br><br>");
                                }
                                out.println("Decrypted word:   " + decodedWord);
                                out.println("</br>");

                                out.println("<hr><p>This section is read from a <strong>cookie</strong></p>");
                                if (lastWord.equals("No previous translate")) {
                                    out.println(lastWord);
                                } else {
                                    out.println("The word you typed has " + commonsLetters(decodedWord, lastWord) + " common letters with last word.");
                                }
                                Cookie cookie = new Cookie("lastWord", decodedWord);
                                response.addCookie(cookie);
                                polybiusSquare.addToHistoryOfDecode(word, decodedWord);
                                /**
                                 * **************
                                 */
                                if (emf != null) {
                                    translationController = (TranslationEntityJpaController) context.getAttribute("translationController");
                                    historyDataController = (HistoryDateEntityJpaController) context.getAttribute("historyDataController");
                                    TranslationEntity translation = new TranslationEntity();
                                    translation.setGivenWord(word);
                                    translation.setTranslatedWord(decodedWord);
                                    translationController.create(translation);
                                    HistoryDateEntity historyDate = new HistoryDateEntity();
                                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                                    LocalDateTime now = LocalDateTime.now();
                                    historyDate.setDate(dtf.format(now).toString());
                                    historyDate.setIdTranslation(translation.getId().toString());
                                    historyDataController.create(historyDate);
                                }

                            } catch (PolybiusException exception) {
                                response.sendError(HttpServletResponse.SC_BAD_REQUEST, exception.getMessage());
                            }
                        } else {
                            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Wrong format of data. Letter code is not two-digit or is not a number.");
                        }
                    } else // action == "encryption"
                    {
                        String result = "";
                        try {
                            List<Integer> encryptedWord = polybiusSquare.encryption(word);
                            for (int letterCode : encryptedWord) {
                                result += " " + letterCode;
                            }
                            if (emf == null) {
                                out.println("Warning! <br>Results of translation are not stored in database.<br><br>");
                            }
                            out.println("Encrypted word: " + result);
                            out.println("</br>");
                            out.println("<hr><p>This section is read from a <strong>cookie</strong></p>");
                            if (lastWord.equals("No previous translate")) {
                                out.println(lastWord);
                            } else {
                                out.println("The word you typed has " + commonsLetters(word.toUpperCase(), lastWord) + " common letters with last word.");
                            }
                            Cookie cookie = new Cookie("lastWord", word.toUpperCase());
                            response.addCookie(cookie);
                            polybiusSquare.addToHistoryOfEncode(word, result);
                            if (emf != null) {
                                TranslationEntity translation = new TranslationEntity();
                                translationController = (TranslationEntityJpaController) context.getAttribute("translationController");
                                historyDataController = (HistoryDateEntityJpaController) context.getAttribute("historyDataController");
                                translation.setGivenWord(word);
                                translation.setTranslatedWord(result);
                                translationController.create(translation);
                                HistoryDateEntity historyDate = new HistoryDateEntity();
                                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                                LocalDateTime now = LocalDateTime.now();
                                historyDate.setDate(dtf.format(now).toString());
                                historyDate.setIdTranslation(translation.getId().toString());
                                historyDataController.create(historyDate);
                            }
                        } catch (PolybiusException exception) {
                            response.sendError(HttpServletResponse.SC_BAD_REQUEST, exception.getMessage());
                        }
                    }
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
     * Method which return count of common letters.
     *
     * @param word current word
     * @param lastWord last word
     * @return count of common letters
     */
    private int commonsLetters(String word, String lastWord) {

        char[] wordArray = word.replaceAll("\\s+", "").toCharArray();
        char[] lastWordArray = lastWord.replaceAll("\\s+", "").toCharArray();
        Set<Character> wordSet = new TreeSet<>();
        Set<Character> lastWordSet = new TreeSet<>();
        for (char c : wordArray) {
            wordSet.add(c);
        }
        for (char c : lastWordArray) {
            lastWordSet.add(c);
        }
        wordSet.retainAll(lastWordSet);
        return wordSet.size();
    }
}
