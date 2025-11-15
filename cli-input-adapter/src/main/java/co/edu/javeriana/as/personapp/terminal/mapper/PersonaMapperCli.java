package co.edu.javeriana.as.personapp.terminal.mapper;

import co.edu.javeriana.as.personapp.common.annotations.Mapper;
import co.edu.javeriana.as.personapp.domain.Gender;
import co.edu.javeriana.as.personapp.domain.Person;
import co.edu.javeriana.as.personapp.terminal.model.PersonaModelCli;

@Mapper
public class PersonaMapperCli {

	public PersonaModelCli fromDomainToAdapterCli(Person person) {
		PersonaModelCli personaModelCli = new PersonaModelCli();
		personaModelCli.setCc(person.getIdentification());
		personaModelCli.setNombre(person.getFirstName());
		personaModelCli.setApellido(person.getLastName());
		personaModelCli.setGenero(person.getGender().toString());
		personaModelCli.setEdad(person.getAge());
		return personaModelCli;
	}

	public PersonaModelCli fromDomainToBasicModelCli(Person person) {
		if (person != null) {
			return PersonaModelCli.builder()
					.cc(person.getIdentification())
					.nombre(person.getFirstName())
					.apellido(person.getLastName())
					.build();
		}
		return null;
	}

	public Person fromBasicModelCliToDomain(PersonaModelCli personaModelCli) {
		if (personaModelCli != null) {
			return Person.builder()
					.identification(personaModelCli.getCc())
					.firstName(personaModelCli.getNombre())
					.lastName(personaModelCli.getApellido())
					.gender(personaModelCli.getGenero() != null ? parseGender(personaModelCli.getGenero()) : Gender.OTHER)
					.build();
		}
		return null;
	}

	public Person fromCliToDomain(int cc, String nombre, String apellido, String genero, int edad) {
		return Person.builder()
				.identification(cc)
				.firstName(nombre)
				.lastName(apellido)
				.gender(parseGender(genero))
				.age(edad)
				.build();
	}

	private Gender parseGender(String genero) {
		if (genero == null || genero.trim().isEmpty()) {
			return Gender.OTHER;
		}
		String generoUpper = genero.toUpperCase().trim();
		switch (generoUpper) {
		case "M":
		case "MALE":
		case "MASCULINO":
			return Gender.MALE;
		case "F":
		case "FEMALE":
		case "FEMENINO":
			return Gender.FEMALE;
		case "O":
		case "OTHER":
		case "OTRO":
		default:
			return Gender.OTHER;
		}
	}
}
