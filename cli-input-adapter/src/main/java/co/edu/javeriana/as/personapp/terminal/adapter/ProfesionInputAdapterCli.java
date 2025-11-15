package co.edu.javeriana.as.personapp.terminal.adapter;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import co.edu.javeriana.as.personapp.application.port.in.ProfessionInputPort;
import co.edu.javeriana.as.personapp.application.port.out.ProfessionOutputPort;
import co.edu.javeriana.as.personapp.application.usecase.ProfessionUseCase;
import co.edu.javeriana.as.personapp.common.annotations.Adapter;
import co.edu.javeriana.as.personapp.common.exceptions.InvalidOptionException;
import co.edu.javeriana.as.personapp.common.exceptions.NoExistException;
import co.edu.javeriana.as.personapp.common.setup.DatabaseOption;
import co.edu.javeriana.as.personapp.terminal.mapper.ProfesionMapperCli;
import co.edu.javeriana.as.personapp.terminal.model.ProfesionModelCli;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Adapter
public class ProfesionInputAdapterCli {

	@Autowired
	@Qualifier("professionOutputAdapterMaria")
	private ProfessionOutputPort professionOutputPortMaria;

	@Autowired
	@Qualifier("professionOutputAdapterMongo")
	private ProfessionOutputPort professionOutputPortMongo;

	@Autowired
	private ProfesionMapperCli profesionMapperCli;

	ProfessionInputPort professionInputPort;

	public void setProfessionOutputPortInjection(String dboptions) throws InvalidOptionException {
		if (dboptions.equalsIgnoreCase(DatabaseOption.MARIA.toString())) {
			professionInputPort = new ProfessionUseCase(professionOutputPortMaria);
		} else if (dboptions.equalsIgnoreCase(DatabaseOption.MONGO.toString())) {
			professionInputPort = new ProfessionUseCase(professionOutputPortMongo);
		} else {
			throw new InvalidOptionException("Invalid database option: " + dboptions);
		}
	}

	public void historial() {
		log.info("Into historial ProfesionEntity in Input Adapter");
		professionInputPort.findAll().stream()
				.map(profesionMapperCli::fromDomainToBasicModelCli)
				.forEach(System.out::println);
	}

	public void create(int id, String name, String description) {
		log.info("Into create ProfesionEntity in Input Adapter");
		ProfesionModelCli profesionModelCli = new ProfesionModelCli();
		profesionModelCli.setId(id);
		profesionModelCli.setDescription(description);
		profesionModelCli.setName(name);
		professionInputPort.create(profesionMapperCli.fromBasicModelCliToDomain(profesionModelCli));
	}

	public void drop(int id) throws NoExistException {
		Optional.ofNullable(professionInputPort.findOne(id))
				.orElseThrow(() -> new NoExistException(
						"The profession with id " + id + " does not exist into db, cannot be deleted"));
		professionInputPort.drop(id);
		System.out.println("Profesión eliminada con éxito.");
	}

	public void edit(int id, String name, String description) throws NoExistException {
		log.info("Into edit ProfesionEntity in Input Adapter");
		ProfesionModelCli profesionModelCli = new ProfesionModelCli();
		profesionModelCli.setId(id);
		profesionModelCli.setDescription(description);
		profesionModelCli.setName(name);
		professionInputPort.edit(id, profesionMapperCli.fromBasicModelCliToDomain(profesionModelCli));
		System.out.println("Profesión editada con éxito.");
	}

	public void findOne(int id) throws NoExistException {
		log.info("Into findOne ProfesionEntity in Input Adapter");
		ProfesionModelCli profesionModelCli = profesionMapperCli
				.fromDomainToBasicModelCli(professionInputPort.findOne(id));
		if (profesionModelCli == null) {
			throw new NoExistException(
					"The profession with id " + id + " does not exist into db, cannot be found");
		}
		System.out.println(profesionModelCli.toString());
	}

	public void count() {
		System.out.println("Total de profesiones: " + professionInputPort.count());
	}
}

