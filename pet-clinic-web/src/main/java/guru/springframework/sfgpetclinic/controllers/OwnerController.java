package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.models.Owner;
import guru.springframework.sfgpetclinic.services.OwnerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RequestMapping("/owners")
@Controller
public class OwnerController {
  private final OwnerService ownerService;

  public OwnerController(OwnerService ownerService) {
    this.ownerService = ownerService;
  }

  @InitBinder
  public void setAllowedFields(WebDataBinder dataBinder) {
    dataBinder.setDisallowedFields("id");
  }

//  @RequestMapping({"", "/", "/index", "/index.html"})
//  public String listOwners(Model model) {
//    model.addAttribute("owners", ownerService.findAll());
//    return "owners/index";
//  }

  @RequestMapping("/find")
  public String findOwners(Model model) {

    model.addAttribute("owner", Owner.builder().build());

    return "owners/findOwners";
  }

  @GetMapping
  public String processFindOwner(Owner owner, BindingResult result, Model model) {

    if (owner.getLastName() == null) {
      owner.setLastName("");
    }

    List<Owner> owners = ownerService.findAllByLastNameLike(owner.getLastName());

    if (owners.isEmpty()) {
      result.rejectValue("lastName", "notFound", "not found");

      return "owners/findOwners";
    } else if (owners.size() == 1) {
      owner = owners.get(0);

      return "redirect:/owners/" + owner.getId();
    } else {
      model.addAttribute("selections", owners);

      return "owners/ownersList";
    }

  }

  @GetMapping("/{ownerId}")
  public ModelAndView showOwner(@PathVariable("ownerId") Long id) {
    ModelAndView modelAndView = new ModelAndView("owners/ownerDetails");
    modelAndView.addObject(ownerService.findById(id));

    return modelAndView;
  }
}
