package frba.utn.climalert.repository.clima;

import frba.utn.climalert.domain.clima.Clima;

import java.util.List;
import java.util.Optional;

public interface climaRepository {
  List<Clima> findAll();

  Optional<Clima> findById(Long id);

  Optional<Clima> findLatest();

  Clima save(Clima clima);

  void delete(Clima clima);
}
