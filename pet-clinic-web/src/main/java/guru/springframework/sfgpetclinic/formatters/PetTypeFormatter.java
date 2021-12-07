package guru.springframework.sfgpetclinic.formatters;

import guru.springframework.sfgpetclinic.models.PetType;
import guru.springframework.sfgpetclinic.services.PetTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Collection;
import java.util.Locale;

@Slf4j
@Component
public class PetTypeFormatter implements Formatter<PetType> {

  private final PetTypeService petTypeService;

  public PetTypeFormatter(PetTypeService petTypeService) {
    this.petTypeService = petTypeService;
  }

  @Override
  public String print(PetType petType, Locale locale) {
    return petType.getName();
  }

  @Override
  public PetType parse(String s, Locale locale) throws ParseException {

      Collection<PetType> findPetTypes = petTypeService.findAll();

      return findPetTypes.stream()
          .filter(petType -> petType.getName().equals(s))
          .findAny()
          .orElseThrow(() -> new ParseException("type not found " + s, 0));
  }
}
