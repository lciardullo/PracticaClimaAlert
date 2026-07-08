package frba.utn.climalert;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class ClimAlertApplication {

  public static void main(String[] args) {
    SpringApplication.run(ClimAlertApplication.class, args);
  }

}
