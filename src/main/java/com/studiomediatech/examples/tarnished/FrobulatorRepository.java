package com.studiomediatech.examples.tarnished;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FrobulatorRepository extends CrudRepository<Frobulator, Long> {

	List<Frobulator> findAll();

}
