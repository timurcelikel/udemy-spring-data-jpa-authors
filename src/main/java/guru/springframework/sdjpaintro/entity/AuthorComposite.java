package guru.springframework.sdjpaintro.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;

@Entity
@IdClass(NameId.class)
public class AuthorComposite {

	@Id
	private String firstName;

	@Id
	private String lastName;

	private String country;

	public String getFirstName() {

		return firstName;
	}

	public void setFirstName(final String firstName) {

		this.firstName = firstName;
	}

	public String getLastName() {

		return lastName;
	}

	public void setLastName(final String lastName) {

		this.lastName = lastName;
	}

	public String getCountry() {

		return country;
	}

	public void setCountry(final String country) {

		this.country = country;
	}
}
