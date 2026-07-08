package ar.edu.utn.frba.domain.adapter;

import ar.edu.utn.frba.domain.notificacion.Notificacion;
import com.twilio.rest.api.v2010.account.MessageCreator;

public interface NotificacionTelefonoAdapter {
    void init();
    MessageCreator createMessage(Notificacion notificacion);
    void enviarNotificacion(Notificacion notificacion);
}
