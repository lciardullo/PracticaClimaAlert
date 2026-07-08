package frba.utn.climalert.domain.clima;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Localidad {
  private String nombre;
  private String region;
  private String pais;
}
