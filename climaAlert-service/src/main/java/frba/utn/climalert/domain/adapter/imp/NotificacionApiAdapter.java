package frba.utn.climalert.domain.adapter.imp;

import frba.utn.climalert.domain.adapter.NotificacionAdapter;
import frba.utn.climalert.domain.notificacion.Notificacion;
import frba.utn.climalert.infrastructure.notification.NotificationClient;
import org.springframework.stereotype.Component;

@Component
public class NotificacionApiAdapter implements NotificacionAdapter {
  private final NotificationClient notificationClient;

  public NotificacionApiAdapter(NotificationClient notificationClient) {
    this.notificationClient = notificationClient;
  }

  @Override
  public void enviar(Notificacion notificacion) {
    notificationClient.enviarNotificacion(notificacion);
  }
}
