package br.com.ancoraweb.controller;

import br.com.ancoraweb.model.Despesas;
import br.com.ancoraweb.model.Ncm;
import br.com.ancoraweb.repository.DespesasRepository;
import br.com.ancoraweb.repository.NcmRepository;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class DespesasController {
  @Autowired
  private DespesasRepository despesasRepository;
  
  @Autowired
  private NcmRepository ncmRepository;
  
  @GetMapping({"**/cadastrodespesas"})
  public ModelAndView inicio() {
    ModelAndView modelAndView = new ModelAndView("despesas/cadastrodespesas");
    Iterable<Despesas> despesaIt = despesasRepository.findAll();
    modelAndView.addObject("despesas", despesaIt);
    modelAndView.addObject("despesasobj", new Despesas());
    return modelAndView;
  }
  
  @GetMapping({"**/editardespesa/{iddespesa}"})
  public ModelAndView editardespesa(@PathVariable("iddespesa") Long iddespesa) {
    ModelAndView modelAndView = new ModelAndView("despesas/cadastrodespesas");
    Optional<Despesas> despesaIt = despesasRepository.findById(iddespesa);
    modelAndView.addObject("despesasobj", despesaIt);
    return modelAndView;
  }
  
  @PostMapping({"**/salvadespesa"})
  public ModelAndView salvar(@Valid Despesas despesa, BindingResult binding) throws IOException {
    if (binding.hasErrors()) {
      ModelAndView modelAndView = new ModelAndView("despesas/cadastrodespesas");
      Iterable<Despesas> despesaIt = despesasRepository.findAll();
      modelAndView.addObject("despesas", despesaIt);
      modelAndView.addObject("despesasobj", despesa);
      List<String> msg = new ArrayList<>();
      for (ObjectError objectError : binding.getAllErrors())
        msg.add(objectError.getDefaultMessage()); 
      modelAndView.addObject("msg", msg);
      return modelAndView;
    } 
    despesasRepository.save(despesa);
    ModelAndView modelAndView = new ModelAndView("despesas/cadastrodespesas");
    modelAndView.addObject("msg", "Despesas Cadastrada com sucesso");
    modelAndView.addObject("despesasobj", despesa);
    return modelAndView;
  }
  
  @GetMapping({"**/associardespesa/{idncm}"})
  public ModelAndView editarcondominio(@PathVariable("idncm") Long idncm) {
    ModelAndView modelAndView = new ModelAndView("despesas/associardespesancm");
    modelAndView.addObject("ncms", ncmRepository.findById(idncm).get());
    modelAndView.addObject("despesasobj", new Despesas());
    return modelAndView;
  }
  
  @GetMapping({"**/associardespesapelanota/{idncm}/{idnota}"})
  public ModelAndView associardespesapelanota(@PathVariable("idncm") Long idncm, @PathVariable("idnota") Long idnota) {
    ModelAndView modelAndView = new ModelAndView("despesas/associardespesancmpelanota");
    modelAndView.addObject("ncms", ncmRepository.findById(idncm).get());
    modelAndView.addObject("despesasobj", new Despesas());
    modelAndView.addObject("idnota", idnota);
    return modelAndView;
  }
  
  @PostMapping({"**/pesquisardespesa"})
  public ModelAndView pesquisardespesa(@RequestParam("idncm") Long idncm, @RequestParam("descricao") String descricao) {
    List<Despesas> despesa;
    ModelAndView modelAndView = new ModelAndView("despesas/associardespesancm");
    if (descricao == null) {
      modelAndView.addObject("msg", "Favor inserir valores em um dos campos da pesquisa");
      despesa = despesasRepository.findAll();
    } else {
      despesa = despesasRepository.findDescricaoDespesa(descricao);
    } 
    modelAndView.addObject("ncms", ncmRepository.findById(idncm).get());
    modelAndView.addObject("despesasobj", despesa);
    return modelAndView;
  }
  
  @PostMapping({"**/pesquisardespesapelanota"})
  public ModelAndView pesquisardespesapelanota(@RequestParam("idncm") Long idncm, @RequestParam("descricao") String descricao, @RequestParam("idnota") Long idnota) {
    List<Despesas> despesa;
    ModelAndView modelAndView = new ModelAndView("despesas/associardespesancmpelanota");
    if (descricao == null) {
      modelAndView.addObject("msg", "Favor inserir valores em um dos campos da pesquisa");
      despesa = despesasRepository.findAll();
    } else {
      despesa = despesasRepository.findDescricaoDespesa(descricao);
    } 
    modelAndView.addObject("ncms", ncmRepository.findById(idncm).get());
    modelAndView.addObject("despesasobj", despesa);
    modelAndView.addObject("idnota", idnota);
    return modelAndView;
  }
  
  @GetMapping({"**/associardespesancm/{idncm}/{iddespesa}"})
  public ModelAndView associardespesancm(@PathVariable("idncm") Long idncm, @PathVariable("iddespesa") Long iddespesa) {
    ModelAndView modelAndView = new ModelAndView("despesas/associardespesancm");
    Optional<Ncm> ncm = ncmRepository.findById(idncm);
    ncm.get().setDespesa(despesasRepository.findById(iddespesa).get());
    ncmRepository.save(ncm.get());
    modelAndView.addObject("msg", "Despesa associada ao NCM com sucesso!");
    modelAndView.addObject("ncms", ncm.get());
    modelAndView.addObject("despesasobj", despesasRepository.findById(iddespesa).get());
    return modelAndView;
  }
}