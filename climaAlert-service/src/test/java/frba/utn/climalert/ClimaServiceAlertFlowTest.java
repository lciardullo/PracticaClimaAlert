package frba.utn.climalert;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import frba.utn.climalert.domain.adapter.ClimaAlertAdapter;
import frba.utn.climalert.domain.adapter.imp.NotificacionApiAdapter;
import frba.utn.climalert.domain.clima.Clima;
import frba.utn.climalert.domain.clima.Localidad;
import frba.utn.climalert.domain.clima.Temperatura;
import frba.utn.climalert.infrastructure.notification.NotificationClient;
import frba.utn.climalert.repository.clima.ClimaImp;
import frba.utn.climalert.service.ClimaServiceImp;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ClimaServiceAlertFlowTest {

  private HttpServer server;

  @AfterEach
  void tearDown() {
    if (server != null) {
      server.stop(0);
    }
  }

  @Test
  void enviaNotificacionesCuandoElClimaEsAlerta() throws Exception {
    List<String> destinatarios = List.of(
        "admin@clima.com",
        "emergencias@clima.com",
        "meteorologia@clima.com",
        "lucas252ciardu@gmail.com"
    );

    CountDownLatch latch = new CountDownLatch(destinatarios.size());
    List<String> requestBodies = new CopyOnWriteArrayList<>();
    int port = startNotificationServer(latch, requestBodies);

    ClimaAlertAdapter climaAlertAdapter = mock(ClimaAlertAdapter.class);
    when(climaAlertAdapter.solicitarRealTime(any(Localidad.class))).thenReturn(crearClimaEnAlerta());

    NotificationClient notificationClient = new NotificationClient(
        "http://localhost:" + port + "/api/v1/notificaciones",
        "ALERTA_CLIMATICA"
    );

    ClimaServiceImp climaService = new ClimaServiceImp(
        climaAlertAdapter,
        new NotificacionApiAdapter(notificationClient),
        new ClimaImp()
    );

    climaService.consultarYNotificar(new Localidad("CABA", "Buenos Aires", "Argentina"), destinatarios);

    assertTrue(latch.await(5, TimeUnit.SECONDS), "No llegaron todas las solicitudes al notification-service");
    assertEquals(destinatarios.size(), requestBodies.size());

    for (String destinatario : destinatarios) {
      assertTrue(
          requestBodies.stream().anyMatch(body -> body.contains("\"nombre\":\"" + destinatario + "\"")),
          "No se envio notificacion para " + destinatario
      );
      assertTrue(
          requestBodies.stream().anyMatch(body -> body.contains("\"valor\":\"" + destinatario + "\"")),
          "No se envio medio de contacto para " + destinatario
      );
    }

    assertTrue(
        requestBodies.stream().allMatch(body -> body.contains("\"tipo\":\"ALERTA_CLIMATICA\"")),
        "Todas las solicitudes deben salir con el tipo ALERTA_CLIMATICA"
    );
    assertTrue(
        requestBodies.stream().allMatch(body -> body.contains("Alerta climatica en CABA")),
        "El detalle de la alerta no llego al notification-service"
    );
  }

  private int startNotificationServer(CountDownLatch latch, List<String> requestBodies) throws IOException {
    server = HttpServer.create(new InetSocketAddress(0), 0);
    server.createContext("/api/v1/notificaciones", exchange -> handleNotificationRequest(exchange, latch, requestBodies));
    server.start();
    return server.getAddress().getPort();
  }

  private void handleNotificationRequest(HttpExchange exchange, CountDownLatch latch, List<String> requestBodies) throws IOException {
    try (InputStream requestBody = exchange.getRequestBody()) {
      requestBodies.add(new String(requestBody.readAllBytes(), StandardCharsets.UTF_8));
    }

    byte[] response = new byte[0];
    exchange.sendResponseHeaders(201, response.length);
    exchange.getResponseBody().write(response);
    exchange.close();
    latch.countDown();
  }

  private Clima crearClimaEnAlerta() {
    Localidad localidad = new Localidad("CABA", "Buenos Aires", "Argentina");
    Temperatura temperatura = new Temperatura(38.5, "Soleado", 12.0, 72.0, 1008.0);
    return new Clima(localidad, temperatura);
  }
}
