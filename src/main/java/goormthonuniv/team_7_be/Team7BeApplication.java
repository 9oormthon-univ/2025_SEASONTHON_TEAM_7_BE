package goormthonuniv.team_7_be;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class Team7BeApplication {

    public static void main(String[] args) {
        SpringApplication.run(Team7BeApplication.class, args);
    }

}
