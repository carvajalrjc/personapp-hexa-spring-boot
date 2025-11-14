package co.edu.javeriana.as.personapp.application.usecase;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;

import co.edu.javeriana.as.personapp.application.port.in.PhoneInputPort;
import co.edu.javeriana.as.personapp.application.port.out.PersonOutputPort;
import co.edu.javeriana.as.personapp.application.port.out.PhoneOutputPort;
import co.edu.javeriana.as.personapp.common.annotations.UseCase;
import co.edu.javeriana.as.personapp.common.exceptions.NoExistException;
import co.edu.javeriana.as.personapp.domain.Person;
import co.edu.javeriana.as.personapp.domain.Phone;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@UseCase
public class PhoneUseCase implements PhoneInputPort {

	private PhoneOutputPort phonePersistence;
	private PersonOutputPort personPersistence;

	public PhoneUseCase(@Qualifier("phoneOutputAdapterMaria") PhoneOutputPort phonePersistence,
			@Qualifier("personOutputAdapterMaria") PersonOutputPort personPersistence) {
		this.phonePersistence = phonePersistence;
		this.personPersistence = personPersistence;
	}

	@Override
	public void setPersistence(PhoneOutputPort phonePersistence, PersonOutputPort personPersistence) {
		this.phonePersistence = phonePersistence;
		this.personPersistence = personPersistence;
	}

	@Override
	public Phone create(Phone phone, int ccPerson) throws NoExistException {
		log.debug("Into create on Application Domain");
		Person oldPerson = personPersistence.findById(ccPerson);
		if (oldPerson == null) {
			log.error("The person with id " + ccPerson + " does not exist into db, cannot be created");
			throw new NoExistException(
					"The person with id " + ccPerson + " does not exist into db, cannot be created");
		}
		phone.setOwner(oldPerson);
		return phonePersistence.save(phone);
	}

	@Override
	public Phone edit(String identification, Phone phone, int ccPerson) throws NoExistException {
		log.debug("Into edit on Application Domain");
		Phone oldPhone = phonePersistence.findById(identification);
		if (oldPhone == null) {
			throw new NoExistException(
					"The phone with id " + identification + " does not exist into db, cannot be edited");
		}
		Person oldPerson = personPersistence.findById(ccPerson);
		if (oldPerson == null) {
			log.error("The person with id " + ccPerson + " does not exist into db, cannot be edited");
			throw new NoExistException(
					"The person with id " + ccPerson + " does not exist into db, cannot be edited");
		}
		phone.setOwner(oldPerson);
		return phonePersistence.save(phone);
	}

	@Override
	public Boolean drop(String identification) throws NoExistException {
		Phone oldPhone = phonePersistence.findById(identification);
		if (oldPhone != null)
			return phonePersistence.delete(identification);
		throw new NoExistException(
				"The phone with id " + identification + " does not exist into db, cannot be dropped");
	}

	@Override
	public Phone findOne(String identification) throws NoExistException {
		Phone oldPhone = phonePersistence.findById(identification);
		if (oldPhone != null)
			return oldPhone;
		throw new NoExistException("The phone with id " + identification + " does not exist into db, cannot be found");
	}

	@Override
	public List<Phone> findAll() {
		log.info("Output: " + phonePersistence.getClass());
		return phonePersistence.find();
	}

	@Override
	public Integer count() {
		return phonePersistence.find().size();
	}

}

