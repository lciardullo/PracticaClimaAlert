package frba.utn.climalert.infrastructure.notification;

import frba.utn.climalert.domain.notificacion.Notificacion;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class NotificationClient {
  private final RestClient restClient = RestClient.builder().build();
  private final String baseUrl;
  private final String notificationType;

  public NotificationClient(
      @Value("${services.notification.base-url}") String baseUrl,
      @Value("${services.notification.tipo}") String notificationType
  ) {
    this.baseUrl = baseUrl;
    this.notificationType = notificationType;
  }

  public void enviarNotificacion(Notificacion notificacion) {
    if (notificacion == null || notificacion.getDestinatarios() == null || notificacion.getDestinatarios().isEmpty()) {
      return;
    }

    for (String destinatario : notificacion.getDestinatarios()) {
      NotificationCreateRequest request = new NotificationCreateRequest(
          buildExternalId(notificacion, destinatario),
          destinatario,
          new MedioContactoPayload("EMAIL", destinatario),
          notificationType,
          buildDetalle(notificacion)
      );

      restClient.post()
          .uri(baseUrl)
          .body(request)
          .retrieve()
          .toBodilessEntity();
    }
  }

  private String buildExternalId(Notificacion notificacion, String destinatario) {
    String asunto = notificacion.getAsunto() != null ? notificacion.getAsunto().replace(' ', '-') : "alerta-climatica";
    return asunto + "-" + destinatario;
  }

  private String buildDetalle(Notificacion notificacion) {
    if (notificacion.getAsunto() == null || notificacion.getAsunto().isBlank()) {
      return notificacion.getMensaje();
    }

    return notificacion.getAsunto() + ". " + notificacion.getMensaje();
  }
}
