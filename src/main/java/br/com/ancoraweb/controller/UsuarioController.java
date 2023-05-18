package br.com.ancoraweb.controller;

import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import br.com.ancoraweb.model.TbPerfil;
import br.com.ancoraweb.model.TbUsuario;
import br.com.ancoraweb.repository.PerfilRepository;
import br.com.ancoraweb.repository.UsuarioRepository;

@Controller
public class UsuarioController {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private PerfilRepository perfilRepository;
	
	@RequestMapping(method = RequestMethod.GET, value = "**/cadusuario")
	public ModelAndView inicio() {
		ModelAndView modelAndView = new ModelAndView("usuario/cadastrousuario");
		modelAndView.addObject("usuarioobj", new TbUsuario());
		Iterable<TbUsuario> usuariosIt = usuarioRepository.findAll();
		modelAndView.addObject("usuarios", usuariosIt);
		modelAndView.addObject("perfis", perfilRepository.findAll());
		return modelAndView;
	}
	
	
	@GetMapping("**/editarUsuario/{idusuario}")
	public ModelAndView editar(@PathVariable("idusuario") Long idusuario) {
		ModelAndView modelAndView = new ModelAndView("usuario/cadastrousuario");
		Optional<TbUsuario> usuario = usuarioRepository.findById(idusuario);
		modelAndView.addObject("perfis", perfilRepository.findAll());
		modelAndView.addObject("usuarioobj", usuario.get());
		return modelAndView;
		
	}
	
	
	@GetMapping("**/alterarSenha")
	public ModelAndView alterarSenha() {
		
		TbUsuario usuario = usuarioRepository.findUserByLogin(SecurityContextHolder.getContext().getAuthentication().getName());
		ModelAndView modelAndView = new ModelAndView("usuario/alterarsenha");
		modelAndView.addObject("usuarioobj", usuario);
		return modelAndView;
	}
	
	
	@RequestMapping(method = RequestMethod.POST, value="**/salvaUsuario")
	public ModelAndView salvaUsuario(@Validated TbUsuario usuario, BindingResult binding) {
		
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String result = encoder.encode(usuario.getSenha());
		usuario.setSenha(result);
    	TbPerfil perfil = perfilRepository.findById(usuario.getPerfis().get(0).getCodPerfil()).get();
	    usuario.getPerfis().set(0, perfil);
		usuarioRepository.save(usuario);
		ModelAndView modelAndView = new ModelAndView("usuario/cadastrousuario");
		modelAndView.addObject("usuarioobj", new TbUsuario());
		Iterable<TbUsuario> usuariosIt = usuarioRepository.findAll();
		modelAndView.addObject("usuarios", usuariosIt);
		modelAndView.addObject("perfis", perfilRepository.findAll());
		return modelAndView;
	}	
	
	
	@GetMapping("**/excluirUsuario/{idusuario}")
	public ModelAndView excluir(@PathVariable("idusuario") Long idusuario) {
		usuarioRepository.deleteById(idusuario);
		
		ModelAndView modelAndView = new ModelAndView("usuario/cadastrousuario");
		modelAndView.addObject("usuarioobj", new TbUsuario());
		Iterable<TbUsuario> usuariosIt = usuarioRepository.findAll();
		modelAndView.addObject("usuarios", usuariosIt);
		modelAndView.addObject("perfis", perfilRepository.findAll());
		return modelAndView;
	}
	
}
