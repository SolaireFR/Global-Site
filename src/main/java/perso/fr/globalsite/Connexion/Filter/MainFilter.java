package perso.fr.globalsite.Connexion.Filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import perso.fr.globalsite.Connexion.Service.URLManager;
import perso.fr.globalsite.URLManager.PublicURL;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

        URLManager.init();
        System.out.println(PublicURL.PrintURLs());

        String requestURI = req.getRequestURI();

        // PERMIT ALL
        if (IsPublicURL(requestURI))
            chain.doFilter(req, res);

        // PERMIT CONNECTED
        else if (verifConnexionFilter.verifConnexion(req)) {

            // PERMIT ADMIN
            if (requestURI.contains("/admin/"))
                if (verifRoleFilter.verifRoleAdmin(req))
                    chain.doFilter(req, res);
                    
                // MUST HAVE A VALID ROLE
                else {
                    HttpStatus status = HttpStatus.UNAUTHORIZED;
                    String message = "vous n'avez pas les droits d'acces a cette page";
                    redirectToError(res, status, message);
                    return;
                }

            // PERMIT ALL CONNECTED
            else
                chain.doFilter(req, res);
        }

        // MUST BE CONNECTED
        else
            redirectToLogin(res);
    }

    private boolean IsPublicURL(String fullURL) {
        List<String> urls = PublicURL.urls;
        for(String publicURL : urls) {
            if (fullURL.contains(publicURL))
                return true;
        }
        return false;
    }

    private void redirectToLogin(HttpServletResponse res) throws IOException {
        res.sendRedirect(URLManager.LOGIN_URL);
        return;
    }

    private void redirectToError(HttpServletResponse res, HttpStatus status, String message)
            throws IOException {
        res.sendRedirect(URLManager.ERROR_URL+"?status=" + status.value() + "&message=" + message);
        return;
    }
}