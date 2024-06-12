package perso.fr.GlobalSite.Connection.Service;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.LocaleResolver;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Locale;

@Component
public class LocaleInterceptor implements HandlerInterceptor {

    private final LocaleResolver localeResolver;

    public LocaleInterceptor(LocaleResolver localeResolver) {
        this.localeResolver = localeResolver;
    }

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) throws Exception {
        if (localeResolver.resolveLocale(request).equals(Locale.ENGLISH)) {
            String acceptLanguage = request.getHeader("Accept-Language");
            if (acceptLanguage != null && !acceptLanguage.isEmpty()) {
                Locale preferredLocale = request.getLocale();
                localeResolver.setLocale(request, response, preferredLocale);
            }
        }
        return true;
    }
}