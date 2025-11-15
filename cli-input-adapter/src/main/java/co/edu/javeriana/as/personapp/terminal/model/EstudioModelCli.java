package co.edu.javeriana.as.personapp.terminal.model;

import java.time.LocalDate;

import co.edu.javeriana.as.personapp.domain.Person;
import co.edu.javeriana.as.personapp.domain.Profession;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EstudioModelCli {
	private Person person;
	private Profession profession;
	private LocalDate graduationDate;
	private String universityName;

	@Override
	public String toString() {
		String personInfo = person != null ? person.getFirstName() + " " + person.getLastName() : "null";
		String professionInfo = profession != null ? profession.getName() : "null";
		return "EstudioModelCli [person=" + personInfo + ", profession=" + professionInfo + ", graduationDate="
				+ graduationDate + ", universityName=" + universityName + "]";
	}
}

