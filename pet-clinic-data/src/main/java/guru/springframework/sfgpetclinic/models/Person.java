package guru.springframework.sfgpetclinic.models;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass
public class Person extends BaseEntity {

  public Person(Long id, String firstName, String lastName) {
    super(id);
    this.firstName = firstName;
    this.lastName = lastName;
  }

  @Column(name = "first_name")
  private String firstName;

  @Column(name = "last_name")
  private String lastName;
}
