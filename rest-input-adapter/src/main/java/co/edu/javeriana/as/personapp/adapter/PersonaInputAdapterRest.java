package co.edu.javeriana.as.personapp.adapter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import co.edu.javeriana.as.personapp.application.port.in.PersonInputPort;
import co.edu.javeriana.as.personapp.application.port.out.PersonOutputPort;
import co.edu.javeriana.as.personapp.application.usecase.PersonUseCase;
import co.edu.javeriana.as.personapp.common.annotations.Adapter;
import co.edu.javeriana.as.personapp.common.exceptions.InvalidOptionException;
import co.edu.javeriana.as.personapp.common.exceptions.NoExistException;
import co.edu.javeriana.as.personapp.common.setup.DatabaseOption;
import co.edu.javeriana.as.personapp.domain.Person;
import co.edu.javeriana.as.personapp.mapper.PersonaMapperRest;
import co.edu.javeriana.as.personapp.model.request.PersonaRequest;
import co.edu.javeriana.as.personapp.model.response.PersonaResponse;
import co.edu.javeriana.as.personapp.model.response.Response;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Adapter
public class PersonaInputAdapterRest {

	@Autowired
	@Qualifier("personOutputAdapterMaria")
	private PersonOutputPort personOutputPortMaria;

	@Autowired
	@Qualifier("personOutputAdapterMongo")
	private PersonOutputPort personOutputPortMongo;

	@Autowired
	private PersonaMapperRest personaMapperRest;

	PersonInputPort personInputPort;

	private String setPersonOutputPortInjection(String dbOption) throws InvalidOptionException {
		if (dbOption.equalsIgnoreCase(DatabaseOption.MARIA.toString())) {
			personInputPort = new PersonUseCase(personOutputPortMaria);
			return DatabaseOption.MARIA.toString();
		} else if (dbOption.equalsIgnoreCase(DatabaseOption.MONGO.toString())) {
			personInputPort = new PersonUseCase(personOutputPortMongo);
			return DatabaseOption.MONGO.toString();
		} else {
			throw new InvalidOptionException("Invalid database option: " + dbOption);
		}
	}

	public List<PersonaResponse> historial(String database) {
		log.info("Into historial PersonaEntity in Input Adapter");
		try {
			if(setPersonOutputPortInjection(database).equalsIgnoreCase(DatabaseOption.MARIA.toString())){
				return personInputPort.findAll().stream().map(personaMapperRest::fromDomainToAdapterRestMaria)
						.collect(Collectors.toList());
			}else {
				return personInputPort.findAll().stream().map(personaMapperRest::fromDomainToAdapterRestMongo)
						.collect(Collectors.toList());
			}
			
		} catch (InvalidOptionException e) {
			log.warn(e.getMessage());
			return new ArrayList<PersonaResponse>();
		}
	}

	public PersonaResponse crearPersona(PersonaRequest request) {
		try {
			setPersonOutputPortInjection(request.getDatabase());
			Person person = personInputPort.create(personaMapperRest.fromAdapterToDomain(request));
			return personaMapperRest.fromDomainToAdapterRestMaria(person);
		} catch (InvalidOptionException e) {
			log.warn(e.getMessage());
			//return new PersonaResponse("", "", "", "", "", "", "");
		}
		return null;
	}

	public ResponseEntity<?> obtenerPersona(String database, int cc) throws NoExistException {
		try {
			setPersonOutputPortInjection(database);
			Person person = personInputPort.findOne(cc);
			return ResponseEntity.ok(personaMapperRest.fromDomainToAdapterRestMaria(person));
		} catch (InvalidOptionException e) {
			log.warn(e.getMessage());
		}
		return ResponseEntity.notFound().build();
	}

	public ResponseEntity<?> actualizarPersona(String database, int cc, PersonaRequest request) throws NoExistException {
		try {
			setPersonOutputPortInjection(database);
			Person person = personInputPort.edit(cc, personaMapperRest.fromAdapterToDomain(request));
			return ResponseEntity.ok(personaMapperRest.fromDomainToAdapterRestMaria(person));
		} catch (InvalidOptionException e) {
			log.warn(e.getMessage());
		} catch (NoExistException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new Response(HttpStatus.NOT_FOUND.toString(), 
							"Persona con cc " + cc + " no existe en la base de datos", 
							LocalDateTime.now()));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new Response(HttpStatus.INTERNAL_SERVER_ERROR.toString(), 
							"Error interno en el servidor", 
							LocalDateTime.now()));
		}
		return ResponseEntity.notFound().build();
	}

	public ResponseEntity<?> eliminarPersona(String database, int cc) throws InvalidOptionException, NoExistException {
		setPersonOutputPortInjection(database);
		Person person = personInputPort.findOne(cc);
		if (person == null) {
			throw new NoExistException("The person with id " + cc + " does not exist into db, cannot be deleted");
		}
		personInputPort.drop(cc);
		return ResponseEntity.ok(new Response(HttpStatus.OK.toString(), 
				"Person with id " + cc + " deleted successfully", 
				LocalDateTime.now()));
	}

	public ResponseEntity<?> contarPersonas(String database) {
		try {
			setPersonOutputPortInjection(database);
			return ResponseEntity.ok(personInputPort.count());
		} catch (InvalidOptionException e) {
			log.warn(e.getMessage());
		}
		return ResponseEntity.notFound().build();
	}

}
