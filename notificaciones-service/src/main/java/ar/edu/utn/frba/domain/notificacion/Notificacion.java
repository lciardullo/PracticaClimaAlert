package ar.edu.utn.frba.domain.notificacion;

import java.util.Date;

import ar.edu.utn.frba.domain.medioContacto.MedioContacto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Notificacion {

    private String id;
    private Destinatario destinatario;
    private MedioContacto medio;
    private String mensaje;
    private Date fechaCreacion;
    private Date fechaHoraLeida;
    private Boolean leida;

    public Notificacion(
        Destinatario destinatario,
        String mensaje,
        MedioContacto medio
    ) {
        this.id = id;
        this.destinatario = destinatario;
        this.medio = medio;
        this.mensaje = mensaje;
        this.fechaCreacion = new Date();
        this.fechaHoraLeida = null;
        this.leida = false;
    }

    public void marcarComoLeida() {
        this.leida = true;
    }
}
