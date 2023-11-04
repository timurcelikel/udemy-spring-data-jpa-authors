package guru.springframework.sdjpaintro.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NameId implements Serializable {

	private String firstName;

	private String lastName;
}
