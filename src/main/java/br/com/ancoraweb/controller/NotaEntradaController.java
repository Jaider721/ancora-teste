package br.com.ancoraweb.controller;

import br.com.ancoraweb.bean.Apropriacao;
import br.com.ancoraweb.model.Despesas;
import br.com.ancoraweb.model.Ncm;
import br.com.ancoraweb.model.NotaEntrada;
import br.com.ancoraweb.model.NotaNcm;
import br.com.ancoraweb.repository.DespesasRepository;
import br.com.ancoraweb.repository.NcmRepository;
import br.com.ancoraweb.repository.NotaEntradaRepository;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class NotaEntradaController {
  @Autowired
  private NotaEntradaRepository notaEntradaRepository;
  
  @Autowired
  private DespesasRepository despesasRepository;
  
  @Autowired
  private NcmRepository ncmRepository;
  
  @GetMapping({"**/notafiscaldetalhada/{idnota}"})
  public ModelAndView notafiscaldetalhada(@PathVariable("idnota") Long idnota) {
    ModelAndView modelAndView = new ModelAndView("notas/notafiscaldetalhada");
    Optional<NotaEntrada> notafiscal = notaEntradaRepository.findById(idnota);
    modelAndView.addObject("notafiscal", notafiscal.get());
    modelAndView.addObject("condominio", ((NotaEntrada)notafiscal.get()).getEmpresa().getRazaoSocial());
    modelAndView.addObject("tipoDespesas", pegarListaDespesa(idnota));
    modelAndView.addObject("soma", ((NotaEntrada)notafiscal.get()).getValor());
    return modelAndView;
  }
  
  @GetMapping({"**/associardespesancmpelanota/{idncm}/{iddespesa}/{idnota}"})
  public ModelAndView associardespesancmpelanota(@PathVariable("idncm") Long idncm, @PathVariable("iddespesa") Long iddespesa, @PathVariable("idnota") Long idnota) {
    ModelAndView modelAndView = new ModelAndView("notas/notafiscaldetalhada");
    Optional<Ncm> ncm = ncmRepository.findById(idncm);
    ncm.get().setDespesa(despesasRepository.findById(iddespesa).get());
    this.ncmRepository.save(ncm.get());
    Optional<NotaEntrada> notafiscal = notaEntradaRepository.findById(idnota);
    modelAndView.addObject("notafiscal", notafiscal.get());
    modelAndView.addObject("condominio", ((NotaEntrada)notafiscal.get()).getEmpresa().getRazaoSocial());
    modelAndView.addObject("tipoDespesas", pegarListaDespesa(idnota));
    modelAndView.addObject("soma", ((NotaEntrada)notafiscal.get()).getValor());
    return modelAndView;
  }
  
  private List<Apropriacao> pegarListaDespesa(Long idnota) {
    Optional<NotaEntrada> notafiscal = this.notaEntradaRepository.procurarDespesaNota(idnota);
    List<Apropriacao> apropriacao = new ArrayList<>();
    for (NotaNcm nota : notafiscal.get().getNotaNcm()) {
      Apropriacao despesa = new Apropriacao();
      despesa.setIdDespesa(nota.getNcmNota().getDespesa().getId());
      despesa.setID_DESPESA_DES(nota.getNcmNota().getDespesa().getIdDespesa());
      despesa.setST_COMPLEMENTO_APRO(String.valueOf(nota.getNcmNota().getDespesa().getIdDespesa()) + " - " + nota.getNcmNota().getDespesa().getDescricao());
      despesa.setVL_VALOR_PDES(nota.getVProd());
      apropriacao.add(despesa);
    } 
    Map<Long, BigDecimal> mapa = new HashMap<>();
    for (Apropriacao despesa : apropriacao) {
      System.err.println(despesa.getIdDespesa() + " - " + despesa.getVL_VALOR_PDES());
      if (mapa.containsKey(despesa.getIdDespesa())) {
        BigDecimal valor = mapa.get(despesa.getIdDespesa());
        valor = valor.add(despesa.getVL_VALOR_PDES());
        mapa.put(despesa.getIdDespesa(), valor);
        continue;
      } 
      mapa.put(despesa.getIdDespesa(), despesa.getVL_VALOR_PDES());
    } 
    System.out.println("Soma dos valores por ID:");
    apropriacao = new ArrayList<>();
    for (Map.Entry<Long, BigDecimal> entry : mapa.entrySet()) {
      System.out.println((new StringBuilder()).append(entry.getKey()).append(": ").append(entry.getValue()).toString());
      Apropriacao despesa = new Apropriacao();
      Despesas despesaObj = this.despesasRepository.findById(entry.getKey()).get();
      despesa.setIdDespesa(entry.getKey());
      despesa.setID_DESPESA_DES(despesaObj.getIdDespesa());
      despesa.setST_COMPLEMENTO_APRO(((Despesas)this.despesasRepository.findById(entry.getKey()).get()).getDescricao());
      System.out.println(entry.getValue());
      despesa.setVL_VALOR_PDES(entry.getValue());
      apropriacao.add(despesa);
    } 
    return apropriacao;
  }
}
