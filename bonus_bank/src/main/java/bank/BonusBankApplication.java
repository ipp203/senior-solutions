package bank;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@SpringBootApplication
public class BonusBankApplication {

    public static void main(String[] args) {
        SpringApplication.run(BonusBankApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public EntityManagerFactory createEntitiManagerFactory() {
        return Persistence.createEntityManagerFactory("pu");
    }

}
