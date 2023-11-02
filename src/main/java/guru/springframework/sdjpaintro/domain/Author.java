package guru.springframework.sdjpaintro.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NamedQueries({
		@NamedQuery(name = "author_find_all", query = "FROM Author"),
		@NamedQuery(name = "find_by_name",
				query = "FROM Author a WHERE a.firstName = :first_name and a.lastName = :last_name")
})
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Author {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String firstName;

	private String lastName;

	// @Transient tells Hibernate ignore this field when doing a DDL validate
	@Transient
	private List<Book> books;
}
