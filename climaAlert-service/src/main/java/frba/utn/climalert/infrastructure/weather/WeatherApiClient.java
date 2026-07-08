package frba.utn.climalert.infrastructure.weather;

import frba.utn.climalert.domain.clima.Clima;
import frba.utn.climalert.domain.clima.Localidad;
import frba.utn.climalert.domain.clima.Temperatura;
import frba.utn.climalert.dto.externo.weatherDtoResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class WeatherApiClient {
  private final RestClient restClient = RestClient.builder().build();
  private final String baseUrl;
  private final String apiKey;
  private final String language;

  public WeatherApiClient(
      @Value("${services.weather.base-url}") String baseUrl,
      @Value("${services.weather.api-key}") String apiKey,
      @Value("${services.weather.lang:es}") String language
  ) {
    this.baseUrl = baseUrl;
    this.apiKey = apiKey;
    this.language = language;
  }

  public Clima getCurrentWeather(Localidad localidad) {
    weatherDtoResponse response = restClient.get()
        .uri(buildUri(localidad))
        .accept(MediaType.APPLICATION_JSON)
        .retrieve()
        .body(weatherDtoResponse.class);

    if (response == null) {
      throw new IllegalStateException("No se pudo obtener la respuesta de clima actual");
    }

    Localidad localidadResponse = new Localidad(
        response.location().name(),
        response.location().region(),
        response.location().country()
    );

    Temperatura temperatura = new Temperatura(
        response.current().temp_c(),
        response.current().condition().text(),
        response.current().wind_kph(),
        response.current().humidity(),
        response.current().pressure_mb()
    );

    return new Clima(localidadResponse, temperatura);
  }

  private String buildUri(Localidad localidad) {
    String query = buildQuery(localidad);
    return UriComponentsBuilder
        .fromUriString(baseUrl)
        .queryParam("q", query)
        .queryParam("lang", language)
        .queryParam("key", apiKey)
        .toUriString();
  }

  private String buildQuery(Localidad localidad) {
    if (localidad == null) {
      throw new IllegalArgumentException("La localidad es obligatoria");
    }

    StringBuilder query = new StringBuilder();
    appendPart(query, localidad.getNombre());
    appendPart(query, localidad.getRegion());
    appendPart(query, localidad.getPais());

    if (query.isEmpty()) {
      throw new IllegalArgumentException("La localidad debe tener al menos un dato para consultar el clima");
    }

    return query.toString();
  }

  private void appendPart(StringBuilder query, String value) {
    if (value == null || value.isBlank()) {
      return;
    }

    if (!query.isEmpty()) {
      query.append(',');
    }
    query.append(value.trim());
  }
}
