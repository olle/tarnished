package com.studiomediatech.examples.tarnished.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.studiomediatech.examples.tarnished.app.Frobulator;
import com.studiomediatech.examples.tarnished.app.FrobulatorService;

@Controller
public class FrobulatorController {

	private static final Logger LOG = LoggerFactory.getLogger(FrobulatorController.class);

	private final FrobulatorService frobulatorService;

	private Map<String, Long> index = new HashMap<>();

	public FrobulatorController(FrobulatorService frobulatorService) {

		this.frobulatorService = frobulatorService;
	}

	@GetMapping("/")
	public String index(Model model) {

		model.addAttribute("name", "Eyoor");
		return "index";
	}

	@GetMapping("/frobulators")
	public String listFrobulators(Model model) {

		List<Frobulator> frobulators = frobulatorService.listFrobulators();
		frobulators.forEach(frobulator -> index.put(frobulator.getKey(), frobulator.getId()));
		model.addAttribute("frobulators", frobulators);

		return "frobulators/list";
	}

	@GetMapping("/frobulators/new")
	public String newFrobulator(Model model) {

		return "frobulators/new";
	}

	@PostMapping("/frobulators")
	public String createFrobulator(Model model, //
			@Valid Frobulator form, BindingResult errors, RedirectAttributes redirect) {

		if (errors.hasErrors()) {
			LOG.error("Invalid frobulator form {}", errors);

			return "frobulators/new";
		}

		frobulatorService.createNewFrobulator(form);

		return "redirect:/frobulators";
	}

	@DeleteMapping("/frobulators/{key}")
	public String deleteFrobulator(Model model, @PathVariable("key") String key) {

		Long id = index.getOrDefault(key, (long) -1);
		frobulatorService.deleteFrobulator(id);

		return "redirect:/frobulators";
	}

	@GetMapping("/frobulators/{key}")
	public String frobulatorDetails(Model model, @PathVariable("key") String key) {

		Long id = Optional.ofNullable(index.get(key)).orElseThrow(() -> new FrobulatorNotFoundException());

		Frobulator frobulator = frobulatorService.getFrobulator(id)
				.orElseThrow(() -> new FrobulatorNotFoundException());

		model.addAttribute("frobulator", frobulator);

		return "frobulators/view";
	}

	@ResponseStatus(HttpStatus.NOT_FOUND)
	static class FrobulatorNotFoundException extends RuntimeException {
		// OK

	}

	/*
	 * 
	 * @GetMapping("/frobulators/{key}/edit") public String editFrobulator(Model
	 * model, @PathVariable("key") String key) {
	 * 
	 * return adapter.editFrobulator(model, key); }
	 * 
	 * 
	 */

}
