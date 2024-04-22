package perso.fr.globalsite.SecurityFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
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

        String requestURI = req.getRequestURI();

        // PERMIT ALL
        if (requestURI.contains("/public/"))
            chain.doFilter(req, res);

        // PERMIT CONNECTED
        else if (verifConnexionFilter.verifConnexion(req)) {

            // PERMIT ALL CONNECTED
            if (requestURI.endsWith("/alluser"))
                chain.doFilter(req, res);

            // PERMIT ADMIN
            else if (verifRoleFilter.verifRoleAdmin(req))
                chain.doFilter(req, res);

            // MUST HAVE A VALID ROLE
            else {
                HttpStatus status = HttpStatus.UNAUTHORIZED;
                String message = "vous n'avez pas les droits d'acces a cette page";
                redirectToError(res, status, message);
                return;
            }
        }

        // MUST BE CONNECTED
        else
            redirectToLogin(res);
    }

    private void redirectToLogin(HttpServletResponse res) throws IOException {
        res.sendRedirect("/global-site/public/login");
        return;
    }

    private void redirectToError(HttpServletResponse res, HttpStatus status, String message)
            throws IOException {
        res.sendRedirect("/global-site/public/error?status=" + status.value() + "&message=" + message);
        return;
    }
}