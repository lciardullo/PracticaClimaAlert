package frba.utn.climalert.domain.clima;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Clima {
  private Long id;
  private Localidad localidad;
  private Temperatura temperatura;

  public Clima(Localidad localidad, Temperatura temperatura) {
    this.localidad = localidad;
    this.temperatura = temperatura;
  }
}
