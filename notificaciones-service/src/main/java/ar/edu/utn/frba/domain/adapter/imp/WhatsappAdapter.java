package ar.edu.utn.frba.domain.adapter.imp;

import ar.edu.utn.frba.domain.adapter.NotificacionTelefonoAdapter;
import ar.edu.utn.frba.domain.adapter.helper.TwilioHelper;
import ar.edu.utn.frba.domain.notificacion.Notificacion;
import com.twilio.rest.api.v2010.account.MessageCreator;

public class WhatsappAdapter implements NotificacionTelefonoAdapter {

    private final TwilioHelper twilioSender = new TwilioHelper();

    @Override
    public void init() {
        twilioSender.init();
    }

    @Override
    public MessageCreator createMessage(Notificacion notificacion) {
        return twilioSender.createWhatsappMessage(notificacion);
    }

    @Override
    public void enviarNotificacion(Notificacion notificacion) {
        init();
        createMessage(notificacion).create();
    }
}
