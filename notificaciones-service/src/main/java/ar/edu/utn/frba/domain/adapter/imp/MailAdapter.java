package ar.edu.utn.frba.domain.adapter.imp;

import ar.edu.utn.frba.domain.adapter.NotificacionMailAdapter;
import ar.edu.utn.frba.domain.adapter.helper.SpringMailHelper;
import ar.edu.utn.frba.domain.notificacion.Notificacion;
import org.springframework.mail.SimpleMailMessage;

public class MailAdapter implements NotificacionMailAdapter {

    private final SpringMailHelper springMailHelper = new SpringMailHelper();

    @Override
    public void init() {
        springMailHelper.init();
    }

    @Override
    public SimpleMailMessage createMessage(Notificacion notificacion) {
        return springMailHelper.createMailMessage(notificacion);
    }

    @Override
    public void enviarNotificacion(Notificacion notificacion) {
        init();
        springMailHelper.send(createMessage(notificacion));
    }
}
