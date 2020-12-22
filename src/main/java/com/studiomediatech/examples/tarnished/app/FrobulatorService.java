package com.studiomediatech.examples.tarnished.app;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FrobulatorService {

	private final FrobulatorRepository frobulatorRepository;

	public FrobulatorService(FrobulatorRepository frobulatorRepository) {

		this.frobulatorRepository = frobulatorRepository;
	}

	@Transactional(readOnly = true)
	public List<Frobulator> listFrobulators() {

		return frobulatorRepository.findAll();
	}

	@Transactional
	public void createNewFrobulator(@Valid Frobulator form) {

		frobulatorRepository.save(form);
	}

	@Transactional
	public void deleteFrobulator(Long id) {

		frobulatorRepository.deleteById(id);
	}

	@Transactional(readOnly = true)
	public Optional<Frobulator> getFrobulator(Long id) {

		return frobulatorRepository.findById(id);
	}

}
