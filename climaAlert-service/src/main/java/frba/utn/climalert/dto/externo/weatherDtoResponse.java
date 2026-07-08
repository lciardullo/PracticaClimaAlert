package frba.utn.climalert.dto.externo;

public record weatherDtoResponse(
    Location location,
    Current current
) {

  public record Location(
      String name,
      String region,
      String country,
      double lat,
      double lon,
      String tz_id,
      long localtime_epoch,
      String localtime
  ) {
  }

  public record Current(
      long last_updated_epoch,
      String last_updated,
      double temp_c,
      double temp_f,
      int is_day,
      Condition condition,
      double wind_mph,
      double wind_kph,
      int wind_degree,
      String wind_dir,
      double pressure_mb,
      double pressure_in,
      double precip_mm,
      double precip_in,
      int humidity,
      int cloud,
      double feelslike_c,
      double feelslike_f,
      double windchill_c,
      double windchill_f,
      double heatindex_c,
      double heatindex_f,
      double dewpoint_c,
      double dewpoint_f,
      double vis_km,
      double vis_miles,
      double uv,
      double gust_mph,
      double gust_kph,
      int will_it_rain,
      int chance_of_rain,
      int will_it_snow,
      int chance_of_snow,
      double wetbulb_c,
      double wetbulb_f,
      double short_rad,
      double diff_rad,
      double dni,
      double gti
  ) {
  }

  public record Condition(
      String text,
      String icon,
      int code
  ) {
  }
}