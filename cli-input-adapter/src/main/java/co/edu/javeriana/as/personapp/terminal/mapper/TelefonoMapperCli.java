package co.edu.javeriana.as.personapp.terminal.mapper;

import org.springframework.beans.factory.annotation.Autowired;

import co.edu.javeriana.as.personapp.common.annotations.Mapper;
import co.edu.javeriana.as.personapp.domain.Person;
import co.edu.javeriana.as.personapp.domain.Phone;
import co.edu.javeriana.as.personapp.terminal.model.TelefonoModelCli;

@Mapper
public class TelefonoMapperCli {

	@Autowired
	private PersonaMapperCli personaMapperCli;

	public TelefonoModelCli fromDomainToCli(Phone phone) {
		return TelefonoModelCli.builder()
				.num(phone.getNumber())
				.oper(phone.getCompany())
				.duenio(personaMapperCli.fromDomainToBasicModelCli(phone.getOwner())) // Mapeo básico
				.build();
	}

	public Phone fromCliToDomain(TelefonoModelCli telefonoModelCli) {
		Person owner = telefonoModelCli.getDuenio() != null
				? personaMapperCli.fromBasicModelCliToDomain(telefonoModelCli.getDuenio())
				: new Person();
		return Phone.builder()
				.number(telefonoModelCli.getNum())
				.company(telefonoModelCli.getOper())
				.owner(owner)
				.build();
	}
}

