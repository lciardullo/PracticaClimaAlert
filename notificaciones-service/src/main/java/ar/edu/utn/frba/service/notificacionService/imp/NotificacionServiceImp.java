package ar.edu.utn.frba.service.notificacionService.imp;

import ar.edu.utn.frba.domain.enumeracion.TipoNotificacion;
import ar.edu.utn.frba.domain.notificacion.*;
import ar.edu.utn.frba.dtos.notificacion.NotificationCreateRequest;
import ar.edu.utn.frba.service.notificacionService.Interface.NotificacionService;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

@Service
public class NotificacionServiceImp implements NotificacionService {

    private final NotiSender notiSender;
    private final ResourceBundle messages;

    private final Map<TipoNotificacion, String> clavesMensajes = Map.ofEntries(
            Map.entry(TipoNotificacion.DONANTE_SIN_INTERACCION, "notificacion.mensaje.donante.veinteDiasSinInteraccion"),
            Map.entry(TipoNotificacion.ENTIDAD_DONACION_ASIGNADA, "notificacion.mensaje.entidadBeneficiaria.asignoDonacion"),
            Map.entry(TipoNotificacion.DONANTE_DONACION_ASIGNADA, "notificacion.mensaje.donante.asignoDonacion"),
            Map.entry(TipoNotificacion.ENTIDAD_RUTA_INICIADA, "notificacion.mensaje.entidadBeneficiaria.rutaIniciada"),
            Map.entry(TipoNotificacion.DONANTE_RUTA_INICIADA, "notificacion.mensaje.donante.rutaIniciada"),
            Map.entry(TipoNotificacion.ENTIDAD_ENTREGA_EXITOSA, "notificacion.mensaje.entidadBeneficiaria.entregaExitosa"),
            Map.entry(TipoNotificacion.DONANTE_ENTREGA_EXITOSA, "notificacion.mensaje.donante.entregaExitosa"),
            Map.entry(TipoNotificacion.ENTIDAD_ENTREGA_FALLIDA, "notificacion.mensaje.entidadBeneficiaria.entregaFallida"),
            Map.entry(TipoNotificacion.DONANTE_ENTREGA_FALLIDA, "notificacion.mensaje.donante.entregaFallida"),
            Map.entry(TipoNotificacion.DONANTE_MISION_CUMPLIDA, "notificacion.mensaje.donante.cumplioMision"),
            Map.entry(TipoNotificacion.DONANTE_CAMBIO_CATEGORIA, "notificacion.mensaje.donante.cambioCategoria"),
            Map.entry(TipoNotificacion.ALERTA_CLIMATICA, "notificacion.mensaje.alerta.climatica")
    );

    public NotificacionServiceImp() {
        this.notiSender = new NotiSender();
        this.messages = ResourceBundle.getBundle("messages", Locale.of("es"));
    }

    @Override
    public Notificacion crearNotificacion(NotificationCreateRequest request) {
        String mensaje = obtenerMensajeSegunTipo(request.tipo(), request.nombre(), request.detalle());

        Destinatario destinatario = new Destinatario(
                request.idExterno(),
                request.nombre()
        );

        return new Notificacion(
                destinatario,
                mensaje,
                request.medioContacto()
        );
    }
    public void enviarNotificacion(Notificacion notificacion) {
        notiSender.enviarNotificacion(notificacion);
    }

    private String obtenerMensajeSegunTipo(TipoNotificacion tipo, String nombre, String detalle) {
        String clave = clavesMensajes.get(tipo);

        if (clave == null) {
            throw new IllegalArgumentException("Tipo de notificacion no soportado: " + tipo);
        }

        return MessageFormat.format(messages.getString(clave), nombre, detalle == null ? "" : detalle);
    }
}
