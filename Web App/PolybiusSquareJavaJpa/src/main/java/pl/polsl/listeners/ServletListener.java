package pl.polsl.listeners;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import pl.polsl.controller.HistoryDateEntityJpaController;
import pl.polsl.controller.TranslationEntityJpaController;
import pl.polsl.math.PolybiusSquare;

/**
 * Web application lifecycle listener. Used to create instance of
 * PolybiuseSquare class. It also creates EntityManagerFactory so as to do that
 * only once during application lifecycle. It creates
 * HistoryDateEntityJpaController and TranslationEntityJpaController to further
 * improve application performance as well.
 *
 * @author Paulina Nieradzik
 * @version 1.1
 */
public class ServletListener implements ServletContextListener {

    /**
     * Context Initialized whichcreate instance od PolybiusSquare class and save
     * it in context
     *
     * @param sce event
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {

        try {
            // loading the JDBC driver
            Class.forName("org.apache.derby.jdbc.ClientDriver");
        } catch (ClassNotFoundException cnfe) {
            return;
        }

        try ( Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/lab", "app", "app")) {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("pl.polsl.lab_JPA_jar_1.0-SNAPSHOTPU");
            TranslationEntityJpaController translationController = new TranslationEntityJpaController(emf);
            HistoryDateEntityJpaController historyDataController = new HistoryDateEntityJpaController(emf);
            sce.getServletContext().setAttribute("translationController", translationController);
            sce.getServletContext().setAttribute("historyDataController", historyDataController);
            sce.getServletContext().setAttribute("entityManagerFactory", emf);
        } catch (SQLException sqle) {
        }

        PolybiusSquare polybiusSquare = new PolybiusSquare();
        sce.getServletContext().setAttribute("polybiusSquare", polybiusSquare);
    }
}
