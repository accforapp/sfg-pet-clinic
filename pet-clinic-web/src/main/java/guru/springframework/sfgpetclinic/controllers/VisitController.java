package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.models.Pet;
import guru.springframework.sfgpetclinic.models.Visit;
import guru.springframework.sfgpetclinic.services.PetService;
import guru.springframework.sfgpetclinic.services.VisitService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.beans.PropertyEditorSupport;
import java.time.LocalDate;

@Controller
@RequestMapping("/owners/{ownerId}/pets/{petId}")
public class VisitController {

  private final VisitService visitService;
  private final PetService petService;

  public VisitController(VisitService visitService, PetService petService) {
    this.visitService = visitService;
    this.petService = petService;
  }

  @InitBinder
  public void setAllowedFields(WebDataBinder dataBinder) {
    dataBinder.setDisallowedFields("id");
//
//    dataBinder.registerCustomEditor(LocalDate.class, new PropertyEditorSupport() {
//      @Override
//      public void setAsText(String text) {
//        setValue(LocalDate.parse(text));
//      }
//    });
  }

  @ModelAttribute("visit")
  public Visit loadPetWithVisit(@PathVariable Long petId, Model model) {
    Pet pet = petService.findById(petId);
    model.addAttribute("pet", pet);

    Visit visit = new Visit();
    pet.getVisits().add(visit);
    visit.setPet(pet);

    return visit;
  }

  @GetMapping("/visits/new")
  public String initNewVisitForm(@PathVariable Long petId, Model model) {
    return "pets/createOrUpdateVisitForm";
  }

  @PostMapping("/visits/new")
  public String processNewVisitForm(@Valid Visit visit, BindingResult result) {
    if (result.hasErrors()) {
      return "/pets/createOrUpdateVisitForm";
    } else {
      visitService.save(visit);
      return "redirect:/owners/" + visit.getPet().getOwner().getId();
    }
  }
}
