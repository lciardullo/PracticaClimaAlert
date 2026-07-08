package frba.utn.climalert.scheduler;

import frba.utn.climalert.service.ClimaService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "clima.scheduler.enabled", havingValue = "true", matchIfMissing = true)
public class ClimaScheduler {
  private final ClimaService climaService;

  public ClimaScheduler(ClimaService climaService) {
    this.climaService = climaService;
  }

  @Scheduled(initialDelay = 0, fixedRate = 300000)
  public void consultarClimaCadaCincoMinutos() {
    climaService.registrarClimaActual();
  }

  @Scheduled(initialDelay = 60000, fixedRate = 60000)
  public void procesarAlertasCadaMinuto() {
    climaService.procesarUltimoClima();
  }
}
