package guru.springframework.sfgpetclinic.models;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "types")
public class PetType extends BaseEntity {

  @Builder
  public PetType(Long id, String name) {
    super(id);
    this.name = name;
  }

  @Column(name = "name")
  private String name;

  @Override
  public String toString() {
    return name;
  }
}
