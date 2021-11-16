package guru.springframework.sfgpetclinic.services.springdatajpa;

import guru.springframework.sfgpetclinic.models.Owner;
import guru.springframework.sfgpetclinic.repositories.OwnerRepository;
import guru.springframework.sfgpetclinic.repositories.PetRepository;
import guru.springframework.sfgpetclinic.repositories.PetTypeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class OwnerSDJpaServiceTest {

  @Mock
  OwnerRepository ownerRepository;

  @Mock
  PetRepository petRepository;

  @Mock
  PetTypeRepository petTypeRepository;

  @InjectMocks
  OwnerSDJpaService ownerSDJpaService;

  @BeforeEach
  void setUp() {
  }

  @Test
  void findAll() {
    Set<Owner> returnOwners = new HashSet<>();
    returnOwners.add(Owner.builder().id(1L).build());
    returnOwners.add(Owner.builder().id(2L).build());
    returnOwners.add(Owner.builder().id(3L).build());

    Mockito.when(ownerRepository.findAll()).thenReturn(returnOwners);

    Set<Owner> owners = ownerSDJpaService.findAll();

    Assertions.assertEquals(3, owners.size());
  }

  @Test
  void findById() {
    Optional<Owner> returnOwner = Optional.ofNullable(Owner.builder().id(1L).build());

    Mockito.when(ownerRepository.findById(Mockito.any())).thenReturn(returnOwner);

    Assertions.assertNotNull(ownerSDJpaService.findById(1L));
  }

  @Test
  void findByIdNotFound() {
    Optional<Owner> returnOwner = Optional.empty();

    Mockito.when(ownerRepository.findById(Mockito.any())).thenReturn(returnOwner);

    Assertions.assertNull(ownerSDJpaService.findById(1L));
  }

  @Test
  void save() {
    Long id = 1L;
    Owner ownerToSave = Owner.builder().id(id).build();

    Mockito.when(ownerRepository.save(Mockito.any())).thenReturn(ownerToSave);
    Mockito.when(ownerRepository.findAll()).thenReturn(Set.of(ownerToSave));

    Owner savedOwner = ownerSDJpaService.save(ownerToSave);

    Assertions.assertNotNull(savedOwner);
    Assertions.assertEquals(id, savedOwner.getId());
    Assertions.assertEquals(1, ownerSDJpaService.findAll().size());

  }

  @Test
  void delete() {
    ownerSDJpaService.delete(Owner.builder().id(1L).build());

    Mockito.verify(ownerRepository).delete(Mockito.any());
  }

  @Test
  void deleteById() {
    ownerSDJpaService.deleteById(1L);

    Mockito.verify(ownerRepository).deleteById(Mockito.any());
  }

  @Test
  void findByLastName() {
    String lastName = "Smith";

    Owner returnOwner = Owner.builder().id(1L).lastName(lastName).build();

    Mockito.when(ownerRepository.findByLastName(Mockito.any())).thenReturn(returnOwner);

    Owner smith = ownerSDJpaService.findByLastName(lastName);

    Assertions.assertEquals(lastName, smith.getLastName());

    Mockito.verify(ownerRepository).findByLastName(Mockito.any());

  }
}