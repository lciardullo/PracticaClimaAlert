package frba.utn.climalert.controller;

import frba.utn.climalert.domain.clima.Clima;
import frba.utn.climalert.service.ClimaService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/clima")
public class ClimaController {
  private final ClimaService climaService;

  public ClimaController(ClimaService climaService) {
    this.climaService = climaService;
  }

  @GetMapping("/actual")
  public Clima consultarClimaActual() {
    return climaService.registrarClimaActual();
  }

  @PostMapping("/alerta")
  public Clima consultarClimaYProcesarAlerta() {
    return climaService.registrarClimaActualYNotificar();
  }
}
