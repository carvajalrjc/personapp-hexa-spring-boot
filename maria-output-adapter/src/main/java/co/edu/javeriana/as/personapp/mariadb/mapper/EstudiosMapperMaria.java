package co.edu.javeriana.as.personapp.mariadb.mapper;

import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;

import org.springframework.beans.factory.annotation.Autowired;

import co.edu.javeriana.as.personapp.common.annotations.Mapper;
import co.edu.javeriana.as.personapp.domain.Study;
import co.edu.javeriana.as.personapp.mariadb.entity.EstudiosEntity;
import co.edu.javeriana.as.personapp.mariadb.entity.EstudiosEntityPK;

@Mapper
public class EstudiosMapperMaria {

	@Autowired
	private PersonaMapperMaria personaMapperMaria;

	@Autowired
	private ProfesionMapperMaria profesionMapperMaria;

	public EstudiosEntity fromDomainToAdapter(Study study) {
		if (study == null)
			return null;
		EstudiosEntityPK estudioPK = new EstudiosEntityPK();
		estudioPK.setCcPer(study.getPerson().getIdentification());
		estudioPK.setIdProf(study.getProfession().getIdentification());
		EstudiosEntity estudio = new EstudiosEntity();
		estudio.setEstudiosPK(estudioPK);
		estudio.setFecha(validateFecha(study.getGraduationDate()));
		estudio.setUniver(validateUniver(study.getUniversityName()));
		return estudio;
	}

	private java.util.Date validateFecha(LocalDate graduationDate) {
		return graduationDate != null
				? java.util.Date.from(graduationDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant())
				: null;
	}

	private String validateUniver(String universityName) {
		return universityName != null ? universityName : "";
	}

	public Study fromAdapterToDomain(EstudiosEntity estudiosEntity) {
		if (estudiosEntity == null || estudiosEntity.getPersona() == null || estudiosEntity.getProfesion() == null) {
			return null;
		}
		Study study = new Study();
		study.setPerson(personaMapperMaria.fromAdapterToDomainBasic(estudiosEntity.getPersona()));
		study.setProfession(profesionMapperMaria.fromAdapterToDomainBasic(estudiosEntity.getProfesion()));
		study.setGraduationDate(validateGraduationDate(estudiosEntity.getFecha()));
		study.setUniversityName(validateUniversityName(estudiosEntity.getUniver()));
		return study;
	}

	private LocalDate validateGraduationDate(java.util.Date fecha) {
		if (fecha == null) {
			return null;
		}
		if (fecha instanceof Date) {
			return ((Date) fecha).toLocalDate();
		}
		return fecha.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	}

	private String validateUniversityName(String univer) {
		return univer != null ? univer : "";
	}
}