package co.edu.javeriana.as.personapp.terminal.mapper;

import co.edu.javeriana.as.personapp.common.annotations.Mapper;
import co.edu.javeriana.as.personapp.domain.Profession;
import co.edu.javeriana.as.personapp.terminal.model.ProfesionModelCli;

@Mapper
public class ProfesionMapperCli {

	public ProfesionModelCli fromDomainToBasicModelCli(Profession profession) {
		if (profession != null) {
			return ProfesionModelCli.builder()
					.id(profession.getIdentification())
					.name(profession.getName())
					.description(profession.getDescription())
					.build();
		}
		return null;
	}

	public Profession fromBasicModelCliToDomain(ProfesionModelCli profesionModelCli) {
		if (profesionModelCli != null) {
			return Profession.builder()
					.identification(profesionModelCli.getId())
					.name(profesionModelCli.getName())
					.description(profesionModelCli.getDescription())
					.build();
		}
		return null;
	}
}

