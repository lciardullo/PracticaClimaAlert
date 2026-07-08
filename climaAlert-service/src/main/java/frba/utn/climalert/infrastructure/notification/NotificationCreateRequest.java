package frba.utn.climalert.infrastructure.notification;

public record NotificationCreateRequest(
    String idExterno,
    String nombre,
    MedioContactoPayload medioContacto,
    String tipo,
    String detalle
) {
}
