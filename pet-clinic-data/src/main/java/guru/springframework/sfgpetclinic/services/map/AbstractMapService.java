package guru.springframework.sfgpetclinic.services.map;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public abstract class AbstractMapService<T, ID> {
  private final Map<ID, T> storage = new HashMap<>();

  public Set<T> findAll() {
    return new HashSet<>(storage.values());
  }
  public T findById(ID id) {
    return storage.get(id);
  }
  public T save(ID id, T object) {
    storage.put(id, object);
    return object;
  }
  public void delete(T object) {
    storage.entrySet().removeIf(set -> object.equals(set.getValue()));
  }
  public void deleteById(ID id) {
    storage.remove(id);
  }
}
