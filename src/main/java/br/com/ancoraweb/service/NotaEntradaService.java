package br.com.ancoraweb.service;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;

import br.com.ancoraweb.excepetion.SistemaExcepetion;
import br.com.ancoraweb.model.NotaEntrada;
import br.com.ancoraweb.repository.NotaEntradaRepository;
import br.com.ancoraweb.util.ArquivoUtil;



@Service
public class NotaEntradaService {

    private final NotaEntradaRepository repository;

    public NotaEntradaService(NotaEntradaRepository repository){ 
    	this.repository = repository;
    }

    public void salvar(List<NotaEntrada> notasEntrada){
        repository.saveAll(notasEntrada);
    }

    public List<NotaEntrada> listarTudo(){
        return repository.findAll();
    }
    
    public void save(NotaEntrada notasEntrada) {
        repository.save(notasEntrada);
      }

    public NotaEntrada listarPorId(Long idNotaEntrada){
        return repository.findById(idNotaEntrada).orElseThrow(() -> new SistemaExcepetion("Nota não encontrada com id: "+ idNotaEntrada));
    }
    public String getxml(Long idNotaEntrada) throws IOException{
    	NotaEntrada nota =listarPorId(idNotaEntrada);
    	return ArquivoUtil.descompactaXml(nota.getXml());
    }

    

}
