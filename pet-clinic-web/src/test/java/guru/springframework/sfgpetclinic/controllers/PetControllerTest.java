package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.models.Owner;
import guru.springframework.sfgpetclinic.models.Pet;
import guru.springframework.sfgpetclinic.models.PetType;
import guru.springframework.sfgpetclinic.services.OwnerService;
import guru.springframework.sfgpetclinic.services.PetService;
import guru.springframework.sfgpetclinic.services.PetTypeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class PetControllerTest {

  @Mock
  PetService petService;

  @Mock
  OwnerService ownerService;

  @Mock
  PetTypeService petTypeService;

  @InjectMocks
  PetController controller;

  MockMvc mockMvc;

  Owner owner;
  Set<PetType> petTypes;

  @BeforeEach
  void setUp() {
    mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

    owner = Owner.builder().id(1L).build();

    petTypes = new HashSet<>(2);
    petTypes.add(PetType.builder().id(10L).name("Dog").build());
    petTypes.add(PetType.builder().id(20L).name("Cat").build());
  }

  @Test
  void initCreationForm() throws Exception {
    when(ownerService.findById(anyLong())).thenReturn(owner);
    when(petTypeService.findAll()).thenReturn(petTypes);

    mockMvc.perform(get("/owners/1/pets/new"))
        .andExpect(status().isOk())
        .andExpect(model().attributeExists("owner", "pet"))
        .andExpect(view().name("pets/createOrUpdatePetForm"));
  }

  @Test
  void processCreationForm() throws Exception {
    when(ownerService.findById(anyLong())).thenReturn(owner);
    when(petTypeService.findAll()).thenReturn(petTypes);

    mockMvc.perform(post("/owners/1/pets/new"))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("/owners/1"));
  }

  @Test
  void initUpdateForm() throws Exception {
    when(ownerService.findById(anyLong())).thenReturn(owner);
    when(petTypeService.findAll()).thenReturn(petTypes);
    when(petService.findById(anyLong())).thenReturn(Pet.builder().id(5L).build());

    mockMvc.perform(get("/owners/1/pets/10/edit"))
        .andExpect(status().isOk())
        .andExpect(model().attributeExists("owner", "pet"))
        .andExpect(view().name("pets/createOrUpdatePetForm"));
  }

  @Test
  void processUpdateForm() throws Exception {
    when(ownerService.findById(anyLong())).thenReturn(owner);
    when(petTypeService.findAll()).thenReturn(petTypes);

    mockMvc.perform(post("/owners/1/pets/10/edit"))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("/owners/1"));
  }
}