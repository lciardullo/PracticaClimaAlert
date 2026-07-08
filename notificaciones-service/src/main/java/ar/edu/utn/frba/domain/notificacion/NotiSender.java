package ar.edu.utn.frba.domain.notificacion;

import ar.edu.utn.frba.domain.adapter.imp.MailAdapter;
import ar.edu.utn.frba.domain.adapter.imp.SmsAdapter;
import ar.edu.utn.frba.domain.adapter.imp.WhatsappAdapter;
import ar.edu.utn.frba.domain.enumeracion.TipoMedio;

import java.util.HashMap;
import java.util.Map;

public class NotiSender {

    private final Map<TipoMedio, IEstrategiaNotificacion> estrategiasNotificacion;

    public NotiSender() {
        this.estrategiasNotificacion = new HashMap<>();

        estrategiasNotificacion.put(TipoMedio.EMAIL, new EstrategiaNotificacionMail(new MailAdapter()));
        estrategiasNotificacion.put(TipoMedio.WHATSAPP, new EstrategiaNotificacionTelefono(new WhatsappAdapter()));
        estrategiasNotificacion.put(TipoMedio.TELEFONO, new EstrategiaNotificacionTelefono(new SmsAdapter()));
    }

    public void enviarNotificacion(Notificacion notificacion){
        TipoMedio tipo = notificacion.getMedio().getTipo();
        IEstrategiaNotificacion estrategia = estrategiasNotificacion.get(tipo);
        if (estrategia == null) {
            throw new IllegalArgumentException("No hay estrategia de notificacion para el medio " + tipo);
        }
        estrategia.enviarNotificacion(notificacion);
    }
}
