package ar.edu.utn.frba.domain.notificacion;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Destinatario {

    private String idExterno;
    private String nombre;

    public Destinatario(String idExterno, String nombre) {
        this.idExterno = idExterno;
        this.nombre = nombre;
    }
}
