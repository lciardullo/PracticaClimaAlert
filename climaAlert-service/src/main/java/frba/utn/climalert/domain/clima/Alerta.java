package frba.utn.climalert.domain.clima;

public class Alerta {
  private final double temperaturaLimite;
  private final double humedadLimite;

  public Alerta(double temperaturaLimite, double humedadLimite) {
    this.temperaturaLimite = temperaturaLimite;
    this.humedadLimite = humedadLimite;
  }

  public Boolean isAlerta(Clima clima) {
    if (clima == null || clima.getTemperatura() == null) {
      return false;
    }

    return clima.getTemperatura().getTemperatura() >= temperaturaLimite
        && clima.getTemperatura().getHumedad() >= humedadLimite;
  }
}
