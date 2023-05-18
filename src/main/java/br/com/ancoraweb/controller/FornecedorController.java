package br.com.ancoraweb.controller;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.com.ancoraweb.model.Empresa;
import br.com.ancoraweb.model.Fornecedor;
import br.com.ancoraweb.repository.FornecedorRepository;


@Controller
public class FornecedorController {

@Autowired
private FornecedorRepository fornecedorRepository;

@RequestMapping("**/listafornecedores")
public ModelAndView inicio() {
	ModelAndView modelAndView = new ModelAndView("fornecedor/listafornecedores");
	Iterable<Fornecedor> clientesIt = fornecedorRepository.findAll();
	modelAndView.addObject("fornecedores", clientesIt);
	modelAndView.addObject("fornecedorobj", new Fornecedor());
	return modelAndView;
}



@PostMapping("**/pesquisarFornecedorCNPJ")
public ModelAndView pesquisarFornecedorCNPJ(@RequestParam("cnpj") String cnpj) {
	ModelAndView modelAndView = new ModelAndView("fornecedor/listafornecedores");
	modelAndView.addObject("fornecedores", fornecedorRepository.findFornecedor(cnpj.replaceAll("[^0-9]+", "")));
	return modelAndView;
}	


}
