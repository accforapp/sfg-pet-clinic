package guru.springframework.sfgpetclinic.services;

import guru.springframework.sfgpetclinic.models.Owner;

import java.util.List;

public interface OwnerService extends CrudService<Owner, Long> {
  Owner findByLastName(String lastName);
  List<Owner> findAllByLastNameLike(String lastName);
}
