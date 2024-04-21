package perso.fr.globalsite.Security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import perso.fr.globalsite.Entity.User;
import perso.fr.globalsite.Entity.Repository.UserRepository;

@Component
@WebFilter(urlPatterns = "/*")
public class SecurityFilter extends HttpFilter {

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        String requestURI = req.getRequestURI();

        // Vérifie si l'URL correspond à "/login" ou "/global-site/login"
        if (requestURI.endsWith("/login")) {
            chain.doFilter(req, res);
            return;
        }

        boolean userLoggedIn = idUserLoggedIn(req);
        if (userLoggedIn)
            chain.doFilter(req, res);
        else
            redirectToLogin(res);
    }

    private boolean idUserLoggedIn(HttpServletRequest req)
            throws IOException, ServletException {
        HttpSession session = req.getSession(false);
        if (session != null) {
            String email = "" + session.getAttribute("email");
            User user = userRepository.getUserByEmail(email);
            if (user != null)
                return true;
            else
                return false;
        }

        else
            return false;
    }

    private void redirectToLogin(HttpServletResponse res) throws IOException {
        res.sendRedirect("/global-site/login");
    }
}