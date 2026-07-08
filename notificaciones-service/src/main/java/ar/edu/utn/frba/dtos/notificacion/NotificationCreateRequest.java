package ar.edu.utn.frba.dtos.notificacion;

import ar.edu.utn.frba.domain.enumeracion.TipoNotificacion;
import ar.edu.utn.frba.domain.medioContacto.MedioContacto;

public record NotificationCreateRequest(
        String idExterno,
        String nombre,
        MedioContacto medioContacto,
        TipoNotificacion tipo,
        String detalle
) {}
