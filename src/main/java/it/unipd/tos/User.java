////////////////////////////////////////////////////////////////////
// ALBERTO SINIGAGLIA 1193384
////////////////////////////////////////////////////////////////////
package it.unipd.tos;

import java.util.Objects;

public class User {
  private final String name;
  private final String surname;
  private final int age;

  public User(String name, String surname, int age) throws IllegalArgumentException {
    if (age < 0) {
      throw new IllegalArgumentException("Età inferiore a zero");
    } else if(name == null || surname == null){
      throw new IllegalArgumentException("nome e cognome non possono essere nulli");
    }
    this.name = name;
    this.surname = surname;
    this.age = age;
  }

  public String getSurname() {
    return surname;
  }

  public String getName() {
    return name;
  }

  public int getAge() {
    return age;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null) {
      return false;
    }

    if(getClass() != o.getClass()){
      return false;
    }

    User user = (User) o;

    if (age != user.age) {
      return false;
    }
    if (!Objects.equals(name, user.name)) {
      return false;
    }
    return Objects.equals(surname, user.surname);
  }

  @Override
  public int hashCode() {
    int result = name.hashCode();
    result = 31 * result + surname.hashCode();
    result = 31 * result + age;
    return result;
  }
}
