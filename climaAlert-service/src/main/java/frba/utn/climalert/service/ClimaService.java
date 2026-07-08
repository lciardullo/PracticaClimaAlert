package frba.utn.climalert.service;

import frba.utn.climalert.domain.clima.Clima;
import frba.utn.climalert.domain.clima.Localidad;

import java.util.List;
import java.util.Optional;

public interface ClimaService {
  Clima consultarClima(Localidad localidad);

  Clima consultarYNotificar(Localidad localidad, List<String> destinatarios);

  Clima registrarClimaActual();

  Clima registrarClimaActualYNotificar();

  Optional<Clima> procesarUltimoClima();
}
