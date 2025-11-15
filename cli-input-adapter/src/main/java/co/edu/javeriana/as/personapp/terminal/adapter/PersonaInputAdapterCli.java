package co.edu.javeriana.as.personapp.terminal.adapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import co.edu.javeriana.as.personapp.application.port.in.PersonInputPort;
import co.edu.javeriana.as.personapp.application.port.out.PersonOutputPort;
import co.edu.javeriana.as.personapp.application.usecase.PersonUseCase;
import co.edu.javeriana.as.personapp.common.annotations.Adapter;
import co.edu.javeriana.as.personapp.common.exceptions.InvalidOptionException;
import co.edu.javeriana.as.personapp.common.setup.DatabaseOption;
import co.edu.javeriana.as.personapp.terminal.mapper.PersonaMapperCli;
import co.edu.javeriana.as.personapp.terminal.model.PersonaModelCli;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Adapter
public class PersonaInputAdapterCli {

	@Autowired
	@Qualifier("personOutputAdapterMaria")
	private PersonOutputPort personOutputPortMaria;

	@Autowired
	@Qualifier("personOutputAdapterMongo")
	private PersonOutputPort personOutputPortMongo;

	@Autowired
	private PersonaMapperCli personaMapperCli;

	PersonInputPort personInputPort;

	public void setPersonOutputPortInjection(String dbOption) throws InvalidOptionException {
		if (dbOption.equalsIgnoreCase(DatabaseOption.MARIA.toString())) {
			personInputPort = new PersonUseCase(personOutputPortMaria);
		} else if (dbOption.equalsIgnoreCase(DatabaseOption.MONGO.toString())) {
			personInputPort = new PersonUseCase(personOutputPortMongo);
		} else {
			throw new InvalidOptionException("Invalid database option: " + dbOption);
		}
	}

	public void historial() {
	    log.info("Into historial PersonaEntity in Input Adapter");
	    personInputPort.findAll().stream()
	        .map(personaMapperCli::fromDomainToAdapterCli)
	        .forEach(System.out::println);
	}

	public void create(int cc, String nombre, String apellido, String genero, int edad) {
		try {
			personInputPort.create(personaMapperCli.fromCliToDomain(cc, nombre, apellido, genero, edad));
			System.out.println("Persona creada exitosamente.");
		} catch (Exception e) {
			log.warn("Error al crear persona: " + e.getMessage());
		}
	}

	public void findOne(int cc) {
		try {
			PersonaModelCli persona = personaMapperCli.fromDomainToAdapterCli(personInputPort.findOne(cc));
			System.out.println(persona);
		} catch (Exception e) {
			log.warn("Error al buscar persona: " + e.getMessage());
		}
	}

	public void edit(int cc, String nombre, String apellido, String genero, int edad) {
		try {
			personInputPort.edit(cc, personaMapperCli.fromCliToDomain(cc, nombre, apellido, genero, edad));
			System.out.println("Persona actualizada exitosamente.");
		} catch (Exception e) {
			log.warn("Error al actualizar persona: " + e.getMessage());
		}
	}

	public void drop(int cc) {
		try {
			personInputPort.drop(cc);
			System.out.println("Persona eliminada exitosamente.");
		} catch (Exception e) {
			log.warn("Error al eliminar persona: " + e.getMessage());
		}
	}

	public void count() {
		try {
			int count = personInputPort.count();
			System.out.println("Total de personas: " + count);
		} catch (Exception e) {
			log.warn("Error al contar personas: " + e.getMessage());
		}
	}

}
