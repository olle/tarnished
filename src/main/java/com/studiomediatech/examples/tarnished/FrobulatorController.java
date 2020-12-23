package com.studiomediatech.examples.tarnished;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class FrobulatorController {

	private static final Logger LOG = LoggerFactory.getLogger(FrobulatorController.class);

	private final FrobulatorService frobulatorService;

	private final Map<String, Long> index = new ConcurrentHashMap<>();

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
		frobulatorService.deleteFrobulator(
				Optional.ofNullable(index.get(key)).orElseThrow(() -> new FrobulatorNotFoundException()));
		return "redirect:/frobulators";
	}

	@GetMapping("/frobulators/{key}")
	public String frobulatorDetails(Model model, @PathVariable("key") String key) {
		model.addAttribute("frobulator", getFrobulatorForKey(key));
		return "frobulators/view";
	}

	@GetMapping("/frobulators/{key}/edit")
	public String editFrobulator(Model model, @PathVariable("key") String key) {
		model.addAttribute("frobulator", getFrobulatorForKey(key));
		return "frobulators/edit";
	}

	private Frobulator getFrobulatorForKey(String key) {
		return Optional.ofNullable(index.get(key)).flatMap(frobulatorService::getFrobulator)
				.orElseThrow(() -> new FrobulatorNotFoundException());
	}

	@ResponseStatus(HttpStatus.NOT_FOUND)
	static class FrobulatorNotFoundException extends RuntimeException {
		// OK

	}

}
