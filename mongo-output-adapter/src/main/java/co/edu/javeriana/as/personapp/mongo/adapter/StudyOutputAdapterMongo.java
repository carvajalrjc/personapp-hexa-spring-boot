package co.edu.javeriana.as.personapp.mongo.adapter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.mongodb.MongoWriteException;

import co.edu.javeriana.as.personapp.application.port.out.StudyOutputPort;
import co.edu.javeriana.as.personapp.common.annotations.Adapter;
import co.edu.javeriana.as.personapp.domain.Study;
import co.edu.javeriana.as.personapp.mongo.document.EstudiosDocument;
import co.edu.javeriana.as.personapp.mongo.mapper.EstudiosMapperMongo;
import co.edu.javeriana.as.personapp.mongo.repository.EstudioRepositoryMongo;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Adapter("studyOutputAdapterMongo")
public class StudyOutputAdapterMongo implements StudyOutputPort {

	@Autowired
	private EstudioRepositoryMongo estudioRepositoryMongo;

	@Autowired
	private EstudiosMapperMongo estudiosMapperMongo;

	@Override
	public Study save(Study study) {
		log.debug("Into save on Adapter MongoDB");
		try {
			EstudiosDocument persistedEstudio = estudioRepositoryMongo
					.save(estudiosMapperMongo.fromDomainToAdapter(study));
			return estudiosMapperMongo.fromAdapterToDomain(persistedEstudio);
		} catch (MongoWriteException e) {
			log.warn(e.getMessage());
			return study;
		}
	}

	@Override
	public Boolean delete(Integer identification, Integer idPerson) {
		log.debug("Into delete on Adapter MongoDB");
		String compositeId = idPerson + "-" + identification;
		Optional<EstudiosDocument> estudio = estudioRepositoryMongo.findById(compositeId);
		if (estudio.isPresent()) {
			estudioRepositoryMongo.delete(estudio.get());
			return true;
		}
		return false;
	}

	@Override
	public Study findById(Integer identification, Integer idPerson) {
		log.debug("Into findById on Adapter MongoDB");
		String compositeId = idPerson + "-" + identification;
		Optional<EstudiosDocument> estudio = estudioRepositoryMongo.findById(compositeId);
		if (estudio.isPresent()) {
			return estudiosMapperMongo.fromAdapterToDomain(estudio.get());
		}
		return null;
	}

	@Override
	public List<Study> find() {
		log.debug("Into find on Adapter MongoDB");
		return estudioRepositoryMongo.findAll().stream().map(estudiosMapperMongo::fromAdapterToDomainBasic)
				.collect(Collectors.toList());
	}

}

