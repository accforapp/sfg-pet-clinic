package guru.springframework.sfgpetclinic.services.map;

import guru.springframework.sfgpetclinic.models.BaseEntity;

import java.util.*;

public abstract class AbstractMapService<T extends BaseEntity, ID extends Long> {
  private final Map<Long, T> storage = new HashMap<>();

  public Set<T> findAll() {
    return new HashSet<>(storage.values());
  }
  public T findById(ID id) {
    return storage.get(id);
  }
  public T save(T object) {
    if (object != null) {
      if (object.getId() == null) {
        object.setId(getNextId());
      }
      storage.put(object.getId(), object);
    } else {
      throw new RuntimeException("Object cannot be null");
    }
    return object;
  }
  public void delete(T object) {
    storage.entrySet().removeIf(set -> object.equals(set.getValue()));
  }
  public void deleteById(ID id) {
    storage.remove(id);
  }

  private Long getNextId() {
    if (storage.keySet().size() == 0) {
      return 1L;
    }
    return Collections.max(storage.keySet()) + 1;
  }
}
