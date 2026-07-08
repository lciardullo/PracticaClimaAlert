package ar.edu.utn.frba.domain.notificacion;

import ar.edu.utn.frba.domain.adapter.NotificacionMailAdapter;

public class EstrategiaNotificacionMail implements IEstrategiaNotificacion {

    private final NotificacionMailAdapter notificacionMailAdapter;

    public EstrategiaNotificacionMail(NotificacionMailAdapter notificacionMailAdapter) {
        this.notificacionMailAdapter = notificacionMailAdapter;
    }

    @Override
    public void enviarNotificacion(Notificacion notificacion) {
        notificacionMailAdapter.enviarNotificacion(notificacion);
    }
}
