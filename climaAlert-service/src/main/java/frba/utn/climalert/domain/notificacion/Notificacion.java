package frba.utn.climalert.domain.notificacion;

import frba.utn.climalert.domain.clima.Clima;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Notificacion {
  private List<String> destinatarios;
  private String asunto;
  private String mensaje;
  private Clima clima;
}
