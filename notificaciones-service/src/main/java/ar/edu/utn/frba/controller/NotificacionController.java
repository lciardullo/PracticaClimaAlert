package ar.edu.utn.frba.controller;

import ar.edu.utn.frba.domain.notificacion.Notificacion;
import ar.edu.utn.frba.dtos.notificacion.NotificationCreateRequest;
import ar.edu.utn.frba.service.notificacionService.Interface.NotificacionService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/notificaciones")
public class NotificacionController {

    private final NotificacionService notificacionService;

    public NotificacionController(NotificacionService notificacionService) {
        this.notificacionService = notificacionService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void crearNotificacion(@RequestBody NotificationCreateRequest request) {
        Notificacion notificacion = notificacionService.crearNotificacion(request);
        notificacionService.enviarNotificacion(notificacion);
    }
}
