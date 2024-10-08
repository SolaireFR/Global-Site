package perso.fr.GlobalSite.Main.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import perso.fr.GlobalSite.Main.Filter.LocaleInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private LocaleInterceptor localeInterceptor;

    @Override
    public void addInterceptors(@NonNull InterceptorRegistry registry) {
        registry.addInterceptor(localeInterceptor);
    }
}
