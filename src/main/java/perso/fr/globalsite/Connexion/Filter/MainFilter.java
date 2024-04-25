package perso.fr.globalsite.Connexion.Filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import perso.fr.globalsite.Connexion.Service.URLManager;
import perso.fr.globalsite.URLManager.AdminURLs;
import perso.fr.globalsite.URLManager.PublicURLs;
import perso.fr.globalsite.URLManager.UserURLs;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@WebFilter(urlPatterns = "/*")
public class MainFilter extends HttpFilter {

    @Autowired
    private VerifConnexionFilter verifConnexionFilter;

    @Autowired
    private VerifRoleFilter verifRoleFilter;

    @Override
    protected void doFilter(
            HttpServletRequest req,
            HttpServletResponse res,
            FilterChain chain) throws IOException, ServletException {

        String requestURI = req.getRequestURI();

        try {
            // PERMIT IF PUBLIC PAGE OR FILE
            boolean isPublic = PublicURLs.contain(requestURI);
            boolean containFile = URLManager.stringContainFile(requestURI);
            if (isPublic || containFile)
                chain.doFilter(req, res);

            // IF USER PAGE
            else if (UserURLs.contain(requestURI)) {

                // PERMIT IF USER CONNECTED
                if (verifConnexionFilter.verifConnexion(req))
                    chain.doFilter(req, res);
                
                // USER MUST BE CONNECTED
                else
                    redirectToLogin(requestURI, res);
            }
            // IF ADMIN PAGE
            else if (AdminURLs.contain(requestURI)) {

                // IF USER CONNECTED
                if (verifConnexionFilter.verifConnexion(req))

                    // PERMIT IF USER ADMIN
                    if (verifRoleFilter.verifRoleAdmin(req))
                        chain.doFilter(req, res);

                    // USER MUST BE ADMIN
                    else
                        res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

                // USER MUST BE CONNECTED
                else
                    redirectToLogin(requestURI, res);
            } 
            // PAGE NOT IN URLs
            else
                res.setStatus(HttpServletResponse.SC_NOT_FOUND);

        } catch (ServletException se) {
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            e.printStackTrace();
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private void redirectToLogin(String previousURL, HttpServletResponse res) throws IOException {
        URLManager.setPreviousURL(previousURL);
        res.sendRedirect(URLManager.LOGIN_URL);
        return;
    }
}