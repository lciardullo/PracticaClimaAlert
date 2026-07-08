package frba.utn.climalert.domain.adapter;

import frba.utn.climalert.domain.notificacion.Notificacion;

public interface NotificacionAdapter {
  void enviar(Notificacion notificacion);
}
