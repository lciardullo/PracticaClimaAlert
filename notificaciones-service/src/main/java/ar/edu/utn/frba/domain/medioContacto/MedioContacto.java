package ar.edu.utn.frba.domain.medioContacto;

import ar.edu.utn.frba.domain.enumeracion.TipoMedio;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MedioContacto {

    private TipoMedio tipo;
    private String valor;

    public MedioContacto(TipoMedio tipo, String valor) {
        this.tipo = tipo;
        this.valor = valor;
    }
}
