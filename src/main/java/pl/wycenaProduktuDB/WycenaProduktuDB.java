package pl.wycenaProduktuDB;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import pl.wycenaProduktuDB.model.Uzytkownik;
import pl.wycenaProduktuDB.repository.UzytkownikRepository;

@SpringBootApplication
public class WycenaProduktuDB {

    public static void main(String[] args) {
        SpringApplication.run(WycenaProduktuDB.class, args);
    }

//      //Mockowanie użytkowników    
//    @Autowired
//    UzytkownikRepository repository;
//
//    @Bean
//    public CommandLineRunner loadData() {
//        return (args -> {
//            Uzytkownik jb = new Uzytkownik("Slawek", true, "admin", "haslo");
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
