package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.models.Owner;
import guru.springframework.sfgpetclinic.services.OwnerService;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class OwnerControllerTest {

  @Mock
  OwnerService ownerService;

  @InjectMocks
  OwnerController controller;

  Set<Owner> owners;

  MockMvc mockMvc;

  @BeforeEach
  void setUp() {
    owners = new HashSet<>();
    owners.add(Owner.builder().id(1L).build());
    owners.add(Owner.builder().id(2L).build());

    mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
  }

//  @Test
//  void listOwners() throws Exception {
//    when(ownerService.findAll()).thenReturn(owners);
//
//    mockMvc.perform(get("/owners"))
//        .andExpect(status().isOk())
//        .andExpect(view().name("owners/index"))
//        .andExpect(model().attribute("owners", hasSize(2)));
//  }
//
//  @Test
//  void listOwnersByIndex() throws Exception {
//    when(ownerService.findAll()).thenReturn(owners);
//
//    mockMvc.perform(get("/owners/index"))
//        .andExpect(status().isOk())
//        .andExpect(view().name("owners/index"))
//        .andExpect(model().attribute("owners", hasSize(2)));
//  }

  @Test
  void findOwners() throws Exception {

    mockMvc.perform(get("/owners/find"))
        .andExpect(status().isOk())
        .andExpect(view().name("owners/findOwners"))
        .andExpect(model().attributeExists("owner"));

    Mockito.verifyZeroInteractions(ownerService);
  }

  @Test
  void processFindFormReturnMany() throws Exception {
    when(ownerService.findAllByLastNameLike(anyString())).thenReturn(new ArrayList<>(owners));

    mockMvc.perform(get("/owners"))
        .andExpect(status().isOk())
        .andExpect(view().name("owners/ownersList"))
        .andExpect(model().attribute("selections", hasSize(2)));
  }

  @Test
  void processFindFormReturnOne() throws Exception {

    Owner owner = Owner.builder().id(1L).build();

    when(ownerService.findAllByLastNameLike(anyString())).thenReturn(List.of(owner));

    mockMvc.perform(get("/owners"))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("/owners/1"));
  }

  @Test
  void showOwnerTest() throws Exception {
    Owner owner = Owner.builder().id(1L).build();

    when(ownerService.findById(anyLong())).thenReturn(owner);

    mockMvc.perform(get("/owners/1"))
        .andExpect(status().isOk())
        .andExpect(view().name("owners/ownerDetails"))
        .andExpect(model().attribute("owner", owner));
  }
}