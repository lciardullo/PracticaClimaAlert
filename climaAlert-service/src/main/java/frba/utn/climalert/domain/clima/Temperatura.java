package frba.utn.climalert.domain.clima;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Temperatura {
  private double temperatura;
  private String condicion;
  private double vientoKH;
  private double humedad;
  private double presion;
}
