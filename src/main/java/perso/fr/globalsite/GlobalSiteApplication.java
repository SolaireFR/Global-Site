package perso.fr.globalsite;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import perso.fr.globalsite.URLManager.InitURLManagers;

@SpringBootApplication
public class GlobalSiteApplication {

	public static void main(String[] args) {
		InitURLManagers.inits();
		SpringApplication.run(GlobalSiteApplication.class, args);
	}
}