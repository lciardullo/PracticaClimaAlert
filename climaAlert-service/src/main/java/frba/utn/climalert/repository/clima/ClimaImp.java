package frba.utn.climalert.repository.clima;

import frba.utn.climalert.domain.clima.Clima;
import frba.utn.climalert.utils.GeneradorIdSecuencial;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Repository
public class ClimaImp implements climaRepository {
  private final List<Clima> climas = new ArrayList<>();
  private final GeneradorIdSecuencial generadorIdClima = new GeneradorIdSecuencial();

  @Override
  public List<Clima> findAll() {
    return new ArrayList<>(climas);
  }

  @Override
  public Optional<Clima> findById(Long id) {
    return climas.stream().filter(clima -> clima.getId().equals(id)).findFirst();
  }

  @Override
  public Optional<Clima> findLatest() {
    return climas.stream().max(Comparator.comparing(Clima::getId));
  }

  @Override
  public Clima save(Clima clima) {
    if (clima.getId() == null) {
      clima.setId(generadorIdClima.siguiente());
      climas.add(clima);
      return clima;
    }

    delete(clima);
    climas.add(clima);
    return clima;
  }

  @Override
  public void delete(Clima clima) {
    if (clima.getId() == null) {
      return;
    }

    climas.removeIf(c -> c.getId().equals(clima.getId()));
  }
}
