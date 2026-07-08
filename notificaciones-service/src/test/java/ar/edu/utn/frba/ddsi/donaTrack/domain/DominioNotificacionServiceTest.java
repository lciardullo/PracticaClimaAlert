package ar.edu.utn.frba.ddsi.donaTrack.domain;

import ar.edu.utn.frba.domain.enumeracion.TipoMedio;
import ar.edu.utn.frba.domain.enumeracion.TipoNotificacion;
import ar.edu.utn.frba.domain.medioContacto.MedioContacto;
import ar.edu.utn.frba.domain.notificacion.*;
import ar.edu.utn.frba.dtos.notificacion.NotificationCreateRequest;
import ar.edu.utn.frba.service.notificacionService.imp.NotificacionServiceImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class DominioNotificacionServiceTest {

    NotificacionServiceSpy service;

    @BeforeEach
    void setUp() {
        service = new NotificacionServiceSpy();
    }


    @Test
    @DisplayName("TEST: NotiSender inicializa las estrategias para todos los medios")
    void notiSenderInicializaLasEstrategiasParaTodosLosMedios() throws Exception {
        NotiSender notiSender = new NotiSender();
        Field field = NotiSender.class.getDeclaredField("estrategiasNotificacion");
        field.setAccessible(true);
        @SuppressWarnings("unchecked")
        Map<TipoMedio, IEstrategiaNotificacion> estrategias =
                (Map<TipoMedio, IEstrategiaNotificacion>) field.get(notiSender);

        assertNotNull(estrategias.get(TipoMedio.EMAIL));
        assertNotNull(estrategias.get(TipoMedio.TELEFONO));
        assertNotNull(estrategias.get(TipoMedio.WHATSAPP));
    }

    @Test
    @DisplayName("TEST: Servicio notifica donante sin interaccion")
    void servicioNotificaDonanteSinInteraccion() {
        MedioContacto medio = new MedioContacto(TipoMedio.EMAIL, "lucas@utn.com");

        NotificationCreateRequest request = new NotificationCreateRequest(
                "1",
                "Lucas",
                medio,
                TipoNotificacion.DONANTE_SIN_INTERACCION,
                null
        );

        Notificacion notificacion = service.crearNotificacion(request);
        service.enviarNotificacion(notificacion);

        assertNotNull(service.notificacionEnviada);
        assertEquals("1", service.notificacionEnviada.getDestinatario().getIdExterno());
        assertEquals("Lucas", service.notificacionEnviada.getDestinatario().getNombre());
        assertSame(medio, service.notificacionEnviada.getMedio());
        assertEquals(
                "Hola Lucas, pasaron 20 dias desde tu ultima interaccion.",
                service.notificacionEnviada.getMensaje()
        );
    }

    @Test
    @DisplayName("TEST: Servicio notifica entidad beneficiaria con donacion asignada")
    void servicioNotificaEntidadBeneficiariaConDonacionAsignada() {
        MedioContacto medio = new MedioContacto(TipoMedio.EMAIL, "entidad@utn.com");

        NotificationCreateRequest request = new NotificationCreateRequest(
                "2",
                "Fundacion",
                medio,
                TipoNotificacion.ENTIDAD_DONACION_ASIGNADA,
                null
        );

        Notificacion notificacion = service.crearNotificacion(request);
        service.enviarNotificacion(notificacion);

        assertNotNull(service.notificacionEnviada);
        assertEquals("2", service.notificacionEnviada.getDestinatario().getIdExterno());
        assertEquals("Fundacion", service.notificacionEnviada.getDestinatario().getNombre());
        assertSame(medio, service.notificacionEnviada.getMedio());
        assertEquals(
                "Hola Fundacion, se te asigno una donacion.",
                service.notificacionEnviada.getMensaje()
        );
    }

    @Test
    @DisplayName("TEST: Servicio notifica donante con donacion asignada")
    void servicioNotificaDonanteConDonacionAsignada() {
        MedioContacto medio = new MedioContacto(TipoMedio.TELEFONO, "1123456789");

        NotificationCreateRequest request = new NotificationCreateRequest(
                "3",
                "Maria",
                medio,
                TipoNotificacion.DONANTE_DONACION_ASIGNADA,
                null
        );

        Notificacion notificacion = service.crearNotificacion(request);
        service.enviarNotificacion(notificacion);

        assertNotNull(service.notificacionEnviada);
        assertEquals("3", service.notificacionEnviada.getDestinatario().getIdExterno());
        assertEquals("Maria", service.notificacionEnviada.getDestinatario().getNombre());
        assertSame(medio, service.notificacionEnviada.getMedio());
        assertEquals(
                "Hola Maria, Tu donacion fue asignada a una entidad beneficiaria.",
                service.notificacionEnviada.getMensaje()
        );
    }

    @Test
    @DisplayName("TEST: Servicio notifica donante que cumplio mision")
    void servicioNotificaDonanteQueCumplioMision() {
        MedioContacto medio = new MedioContacto(TipoMedio.WHATSAPP, "1123456789");

        NotificationCreateRequest request = new NotificationCreateRequest(
                "4",
                "Pedro",
                medio,
                TipoNotificacion.DONANTE_MISION_CUMPLIDA,
                null
        );

        Notificacion notificacion = service.crearNotificacion(request);
        service.enviarNotificacion(notificacion);

        assertNotNull(service.notificacionEnviada);
        assertEquals("4", service.notificacionEnviada.getDestinatario().getIdExterno());
        assertEquals("Pedro", service.notificacionEnviada.getDestinatario().getNombre());
        assertSame(medio, service.notificacionEnviada.getMedio());
        assertEquals(
                "Hola Pedro, cumpliste una nueva mision.",
                service.notificacionEnviada.getMensaje()
        );
    }

    @Test
    @DisplayName("TEST: Servicio notifica donante que cambia de categoria")
    void servicioNotificaDonanteQueCambiaDeCategoria() {
        MedioContacto medio = new MedioContacto(TipoMedio.WHATSAPP, "1123456789");

        NotificationCreateRequest request = new NotificationCreateRequest(
                "5",
                "Ana",
                medio,
                TipoNotificacion.DONANTE_CAMBIO_CATEGORIA,
                null
        );

        Notificacion notificacion = service.crearNotificacion(request);
        service.enviarNotificacion(notificacion);

        assertNotNull(service.notificacionEnviada);
        assertEquals("5", service.notificacionEnviada.getDestinatario().getIdExterno());
        assertEquals("Ana", service.notificacionEnviada.getDestinatario().getNombre());
        assertSame(medio, service.notificacionEnviada.getMedio());
        assertEquals(
                "Hola Ana, cambiaste de categoria.",
                service.notificacionEnviada.getMensaje()
        );
    }

    private static class NotificacionServiceSpy extends NotificacionServiceImp {

        private Notificacion notificacionEnviada;

        @Override
        public void enviarNotificacion(Notificacion notificacion) {
            this.notificacionEnviada = notificacion;
        }
    }
}
