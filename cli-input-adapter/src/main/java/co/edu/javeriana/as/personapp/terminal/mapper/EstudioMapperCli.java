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
					.person(study.getPerson())
					.profession(study.getProfession())
					.graduationDate(study.getGraduationDate())
					.universityName(study.getUniversityName())
					.build();
		}
		return null;
	}

	public Study fromCliToDomain(EstudioModelCli estudioModelCli) {
		if (estudioModelCli != null) {
			return Study.builder()
					.person(estudioModelCli.getPerson())
					.profession(estudioModelCli.getProfession())
					.graduationDate(estudioModelCli.getGraduationDate())
					.universityName(estudioModelCli.getUniversityName())
					.build();
		}
		return null;
	}
}

