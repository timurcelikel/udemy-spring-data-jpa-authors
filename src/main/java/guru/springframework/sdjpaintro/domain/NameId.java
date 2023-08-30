package guru.springframework.sdjpaintro.domain;

import java.io.Serializable;
import java.util.Objects;

public class NameId implements Serializable {

	private String firstName;

	private String lastName;

	public NameId() {

	}

	public NameId(final String firstName, final String lastName) {

		this.firstName = firstName;
		this.lastName = lastName;
	}

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

	@Override
	public boolean equals(final Object o) {

		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		final NameId nameId = (NameId) o;

		if (!Objects.equals(firstName, nameId.firstName))
			return false;
		return Objects.equals(lastName, nameId.lastName);
	}

	@Override
	public int hashCode() {

		int result = firstName != null ? firstName.hashCode() : 0;
		result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {

		return "NameId{" +
				"firstName='" + firstName + '\'' +
				", lastName='" + lastName + '\'' +
				'}';
	}
}
