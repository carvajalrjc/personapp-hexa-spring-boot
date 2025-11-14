package co.edu.javeriana.as.personapp.adapter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import co.edu.javeriana.as.personapp.application.port.in.ProfessionInputPort;
import co.edu.javeriana.as.personapp.application.port.out.ProfessionOutputPort;
import co.edu.javeriana.as.personapp.application.usecase.ProfessionUseCase;
import co.edu.javeriana.as.personapp.common.annotations.Adapter;
import co.edu.javeriana.as.personapp.common.exceptions.InvalidOptionException;
import co.edu.javeriana.as.personapp.common.exceptions.NoExistException;
import co.edu.javeriana.as.personapp.common.setup.DatabaseOption;
import co.edu.javeriana.as.personapp.domain.Profession;
import co.edu.javeriana.as.personapp.mapper.ProfesionMapperRest;
import co.edu.javeriana.as.personapp.model.request.ProfesionRequest;
import co.edu.javeriana.as.personapp.model.response.ProfesionResponse;
import co.edu.javeriana.as.personapp.model.response.Response;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Adapter
public class ProfesionInputAdapterRest {

	@Autowired
	@Qualifier("professionOutputAdapterMaria")
	private ProfessionOutputPort professionOutputPortMaria;

	@Autowired
	@Qualifier("professionOutputAdapterMongo")
	private ProfessionOutputPort professionOutputPortMongo;

	@Autowired
	private ProfesionMapperRest profesionMapperRest;

	ProfessionInputPort professionInputPort;

	private String setProfessionOutputPortInjection(String dboption) throws InvalidOptionException {
		if (dboption.equalsIgnoreCase(DatabaseOption.MARIA.toString())) {
			professionInputPort = new ProfessionUseCase(professionOutputPortMaria);
			return DatabaseOption.MARIA.toString();
		} else if (dboption.equalsIgnoreCase(DatabaseOption.MONGO.toString())) {
			professionInputPort = new ProfessionUseCase(professionOutputPortMongo);
			return DatabaseOption.MONGO.toString();
		} else {
			throw new InvalidOptionException("Invalid database option: " + dboption);
		}
	}

	public List<ProfesionResponse> historial(String database) {
		log.info("Into historial ProfessionEntity in Input Adapter");
		try {
			if (setProfessionOutputPortInjection(database).equalsIgnoreCase(DatabaseOption.MARIA.toString())) {
				return professionInputPort.findAll().stream().map(profesionMapperRest::fromDomainToAdapterRestMaria)
						.collect(Collectors.toList());
			} else {
				return professionInputPort.findAll().stream().map(profesionMapperRest::fromDomainToAdapterRestMongo)
						.collect(Collectors.toList());
			}
		} catch (InvalidOptionException e) {
			log.warn(e.getMessage());
			return new ArrayList<ProfesionResponse>();
		}
	}

	public ProfesionResponse crearProfesion(ProfesionRequest request, String database) {
		try {
			setProfessionOutputPortInjection(database);
			Profession profession = professionInputPort.create(profesionMapperRest.fromAdapterToDomain(request));
			return profesionMapperRest.fromDomainToAdapterRestMaria(profession);
		} catch (InvalidOptionException e) {
			log.warn(e.getMessage());
		}
		return null;
	}

	public ResponseEntity<?> obtenerProfesion(String database, int id) throws NoExistException {
		try {
			setProfessionOutputPortInjection(database);
			Profession profession = professionInputPort.findOne(id);
			return ResponseEntity.ok(profesionMapperRest.fromDomainToAdapterRestMaria(profession));
		} catch (InvalidOptionException e) {
			log.warn(e.getMessage());
		}
		return ResponseEntity.notFound().build();
	}

	public ResponseEntity<?> actualizarProfesion(String database, int id, ProfesionRequest request) throws NoExistException {
		try {
			setProfessionOutputPortInjection(database);
			Profession profession = professionInputPort.edit(id, profesionMapperRest.fromAdapterToDomain(request));
			return ResponseEntity.ok(profesionMapperRest.fromDomainToAdapterRestMaria(profession));
		} catch (InvalidOptionException e) {
			log.warn(e.getMessage());
		} catch (NoExistException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new Response(HttpStatus.NOT_FOUND.toString(), 
							"Profession with id " + id + " does not exist in the database", 
							LocalDateTime.now()));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new Response(HttpStatus.INTERNAL_SERVER_ERROR.toString(), 
							"Internal server error", 
							LocalDateTime.now()));
		}
		return ResponseEntity.notFound().build();
	}

	public ResponseEntity<?> eliminarProfesion(String database, int id) throws InvalidOptionException, NoExistException {
		setProfessionOutputPortInjection(database);
		Optional<Profession> profession = Optional.ofNullable(professionInputPort.findOne(id));
		if (profession.isEmpty()) {
			throw new NoExistException("The profession with id " + id + " does not exist in the database, cannot be deleted");
		}
		professionInputPort.drop(id);
		return ResponseEntity.ok(new Response(HttpStatus.OK.toString(), 
				"Profession with id " + id + " deleted successfully", 
				LocalDateTime.now()));
	}

	public ResponseEntity<?> contarProfesiones(String database) {
		try {
			setProfessionOutputPortInjection(database);
			return ResponseEntity.ok(professionInputPort.count());
		} catch (InvalidOptionException e) {
			log.warn(e.getMessage());
		}
		return ResponseEntity.notFound().build();
	}
}

