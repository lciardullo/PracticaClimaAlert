package frba.utn.climalert.dto.clima;

import frba.utn.climalert.domain.clima.Clima;

public record ClimaDto(
    Long id,
    LocalidadDto localidad,
    TemperaturaDto temperatura
) {
  public static ClimaDto toClimaResponse(Clima clima) {
    return new ClimaDto(
        clima.getId(),
        clima.getLocalidad() == null ? null : new LocalidadDto(
            clima.getLocalidad().getNombre(),
            clima.getLocalidad().getRegion(),
            clima.getLocalidad().getPais()
        ),
        clima.getTemperatura() == null ? null : new TemperaturaDto(
            clima.getTemperatura().getTemperatura(),
            clima.getTemperatura().getCondicion(),
            clima.getTemperatura().getVientoKH(),
            clima.getTemperatura().getHumedad(),
            clima.getTemperatura().getPresion()
        )
    );
  }

  public record LocalidadDto(
      String nombre,
      String region,
      String pais
  ) {
  }

  public record TemperaturaDto(
      double temperatura,
      String condicion,
      double vientoKH,
      double humedad,
      double presion
  ) {
  }
}
