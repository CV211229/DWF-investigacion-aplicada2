package sv.edu.udb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class EventDrivenRabbitmqApplication {

    public static void main(String[] args) {
        SpringApplication.run(EventDrivenRabbitmqApplication.class, args);
    }
}