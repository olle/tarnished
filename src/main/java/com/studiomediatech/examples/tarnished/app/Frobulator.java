package com.studiomediatech.examples.tarnished.app;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@Table(name = "frobulators")
public class Frobulator extends AbstractPersistable<Long> {

	@NotNull
	@Size(min = 2, max = 64)
	@Column(length = 64, nullable = false, name = "name")
	private String name;

	public String getName() {

		return name;
	}

	public void setName(String name) {

		this.name = name;
	}

	@Override
	public String toString() {

		return "FrobulatorEntity [name=" + name + "]";
	}

	public String getKey() {

		return UUID.nameUUIDFromBytes(("secret-" + name).getBytes(StandardCharsets.UTF_8)).toString();
	}

}
