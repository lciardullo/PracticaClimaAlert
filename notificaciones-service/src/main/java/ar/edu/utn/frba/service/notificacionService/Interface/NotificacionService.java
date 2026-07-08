package ar.edu.utn.frba.service.notificacionService.Interface;


import ar.edu.utn.frba.domain.notificacion.Notificacion;
import ar.edu.utn.frba.dtos.notificacion.NotificationCreateRequest;

public interface NotificacionService {
    Notificacion crearNotificacion(NotificationCreateRequest request);

    void enviarNotificacion(Notificacion notificacion);
}
