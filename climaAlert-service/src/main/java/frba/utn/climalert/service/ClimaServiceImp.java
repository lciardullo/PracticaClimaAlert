package frba.utn.climalert.service;

import frba.utn.climalert.domain.adapter.ClimaAlertAdapter;
import frba.utn.climalert.domain.adapter.NotificacionAdapter;
import frba.utn.climalert.domain.clima.Alerta;
import frba.utn.climalert.domain.clima.Clima;
import frba.utn.climalert.domain.clima.Localidad;
import frba.utn.climalert.domain.notificacion.Notificacion;
import frba.utn.climalert.repository.clima.climaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClimaServiceImp implements ClimaService {
  private static final double TEMPERATURA_LIMITE = 35.0;
  private static final double HUMEDAD_LIMITE = 60.0;
  private static final Localidad LOCALIDAD_FIJA = new Localidad("CABA", "Buenos Aires", "Argentina");
  private static final List<String> DESTINATARIOS_ALERTA = List.of(
      "admin@clima.com",
      "emergencias@clima.com",
      "meteorologia@clima.com",
      "lucas252ciardu@gmail.com"
  );

  private final ClimaAlertAdapter climaAlertAdapter;
  private final NotificacionAdapter notificacionAdapter;
  private final climaRepository climaRepository;
  private final Alerta alerta;
  private Long ultimoClimaAlertadoId;

  public ClimaServiceImp(
      ClimaAlertAdapter climaAlertAdapter,
      NotificacionAdapter notificacionAdapter,
      climaRepository climaRepository
  ) {
    this.climaAlertAdapter = climaAlertAdapter;
    this.notificacionAdapter = notificacionAdapter;
    this.climaRepository = climaRepository;
    this.alerta = new Alerta(TEMPERATURA_LIMITE, HUMEDAD_LIMITE);
  }

  @Override
  public Clima consultarClima(Localidad localidad) {
    Clima clima = climaAlertAdapter.solicitarRealTime(localidad);
    return climaRepository.save(clima);
  }

  @Override
  public Clima consultarYNotificar(Localidad localidad, List<String> destinatarios) {
    Clima clima = consultarClima(localidad);
    if (alerta.isAlerta(clima)) {
      enviarAlertaSiCorresponde(clima, destinatarios);
    }
    return clima;
  }

  @Override
  public Clima registrarClimaActual() {
    return consultarClima(LOCALIDAD_FIJA);
  }

  @Override
  public Optional<Clima> procesarUltimoClima() {
    Optional<Clima> climaOpt = climaRepository.findLatest();
    if (climaOpt.isEmpty()) {
      return Optional.empty();
    }

    Clima clima = climaOpt.get();
    if (alerta.isAlerta(clima)) {
      enviarAlertaSiCorresponde(clima, DESTINATARIOS_ALERTA);
    }

    return Optional.of(clima);
  }

  private void enviarAlertaSiCorresponde(Clima clima, List<String> destinatarios) {
    if (clima.getId() != null && clima.getId().equals(ultimoClimaAlertadoId)) {
      return;
    }

    notificacionAdapter.enviar(crearNotificacion(clima, destinatarios));
    ultimoClimaAlertadoId = clima.getId();
  }

  private Notificacion crearNotificacion(Clima clima, List<String> destinatarios) {
    return new Notificacion(
        destinatarios,
        "Alerta climatica en " + obtenerNombreLocalidad(clima),
        construirDetalleCompleto(clima),
        clima
    );
  }

  private String construirDetalleCompleto(Clima clima) {
    return "Localidad: " + obtenerNombreLocalidad(clima)
        + ", region: " + obtenerRegion(clima)
        + ", pais: " + obtenerPais(clima)
        + ". Temperatura: " + clima.getTemperatura().getTemperatura() + " C"
        + ", humedad: " + clima.getTemperatura().getHumedad() + "%"
        + ", condicion: " + clima.getTemperatura().getCondicion()
        + ", viento: " + clima.getTemperatura().getVientoKH() + " km/h"
        + ", presion: " + clima.getTemperatura().getPresion() + " mb.";
  }

  private String obtenerNombreLocalidad(Clima clima) {
    return clima.getLocalidad() != null && clima.getLocalidad().getNombre() != null
        ? clima.getLocalidad().getNombre()
        : "localidad desconocida";
  }

  private String obtenerRegion(Clima clima) {
    return clima.getLocalidad() != null && clima.getLocalidad().getRegion() != null
        ? clima.getLocalidad().getRegion()
        : "sin region";
  }

  private String obtenerPais(Clima clima) {
    return clima.getLocalidad() != null && clima.getLocalidad().getPais() != null
        ? clima.getLocalidad().getPais()
        : "sin pais";
  }
}
