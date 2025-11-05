package ma.ws;

import ma.ws.entities.Compte;
import ma.ws.entities.TypeCompte;
import ma.ws.repositories.CompteRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

@Bean
CommandLineRunner start(CompteRepository compteRepository){
    return args -> {
        compteRepository.save(new Compte(null, Math.random()*9000, new Date(), TypeCompte.EPARGNE));
        compteRepository.save(new Compte(null, Math.random()*9000, new Date(), TypeCompte.COURANT));
        compteRepository.save(new Compte(null, Math.random()*9000, new Date(), TypeCompte.EPARGNE));

        compteRepository.findAll().forEach(c -> {
            System.out.println(c.toString());
        });
    };
}
}


