package br.com.ancoraweb;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.ancoraweb.model.Empresa;
import br.com.ancoraweb.model.NotaEntrada;
import br.com.ancoraweb.repository.NotaEntradaRepository;
import br.com.ancoraweb.util.ArquivoUtil;
import br.com.swconsultoria.impressao.model.Impressao;
import br.com.swconsultoria.impressao.service.ImpressaoService;
import br.com.swconsultoria.impressao.util.ImpressaoUtil;

public class TesteImpressaoHtml {
	
	@Autowired
	private static NotaEntradaRepository notaEntradaRepository;

    public static void main(String[] args) {
        try {
            //Faça a leitura do Arquivo
        	
        	Long idnota = 125L;
        	Iterable<NotaEntrada> notas = notaEntradaRepository.findAll();
			Optional<NotaEntrada> nota = notaEntradaRepository.findById(idnota);
        	
            String xml = ImpressaoUtil.leArquivo(ArquivoUtil.descompactaXml(nota.get().getXml()));

            //Aqui está pegando o Layout Padrão
            Impressao impressao = ImpressaoUtil.impressaoPadraoNFe(xml);

            //Faz a impressão em pdf File passando o caminho do Arquivo
            ImpressaoService.impressaoHtml(impressao, "E:\\projetos\\ANCORA\\notas\\nfe.html");

        } catch (Exception e) {
            //Trate seus erros aqui
            e.printStackTrace();
        }
    }
}