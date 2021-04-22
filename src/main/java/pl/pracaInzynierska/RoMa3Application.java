package pl.pracaInzynierska;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import pl.pracaInzynierska.model.Uzytkownik;
import pl.pracaInzynierska.repository.UzytkownikRepository;

@SpringBootApplication
public class RoMa3Application {

    public static void main(String[] args) {
        SpringApplication.run(RoMa3Application.class, args);
    }

//      //Mockowanie użytkowników    
//    @Autowired
//    UzytkownikRepository repository;
//
//    @Bean
//    public CommandLineRunner loadData() {
//        return (args -> {
//            Uzytkownik jb = new Uzytkownik("Jack", true, "user", "bauer");
//            Uzytkownik co = new Uzytkownik("Chloe", true, "user", "pass");
//            
//            System.out.println(jb);
//            System.out.println(co);
//            
//            repository.save(jb);
//            repository.save(co);
//        });
//    }
}
