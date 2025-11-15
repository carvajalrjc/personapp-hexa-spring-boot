package co.edu.javeriana.as.personapp.terminal.mapper;

import org.springframework.beans.factory.annotation.Autowired;

import co.edu.javeriana.as.personapp.common.annotations.Mapper;
import co.edu.javeriana.as.personapp.domain.Person;
import co.edu.javeriana.as.personapp.domain.Profession;
import co.edu.javeriana.as.personapp.domain.Study;
import co.edu.javeriana.as.personapp.terminal.model.EstudioModelCli;

@Mapper
public class EstudioMapperCli {

	@Autowired
	private PersonaMapperCli personaMapperCli;

	@Autowired
	private ProfesionMapperCli profesionMapperCli;

	public EstudioModelCli fromDomainToCli(Study study) {
		if (study != null) {
			return EstudioModelCli.builder()
					.person(personaMapperCli.fromBasicModelCliToDomain(
							personaMapperCli.fromDomainToBasicModelCli(study.getPerson())))
					.profession(profesionMapperCli.fromBasicModelCliToDomain(
							profesionMapperCli.fromDomainToBasicModelCli(study.getProfession())))
					.graduationDate(study.getGraduationDate())
					.universityName(study.getUniversityName())
					.build();
		}
		return null;
	}

	public Study fromCliToDomain(EstudioModelCli estudioModelCli) {
		if (estudioModelCli != null) {
			Person person = estudioModelCli.getPerson() != null
					? personaMapperCli.fromBasicModelCliToDomain(
							personaMapperCli.fromDomainToBasicModelCli(estudioModelCli.getPerson()))
					: null;
			Profession profession = estudioModelCli.getProfession() != null
					? profesionMapperCli.fromBasicModelCliToDomain(
							profesionMapperCli.fromDomainToBasicModelCli(estudioModelCli.getProfession()))
					: null;
			return Study.builder()
					.person(person)
					.profession(profession)
					.graduationDate(estudioModelCli.getGraduationDate())
					.universityName(estudioModelCli.getUniversityName())
					.build();
		}
		return null;
	}
}

