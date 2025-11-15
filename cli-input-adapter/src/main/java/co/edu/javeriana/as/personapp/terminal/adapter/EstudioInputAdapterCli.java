package co.edu.javeriana.as.personapp.terminal.adapter;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import co.edu.javeriana.as.personapp.application.port.in.PersonInputPort;
import co.edu.javeriana.as.personapp.application.port.in.ProfessionInputPort;
import co.edu.javeriana.as.personapp.application.port.in.StudyInputPort;
import co.edu.javeriana.as.personapp.application.port.out.PersonOutputPort;
import co.edu.javeriana.as.personapp.application.port.out.ProfessionOutputPort;
import co.edu.javeriana.as.personapp.application.port.out.StudyOutputPort;
import co.edu.javeriana.as.personapp.application.usecase.PersonUseCase;
import co.edu.javeriana.as.personapp.application.usecase.ProfessionUseCase;
import co.edu.javeriana.as.personapp.application.usecase.StudyUseCase;
import co.edu.javeriana.as.personapp.common.annotations.Adapter;
import co.edu.javeriana.as.personapp.common.exceptions.NoExistException;
import co.edu.javeriana.as.personapp.terminal.mapper.EstudioMapperCli;
import co.edu.javeriana.as.personapp.terminal.mapper.PersonaMapperCli;
import co.edu.javeriana.as.personapp.terminal.mapper.ProfesionMapperCli;
import co.edu.javeriana.as.personapp.terminal.model.EstudioModelCli;
import co.edu.javeriana.as.personapp.terminal.model.PersonaModelCli;
import co.edu.javeriana.as.personapp.terminal.model.ProfesionModelCli;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Adapter
public class EstudioInputAdapterCli {

	@Autowired
	@Qualifier("studyOutputAdapterMaria")
	private StudyOutputPort studyOutputPortMaria;

	@Autowired
	@Qualifier("studyOutputAdapterMongo")
	private StudyOutputPort studyOutputPortMongo;

	@Autowired
	@Qualifier("personOutputAdapterMaria")
	private PersonOutputPort personOutputPortMaria;

	@Autowired
	@Qualifier("personOutputAdapterMongo")
	private PersonOutputPort personOutputPortMongo;

	@Autowired
	@Qualifier("professionOutputAdapterMaria")
	private ProfessionOutputPort professionOutputPortMaria;

	@Autowired
	@Qualifier("professionOutputAdapterMongo")
	private ProfessionOutputPort professionOutputPortMongo;

	@Autowired
	private PersonaMapperCli personaMapperCli;

	private PersonInputPort personInputPort;

	@Autowired
	private ProfesionMapperCli profesionMapperCli;

	private ProfessionInputPort professionInputPort;

	@Autowired
	private EstudioMapperCli estudioMapperCli;

	private StudyInputPort studyInputPort;

	public void setStudyOutputPortInjection(String dboption) {
		if (dboption.equalsIgnoreCase("MARIA")) {
			this.studyInputPort = new StudyUseCase(studyOutputPortMaria, personOutputPortMaria,
					professionOutputPortMaria);
			this.personInputPort = new PersonUseCase(personOutputPortMaria);
			this.professionInputPort = new ProfessionUseCase(professionOutputPortMaria);
		} else if (dboption.equalsIgnoreCase("MONGO")) {
			this.studyInputPort = new StudyUseCase(studyOutputPortMongo, personOutputPortMongo,
					professionOutputPortMongo);
			this.personInputPort = new PersonUseCase(personOutputPortMongo);
			this.professionInputPort = new ProfessionUseCase(professionOutputPortMongo);
		} else {
			throw new IllegalArgumentException("Invalid database option: " + dboption);
		}
	}

	public void historial() {
		log.info("Into historial EstudioEntity in Input Adapter");
		studyInputPort.findAll().stream()
				.map(estudioMapperCli::fromDomainToCli)
				.forEach(System.out::println);
	}

	public void create(int ccPerson, int idProf, String college, LocalDate date) throws NoExistException {
		PersonaModelCli personModel = personaMapperCli
				.fromDomainToBasicModelCli(personInputPort.findOne(ccPerson));
		if (personModel == null) {
			throw new NoExistException(
					"The person with id " + ccPerson + " does not exist in the database, cannot create study.");
		}
		ProfesionModelCli professionModel = profesionMapperCli
				.fromDomainToBasicModelCli(professionInputPort.findOne(idProf));
		if (professionModel == null) {
			throw new NoExistException(
					"The profession with id " + idProf + " does not exist in the database, cannot create study.");
		}
		EstudioModelCli estudioModelCli = EstudioModelCli.builder()
				.person(personaMapperCli.fromBasicModelCliToDomain(personModel))
				.profession(profesionMapperCli.fromBasicModelCliToDomain(professionModel))
				.graduationDate(date)
				.universityName(college)
				.build();
		studyInputPort.create(estudioMapperCli.fromCliToDomain(estudioModelCli), ccPerson, idProf);
	}

	public void edit(int ccPerson, int idProf, String college, LocalDate date) throws NoExistException {
		PersonaModelCli personModel = personaMapperCli
				.fromDomainToBasicModelCli(personInputPort.findOne(ccPerson));
		if (personModel == null) {
			throw new NoExistException(
					"The person with id " + ccPerson + " does not exist in the database, cannot edit study.");
		}
		ProfesionModelCli professionModel = profesionMapperCli
				.fromDomainToBasicModelCli(professionInputPort.findOne(idProf));
		if (professionModel == null) {
			throw new NoExistException(
					"The profession with id " + idProf + " does not exist in the database, cannot edit study.");
		}
		EstudioModelCli estudioModelCli = EstudioModelCli.builder()
				.person(personaMapperCli.fromBasicModelCliToDomain(personModel))
				.profession(profesionMapperCli.fromBasicModelCliToDomain(professionModel))
				.graduationDate(date)
				.universityName(college)
				.build();
		co.edu.javeriana.as.personapp.domain.Study updatedStudy = studyInputPort.edit(idProf, ccPerson,
				estudioMapperCli.fromCliToDomain(estudioModelCli));
		if (updatedStudy == null) {
			throw new NoExistException("Failed to update the study. No existing study found with person ID "
					+ ccPerson + " and profession ID " + idProf);
		}
		System.out.println("Estudio actualizado con éxito.");
	}

	public void drop(int id, int ccPerson) throws NoExistException {
		Optional.ofNullable(studyInputPort.findOne(id, ccPerson))
				.orElseThrow(() -> new NoExistException(
						"The study with id " + id + " does not exist in the database, cannot drop study."));
		studyInputPort.drop(id, ccPerson);
		System.out.println("Estudio eliminado con éxito.");
	}

	public void findOne(int id, int ccPerson) throws NoExistException {
		Optional.ofNullable(studyInputPort.findOne(id, ccPerson))
				.orElseThrow(() -> new NoExistException(
						"The study with id " + id + " does not exist in the database, cannot find study."));
		System.out.println(studyInputPort.findOne(id, ccPerson));
	}

	public void count() {
		System.out.println("Total de estudios: " + studyInputPort.count());
	}
}

