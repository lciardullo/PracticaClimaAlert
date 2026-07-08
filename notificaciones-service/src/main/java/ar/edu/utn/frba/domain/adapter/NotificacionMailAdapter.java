package ar.edu.utn.frba.domain.adapter;

import ar.edu.utn.frba.domain.notificacion.Notificacion;
import org.springframework.mail.SimpleMailMessage;

public interface NotificacionMailAdapter {
    void init();
    SimpleMailMessage createMessage(Notificacion notificacion);
    void enviarNotificacion(Notificacion notificacion);
}
