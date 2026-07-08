package frba.utn.climalert.domain.adapter;

import frba.utn.climalert.domain.clima.Clima;
import frba.utn.climalert.domain.clima.Localidad;

public interface ClimaAlertAdapter {
  Clima solicitarRealTime(Localidad localidad);
}
