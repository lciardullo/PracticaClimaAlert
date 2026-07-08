package ar.edu.utn.frba.domain.notificacion;

import ar.edu.utn.frba.domain.adapter.NotificacionTelefonoAdapter;

public class EstrategiaNotificacionTelefono implements IEstrategiaNotificacion{

    private NotificacionTelefonoAdapter notificacionTelefonoAdapter;

    public EstrategiaNotificacionTelefono(NotificacionTelefonoAdapter notificacionTelefonoAdapter) {
        this.notificacionTelefonoAdapter = notificacionTelefonoAdapter;
    }
    @Override
    public void enviarNotificacion(Notificacion notificacion) {
        notificacionTelefonoAdapter.enviarNotificacion(notificacion);
    }
}
