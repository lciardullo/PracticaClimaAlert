package ar.edu.utn.frba.domain.adapter.helper;

import ar.edu.utn.frba.config.EnvProvider;
import ar.edu.utn.frba.domain.notificacion.Notificacion;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;

public class TwilioHelper {

    private static boolean initialized = false;

    public void init() {
        if (initialized) {
            return;
        }

        String accountSid = EnvProvider.getRequired("TWILIO_ACCOUNT_SID");
        String authToken = EnvProvider.getRequired("TWILIO_AUTH_TOKEN");
        Twilio.init(accountSid, authToken);
        initialized = true;
    }

    public MessageCreator createSmsMessage(Notificacion notificacion) {
        return Message.creator(
                new PhoneNumber(notificacion.getMedio().getValor()),
                new PhoneNumber(getPhoneNumber()),
                notificacion.getMensaje()
        );
    }

    public MessageCreator createWhatsappMessage(Notificacion notificacion) {
        return Message.creator(
                new PhoneNumber(asWhatsappAddress(notificacion.getMedio().getValor())),
                new PhoneNumber(asWhatsappAddress(getWhatsappNumber())),
                notificacion.getMensaje()
        );
    }

    public String getPhoneNumber() {
        return EnvProvider.getRequired("TWILIO_PHONE_NUMBER");
    }

    public String getWhatsappNumber() {
        String whatsappNumber = EnvProvider.get("TWILIO_WHATSAPP_NUMBER");
        if (whatsappNumber != null && !whatsappNumber.isBlank()) {
            return whatsappNumber;
        }
        return getPhoneNumber();
    }

    public String asWhatsappAddress(String value) {
        return value.startsWith("whatsapp:") ? value : "whatsapp:" + value;
    }
}
