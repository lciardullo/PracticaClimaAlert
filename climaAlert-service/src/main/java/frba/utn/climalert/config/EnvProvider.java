package frba.utn.climalert.config;

import io.github.cdimascio.dotenv.Dotenv;

public final class EnvProvider {

  private static final Dotenv DOTENV = loadDotenv();

  private EnvProvider() {
  }

  public static String getRequired(String key) {
    String value = get(key);
    if (value == null || value.isBlank()) {
      throw new IllegalStateException("Falta configurar la variable " + key + " en .env o en el entorno");
    }
    return value;
  }

  public static String get(String key) {
    String dotenvValue = DOTENV.get(key);
    if (dotenvValue != null && !dotenvValue.isBlank()) {
      return dotenvValue;
    }

    String envValue = System.getenv(key);
    if (envValue != null && !envValue.isBlank()) {
      return envValue;
    }

    return null;
  }

  private static Dotenv loadDotenv() {
    Dotenv dotenv = Dotenv.configure()
        .ignoreIfMissing()
        .load();

    if (!dotenv.entries().iterator().hasNext()) {
      dotenv = Dotenv.configure()
          .directory("notificaciones-service")
          .ignoreIfMissing()
          .load();
    }

    return dotenv;
  }

}

