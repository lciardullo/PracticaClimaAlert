package frba.utn.climalert;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = "clima.scheduler.enabled=false")
class ClimAlertApplicationTests {

  @Test
  void contextLoads() {
  }

}
