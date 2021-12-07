package guru.springframework.sfgpetclinic.models;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "owner")
public class Owner extends Person {

  @Builder
  public Owner(Long id, String firstName, String lastName, String address, String city, String telephone, Set<Pet> pets) {
    super(id, firstName, lastName);
    this.address = address;
    this.city = city;
    this.telephone = telephone;

    if (pets != null) {
      this.pets = pets;
    }
  }


  @Column(name = "address")
  private String address;

  @Column(name = "city")
  private String city;

  @Column(name = "telephone")
  private String telephone;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
  private Set<Pet> pets = new HashSet<>();

  public Pet getPet(String name) {
    return getPet(name, false);
  }

  public Pet getPet(String name, boolean ignoreNew) {
    String finalName = name.toLowerCase(Locale.ROOT);

    return pets.stream()
        .filter(pet -> !ignoreNew || !pet.isNew() && pet.getName().equalsIgnoreCase(finalName))
        .findAny().orElse(null);
  }

//  public Pet getPet(String name, boolean ignoreNew) {
//    name = name.toLowerCase(Locale.ROOT);
//
//    for (Pet pet : pets) {
//      if (!ignoreNew || pet.isNew()) {
//        String compName = pet.getName();
//        compName = compName.toLowerCase(Locale.ROOT);
//        if (compName.equals(name)) {
//          return pet;
//        }
//      }
//    }
//    return null;
//  }
}
