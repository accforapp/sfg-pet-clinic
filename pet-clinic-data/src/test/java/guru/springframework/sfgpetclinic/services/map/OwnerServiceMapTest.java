package guru.springframework.sfgpetclinic.services.map;

import guru.springframework.sfgpetclinic.models.Owner;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class OwnerServiceMapTest {

  OwnerServiceMap ownerServiceMap;
  Long ownerId = 1L;
  String lastName = "Smith";

  @BeforeEach
  void setUp() {
    ownerServiceMap = new OwnerServiceMap(new PetTypeMapService(), new PetServiceMap());
    ownerServiceMap.save(Owner.builder().id(ownerId).lastName(lastName).build());
  }

  @Test
  void findAll() {
    Set<Owner> owners = ownerServiceMap.findAll();

    Assertions.assertEquals(1, owners.size());
  }

  @Test
  void findById() {
    Owner owner = ownerServiceMap.findById(ownerId);

    Assertions.assertEquals(ownerId, owner.getId());
  }

  @Test
  void saveExistingId() {
    Long id = 2L;

    Owner savedOwner = ownerServiceMap.save(Owner.builder().id(id).build());

    Assertions.assertEquals(id, savedOwner.getId());
  }

  @Test
  void saveNoId() {
    Owner savedOwner = ownerServiceMap.save(new Owner());

    Assertions.assertNotNull(savedOwner);
    Assertions.assertNotNull(savedOwner.getId());
  }

  @Test
  void delete() {
    ownerServiceMap.delete(ownerServiceMap.findById(ownerId));

    Assertions.assertEquals(0, ownerServiceMap.findAll().size());
  }

  @Test
  void deleteById() {
    ownerServiceMap.deleteById(ownerId);

    Assertions.assertNull(ownerServiceMap.findById(ownerId));
  }

  @Test
  void findByLastName() {
    Owner owner = ownerServiceMap.findByLastName(lastName);

    Assertions.assertNotNull(owner);
    Assertions.assertEquals(ownerId, owner.getId());
  }

  @Test
  void findByLastNameNotFound() {
    Owner owner = ownerServiceMap.findByLastName("foo");

    Assertions.assertNull(owner);
  }
}