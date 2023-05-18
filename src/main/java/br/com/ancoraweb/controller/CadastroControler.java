package br.com.ancoraweb.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import br.com.ancoraweb.model.Empresa;
import br.com.ancoraweb.repository.EmpresaRepository;

@Controller
public class CadastroControler {
	
	
	@Autowired
	private EmpresaRepository empresaRepository;
	
	@GetMapping("**/cadastroempresa")
	public ModelAndView inicio() {
		ModelAndView modelAndView = new ModelAndView("cadastro/cadastroempresa");
		Iterable<Empresa> empresaIt = empresaRepository.findAll();
		modelAndView.addObject("empresas", empresaIt);
		modelAndView.addObject("empresaobj", new Empresa());
		return modelAndView;
	}

	@PostMapping("**/salvaempresa")
	public ModelAndView salvar(@Valid Empresa empresa, @RequestParam("file") MultipartFile file, BindingResult binding) throws IOException {
		
		if (!file.isEmpty()) {
				String teste = Base64.getEncoder().encodeToString(file.getBytes());
				System.out.println(teste);
				byte[] byteArrray = file.getBytes();
				empresa.setCertificado(byteArrray);
		}
		
		
		
		if(binding.hasErrors()) {
			ModelAndView modelAndView = new ModelAndView("cadastro/cadastroempresa");
			Iterable<Empresa> empresaIt = empresaRepository.findAll();
			modelAndView.addObject("empresa", empresaIt);
			modelAndView.addObject("empresaobj", empresa);
			
			List<String> msg = new ArrayList<String>();
			for(ObjectError objectError : binding.getAllErrors()) {
				msg.add(objectError.getDefaultMessage());
			}
			modelAndView.addObject("msg", msg);
			return modelAndView;
		}
		
		empresa.setPodeRodar("S");
		empresaRepository.save(empresa);
		ModelAndView modelAndView = new ModelAndView("cadastro/cadastroempresa");
		modelAndView.addObject("msg", "Condominio cadastrado com sucesso");
		modelAndView.addObject("empresas", empresa);
		modelAndView.addObject("empresaobj", empresa);
		return modelAndView;
	}
	
	@GetMapping("**/listaempresa")
    public ModelAndView listaempresa() {
        final ModelAndView modelAndView = new ModelAndView("cadastro/listaempresa");
        final Iterable<Empresa> empresas = (Iterable<Empresa>)this.empresaRepository.findAll();
        modelAndView.addObject("empresas", empresas);
        modelAndView.addObject("empresaobj", new Empresa());
        return modelAndView;
    }

}
