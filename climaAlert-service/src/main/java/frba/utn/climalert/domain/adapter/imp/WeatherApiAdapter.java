package frba.utn.climalert.domain.adapter.imp;

import frba.utn.climalert.domain.adapter.ClimaAlertAdapter;
import frba.utn.climalert.domain.clima.Clima;
import frba.utn.climalert.domain.clima.Localidad;
import frba.utn.climalert.infrastructure.weather.WeatherApiClient;
import org.springframework.stereotype.Component;

@Component
public class WeatherApiAdapter implements ClimaAlertAdapter {
  private final WeatherApiClient weatherApiClient;

  public WeatherApiAdapter(WeatherApiClient weatherApiClient) {
    this.weatherApiClient = weatherApiClient;
  }

  @Override
  public Clima solicitarRealTime(Localidad localidad) {
    return weatherApiClient.getCurrentWeather(localidad);
  }
}
