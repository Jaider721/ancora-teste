package br.com.ancoraweb.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import br.com.ancoraweb.model.Despesas;
import br.com.ancoraweb.model.Ncm;
import br.com.ancoraweb.repository.NcmRepository;


@Controller
public class NcmController {

	
	
	@Autowired
	private NcmRepository ncmRepository;
	
	
	
	@GetMapping
	@RequestMapping(method = RequestMethod.GET, value = "**/associarncmdespesas")
	public ModelAndView inicioNcm() {
		ModelAndView modelAndView = new ModelAndView("ncm/associarncmdespesas");
		Iterable<Ncm> despesaIt = ncmRepository.findAll();
		modelAndView.addObject("ncms", despesaIt);
		modelAndView.addObject("ncmobj", new Despesas());
		return modelAndView;
	}
	
}
