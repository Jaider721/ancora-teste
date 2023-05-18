package br.com.ancoraweb.controller;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;
import javax.xml.parsers.ParserConfigurationException;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ResourceUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.xml.sax.SAXException;

import br.com.ancoraweb.bean.Apropriacao;
import br.com.ancoraweb.model.Despesas;
import br.com.ancoraweb.model.Empresa;
import br.com.ancoraweb.model.NotaEntrada;
import br.com.ancoraweb.model.NotaNcm;
import br.com.ancoraweb.repository.DespesasRepository;
import br.com.ancoraweb.repository.EmpresaRepository;
import br.com.ancoraweb.repository.FornecedorRepository;
import br.com.ancoraweb.repository.NotaEntradaRepository;
import br.com.ancoraweb.service.NotaEntradaService;
import br.com.swconsultoria.impressao.model.Impressao;
import br.com.swconsultoria.impressao.service.ImpressaoService;
import br.com.swconsultoria.impressao.util.ImpressaoUtil;
import net.sf.jasperreports.engine.JRException;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@Controller
public class EmpresaController {
  @Autowired
  private EmpresaRepository empresaRepository;
  
  @Autowired
  private NotaEntradaRepository notaEntradaRepository;
  
  @Autowired
  private FornecedorRepository fornecedorRepository;
  
  @Autowired
  private DespesasRepository despesasRepository;
  
  @Autowired
  RestTemplate restTemplate;
  
  private final NotaEntradaService notaEntradaService;
  
  private String caminhoPDF = "/opt/notas/nfe.pdf";
  
  private String os = System.getProperty("os.name").toLowerCase();
  
  public EmpresaController(NotaEntradaService notaEntradaService) {
    if (this.os.contains("win")) {
      this.caminhoPDF = "E:\\projetos\\ANCORA\\notas\\nfe.pdf";
    } else {
      this.caminhoPDF = "/opt/notas/nfe.pdf";
    } 
    this.notaEntradaService = notaEntradaService;
  }
  
  
  @GetMapping({"**/editarcondominio/{idcondominio}"})
  public ModelAndView editarcondominio(@PathVariable("idcondominio") Long idcondominio) {
    Optional<Empresa> empresa = this.empresaRepository.findById(idcondominio);
    ModelAndView modelAndView = new ModelAndView("cadastro/editarempresa");
    modelAndView.addObject("empresaobj", empresa.get());
    return modelAndView;
  }
  
  @PostMapping({"**/editarempresa"})
  public ModelAndView editarempresa(@Valid Empresa empresa, MultipartFile file, BindingResult binding) throws IOException {
    if (file != null && !file.isEmpty()) {
      String teste = Base64.getEncoder().encodeToString(file.getBytes());
      System.out.println(teste);
      byte[] byteArrray = file.getBytes();
      empresa.setCertificado(byteArrray);
    } else {
      empresa.setCertificado(((Empresa)this.empresaRepository.findById(empresa.getId()).get()).getCertificado());
    } 
    if (binding.hasErrors()) {
      ModelAndView modelAndView1 = new ModelAndView("cadastro/editarempresa");
      Iterable<Empresa> empresaIt = this.empresaRepository.findAll();
      modelAndView1.addObject("empresa", empresaIt);
      modelAndView1.addObject("empresaobj", empresa);
      List<String> msg = new ArrayList<>();
      for (ObjectError objectError : binding.getAllErrors())
        msg.add(objectError.getDefaultMessage()); 
      modelAndView1.addObject("msg", msg);
      return modelAndView1;
    } 
    empresa.setPodeRodar("S");
    this.empresaRepository.save(empresa);
    ModelAndView modelAndView = new ModelAndView("cadastro/editarempresa");
    modelAndView.addObject("msg", "Condomalterado com sucesso");
    modelAndView.addObject("empresa", empresa);
    modelAndView.addObject("empresaobj", empresa);
    return modelAndView;
  }
  
  @GetMapping({"**/listarnotas"})
  public ModelAndView listarnotas() {
    ModelAndView modelAndView = new ModelAndView("notas/pesquisarnota");
    Iterable<NotaEntrada> notas = this.notaEntradaRepository.findAll();
    modelAndView.addObject("notas", notas);
    return modelAndView;
  }
  
  @GetMapping({"**/notasprocondominio"})
  public ModelAndView notasprocondominio() {
    ModelAndView modelAndView = new ModelAndView("notas/notasprocondominio");
    Iterable<NotaEntrada> notas = this.notaEntradaRepository.findAll();
    modelAndView.addObject("notas", notas);
    return modelAndView;
  }
  
  @GetMapping({"**/notasporcondominioenviadas"})
  public ModelAndView notasporcondominioenviadas() {
    ModelAndView modelAndView = new ModelAndView("notas/notasporcondominioenviadas");
    Iterable<NotaEntrada> notas = this.notaEntradaRepository.todasNotasEnviadas();
    modelAndView.addObject("notas", notas);
    return modelAndView;
  }
  
  @GetMapping({"**/notasporcondominionaoenviadas"})
  public ModelAndView notasporcondominionaoenviadas() {
    ModelAndView modelAndView = new ModelAndView("notas/notasporcondominionaoenviadas");
    Iterable<NotaEntrada> notas = this.notaEntradaRepository.todasNotasNaoEnviadas();
    modelAndView.addObject("notas", notas);
    return modelAndView;
  }
  
  @PostMapping({"**/pesquisarcondominiocnpj"})
  public ModelAndView pesquisarcondominiocnpj(@RequestParam("cnpj") String cnpj) {
    ModelAndView modelAndView = new ModelAndView("notas/notasprocondominio");
    List<NotaEntrada> notas = this.notaEntradaRepository.findNotaCondominioCNPJ(cnpj.replaceAll("[^0-9]+", ""));
    if (!notas.isEmpty()) {
      modelAndView.addObject("condominio", ((NotaEntrada)notas.get(0)).getEmpresa().getRazaoSocial());
      modelAndView.addObject("notas", notas);
    } else {
      modelAndView.addObject("msg", "CNPJ do Condomnencontarado");
    } 
    return modelAndView;
  }
  
  @PostMapping({"**/pesquisarcondominiocnpjenviadas"})
  public ModelAndView pesquisarcondominiocnpjenviadas(@RequestParam("cnpj") String cnpj) {
    ModelAndView modelAndView = new ModelAndView("notas/notasporcondominioenviadas");
    List<NotaEntrada> notas = this.notaEntradaRepository.findNotaCondominioCNPJenviadas(cnpj.replaceAll("[^0-9]+", ""));
    if (!notas.isEmpty()) {
      modelAndView.addObject("condominio", ((NotaEntrada)notas.get(0)).getEmpresa().getRazaoSocial());
      modelAndView.addObject("notas", notas);
    } else {
      modelAndView.addObject("msg", "Não existem notas enviadas para o SuperLógica para esse condomínio");
    } 
    return modelAndView;
  }
  
  @PostMapping({"**/pesquisarcondominiocnpjnaoenviadas"})
  public ModelAndView pesquisarcondominiocnpjnaoenviadas(@RequestParam("cnpj") String cnpj) {
    ModelAndView modelAndView = new ModelAndView("notas/notasporcondominionaoenviadas");
    List<NotaEntrada> notas = this.notaEntradaRepository.findNotaCondominioCNPJnaoenviadas(cnpj.replaceAll("[^0-9]+", ""));
    if (!notas.isEmpty()) {
      modelAndView.addObject("condominio", ((NotaEntrada)notas.get(0)).getEmpresa().getRazaoSocial());
      modelAndView.addObject("notas", notas);
    } else {
      modelAndView.addObject("msg", "Não existem notas enviadas para o SuperLógica para esse condomínio");
    } 
    return modelAndView;
  }
  
  @PostMapping({"**/pesquisarnota"})
  public ModelAndView pesquisarnota(@RequestParam("pesquisarnota") String pesquisarnota) {
    ModelAndView modelAndView = new ModelAndView("notas/pesquisarnota");
    Iterable<NotaEntrada> notas = this.notaEntradaRepository.findPesquisarNota(pesquisarnota);
    modelAndView.addObject("notas", notas);
    return modelAndView;
  }
  
  @PostMapping({"**/dataEmissao"})
  public ModelAndView pesquisarPeriodoNotaFiscal(@RequestParam("dataEmissao") String dataEmissao) {
    ModelAndView modelAndView = new ModelAndView("notas/pesquisarnota");
    SimpleDateFormat formatador = new SimpleDateFormat("yyyy-MM-dd");
    try {
      Date data = formatador.parse(dataEmissao);
      modelAndView.addObject("notas", this.notaEntradaRepository.findPeriodoNota(data));
    } catch (ParseException e) {
      e.printStackTrace();
      modelAndView.addObject("notas", new NotaEntrada());
    } 
    return modelAndView;
  }
  
  @GetMapping({"**/consutanota"})
  public ModelAndView consutanota() {
    HttpHeaders headers = new HttpHeaders();
    headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
    HttpEntity <String> entity = new HttpEntity<String>(headers);
    this.restTemplate.exchange("http://localhost:9090/ancora-nfe/api/v1/notaEntrada/consulta", HttpMethod.GET, entity, String.class, new Object[0]).getBody();
    ModelAndView modelAndView = new ModelAndView("notas/pesquisarnota");
    Iterable<NotaEntrada> notas = this.notaEntradaRepository.findAll();
    modelAndView.addObject("notas", notas);
    return modelAndView;
  }
  
  @GetMapping({"**/consultarEmpresa/{idcondominio}"})
  @ResponseBody
  public ModelAndView consultarEmpresa(@PathVariable("idcondominio") Long idcondominio) throws IOException, JRException, ParserConfigurationException, SAXException {
    HttpHeaders headers = new HttpHeaders();
    headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
    HttpEntity <String> entity = new HttpEntity<String>(headers);
    this.restTemplate.exchange("http://localhost:9090/ancora-nfe/api/v1/notaEntrada/consultarEmpresa/" + idcondominio.toString(), HttpMethod.GET, entity, String.class).getBody();
    ModelAndView modelAndView = new ModelAndView("notas/consultarEmpresa");
    Iterable<NotaEntrada> notas = this.notaEntradaRepository.findNotaCondominio(idcondominio);
    modelAndView.addObject("notas", notas);
    modelAndView.addObject("condominio", ((Empresa)this.empresaRepository.findById(idcondominio).get()).getRazaoSocial());
    return modelAndView;
  }
  
  @GetMapping(value = {"**/vernotacompleta/{idnota}"}, produces = {"application/pdf"})
  @ResponseBody
  public ResponseEntity<?> getNota(@PathVariable("idnota") Long idnota) throws IOException, JRException, ParserConfigurationException, SAXException {
    Impressao impressao = ImpressaoUtil.impressaoPadraoNFe(this.notaEntradaService.getxml(idnota));
    ImpressaoService.impressaoPdfArquivo(impressao, this.caminhoPDF);
    File file = ResourceUtils.getFile(this.caminhoPDF);
    Path path = Paths.get(file.getAbsolutePath());
    ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));
    return 
      ResponseEntity.ok()
      .contentType(MediaType.APPLICATION_PDF)
      .contentLength(file.length())
      .body(resource);
  }
  
  @PostMapping({"**/superlogica"})
  public ModelAndView superlogica(@RequestParam MultiValueMap<String, String> queryMap) throws IOException, JRException, ParserConfigurationException, SAXException, ParseException {
    List<String> itemIds = (List<String>)queryMap.get("idnota");
    NotaEntrada nota = this.notaEntradaService.listarPorId(Long.valueOf(Long.parseLong(itemIds.get(0))));
    Impressao impressao = ImpressaoUtil.impressaoPadraoNFe(this.notaEntradaService.getxml(nota.getId()));
    ImpressaoService.impressaoPdfArquivo(impressao, this.caminhoPDF);
    String teste = gravarDespesa(nota);
    System.out.println(teste);
    ModelAndView modelAndView = new ModelAndView("notas/pesquisarnota");
    if (teste.equals("200")) {
      nota.setEnviadoSuperLogica(true);
      notaEntradaService.save(nota);
      modelAndView.addObject("msg_azul", "Nota cadastrada no Superlcom sucesso!");
    } else if (teste.equals("206")) {
      modelAndView.addObject("msg", "Existe NCM da Nota sem associacom a despesa");
    } else if (teste.equals("500")) {
      modelAndView.addObject("msg", "plano de contas - categoria nencontrada - Favor verificar a categoria se estassociada ao NCM da Nota");
    } else {
      modelAndView.addObject("msg", "Serviço do SuperLógica Indisponível");
    } 
    modelAndView.addObject("condominio", nota.getEmpresa().getRazaoSocial());
    modelAndView.addObject("notas", nota);
    return modelAndView;
  }
  
  private String gravarDespesa(NotaEntrada nota) throws IOException, ParseException {
    String idArquivo = null;
    OkHttpClient client = (new OkHttpClient()).newBuilder()
      .build();
    okhttp3.MediaType mediaType = okhttp3.MediaType.parse("application/x-www-form-urlencoded");
    String idfornecedorSuperlogica = this.fornecedorRepository.findFornecedor(nota.getCnpjEmitente()).getIdSuperlogica();
    RequestBody body = RequestBody.create(mediaType, 
        "ID_CONDOMINIO_COND=" + nota.getEmpresa().getIdCondSuperLogica() + "&" + 
        "ST_NOME_CON=Favorecido&" + 
        "ID_CONTATO_CON=" + idfornecedorSuperlogica + "&" + 
        "DT_VENCIMENTOPRIMEIRAPARCELA=" + dataPadraoAmericao(nota.getData()) + "&" + 
        "ID_FORMA_PAG=0&FL_MARCAR_PARA_REMESSA=0&" + 
        "CHECK_LIQUIDAR_TODOS_CH=&" + 
        "MARCAR_PARA_IMPRESSAO=0&" + 
        apropriacao(nota) + 
        "DT_LIQUIDACAO_PDES=&" + 
        "VL_DESCONTO_PDES=0&" + 
        "VL_MULTA_PDES=0&" + 
        "VL_JUROS_PDES=0&" + 
        "VL_PAGO=0&" + 
        "FL_ACAO_IMPRESSAO=1&" + 
        "NM_NUMERO_CH=&RETENCOES[0][DT_VENCIMENTO_PDES]=&RETENCOES[0][ID_DESPESA_DES]=0&" + 
        "RETENCOES[0][FL_LIQUIDADO_PDES]=&RETENCOES[0][ID_PARCELA_PDES]=&RETENCOES[0][ID_RV2_IMPOSTO_DES]=&" + 
        "RETENCOES[0][VL_RV2_VALORRETIDO_DES]=&RETENCOES[0][ST_COMPLEMENTO_PDES]=&" + 
        "RETENCOES[0][VL_RV2_SUBEMPREITADA_DES]=&RETENCOES[0][VL_RV2_MATERIAL_DES]=&" + 
        "RETENCOES[0][VL_RV2_MAODEOBRA_DES]=&RETENCOES[0][BASE_CALCULO]=0&RETENCOES[0][ST_CODIGOBARRAS_PDES]=&" + 
        "ID_CONTABANCO_CB=55&" + 
        "DT_DESPESA_DES=&" + 
        "ST_DOCUMENTO_DES=" + nota.getNumeroNota() + "&" + 
        "ID_TIPO_DOC=1&" + 
        "ST_SERIENOTA_DES=&" + 
        "ARQUIVOS[0][ID_ARQUIVO_ARQ]=" + idarquivo());
    Request request = (new Request.Builder())
      .url("https://api.superlogica.net/v2/condor/despesas/")
      .method("POST", body)
      .addHeader("Content-Type", "application/x-www-form-urlencoded")
      .addHeader("app_token", "b9fbdf19-2b08-4295-ac73-3404eb7e2fe4")
      .addHeader("access_token", "7fadf2e3-4bde-4f1b-bd8b-8143e19f273f")
      .build();
    Response response = client.newCall(request).execute();
    System.out.println(response);
    String jsonData = response.body().string();
    System.out.println(jsonData);
    JSONArray jsonarray = new JSONArray(jsonData);
    for (int i = 0; i < jsonarray.length(); i++) {
      JSONObject jsonobject = jsonarray.getJSONObject(i);
      idArquivo = jsonobject.getString("status");
      System.out.println(idArquivo);
    } 
    return idArquivo;
  }
  
  private String apropriacao(NotaEntrada notaEntrada) {
    String apropriacao = "";
    List<Apropriacao> despesaNCM = new ArrayList<>();
    for (NotaNcm nota : notaEntrada.getNotaNcm()) {
      Apropriacao despesa = new Apropriacao();
      despesa.setIdDespesa(nota.getNcmNota().getDespesa().getId());
      despesa.setID_DESPESA_DES(nota.getNcmNota().getDespesa().getIdDespesa());
      despesa.setST_COMPLEMENTO_APRO(nota.getNcmNota().getDescricao());
      despesa.setVL_VALOR_PDES(nota.getVProd());
      despesaNCM.add(despesa);
    } 
    Map<Long, BigDecimal> mapa = new HashMap<>();
    for (Apropriacao despesa : despesaNCM) {
      System.err.println(despesa.getIdDespesa() + " - " + despesa.getVL_VALOR_PDES());
      if (mapa.containsKey(despesa.getIdDespesa())) {
        BigDecimal valor = mapa.get(despesa.getIdDespesa());
        valor = valor.add(despesa.getVL_VALOR_PDES());
        mapa.put(despesa.getIdDespesa(), valor);
        continue;
      } 
      mapa.put(despesa.getIdDespesa(), despesa.getVL_VALOR_PDES());
    } 
    despesaNCM = new ArrayList<>();
    for (Map.Entry<Long, BigDecimal> entry : mapa.entrySet()) {
      System.out.println((new StringBuilder()).append(entry.getKey()).append(": ").append(entry.getValue()).toString());
      Apropriacao despesa = new Apropriacao();
      Despesas despesaObj = this.despesasRepository.findById(entry.getKey()).get();
      despesa.setIdDespesa(entry.getKey());
      despesa.setID_DESPESA_DES(despesaObj.getIdDespesa());
      despesa.setST_COMPLEMENTO_APRO(despesaObj.getDescricao());
      System.out.println(entry.getValue());
      despesa.setVL_VALOR_PDES(entry.getValue());
      despesaNCM.add(despesa);
    } 
    for (int i = 0; i < despesaNCM.size(); i++)
      apropriacao = String.valueOf(apropriacao) + "APROPRIACAO[" + i + "][ST_CONTA_CONT]=" + ((Apropriacao)despesaNCM.get(i)).getID_DESPESA_DES() + "&" + 
        "APROPRIACAO[" + i + "][ST_DESCRICAO_CONT]=" + ((Apropriacao)despesaNCM.get(i)).getST_COMPLEMENTO_APRO() + "&" + 
        "APROPRIACAO[" + i + "][ID_DESPESA_DES]=&" + 
        "APROPRIACAO[" + i + "][ST_COMPLEMENTO_APRO]=" + ((Apropriacao)despesaNCM.get(i)).getST_COMPLEMENTO_APRO() + "&" + 
        "APROPRIACAO[" + i + "][VL_VALOR_PDES]=" + ((Apropriacao)despesaNCM.get(i)).getVL_VALOR_PDES().toString() + "&"; 
    return apropriacao;
  }
  
  private String dataPadraoAmericao(Date data) throws ParseException {
    SimpleDateFormat formatador = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
    String dataFormatada = formatador.format(data);
    System.out.println(dataFormatada);
    return dataFormatada;
  }
  
  private String idarquivo() throws IOException {
    OkHttpClient client = (new OkHttpClient()).newBuilder().build();
    File file = new File(this.caminhoPDF);
    MultipartBody multipartBody = (new MultipartBody.Builder()).setType(MultipartBody.FORM)
      .addFormDataPart("ID_RESPONSAVEL_ARQ", "2")
      .addFormDataPart("FL_TIPO_ARQ", "9")
      .addFormDataPart("ARQUIVO", file.getName(), RequestBody.create(okhttp3.MediaType.parse("application/octet-stream"), file)).build();
    Request request = (new Request.Builder())
      .url("https://api.superlogica.net/v2/condor/arquivos/")
      .method("POST", (RequestBody)multipartBody)
      .addHeader("Content-Type", "application/x-www-form-urlencoded")
      .addHeader("app_token", "b9fbdf19-2b08-4295-ac73-3404eb7e2fe4")
      .addHeader("access_token", "7fadf2e3-4bde-4f1b-bd8b-8143e19f273f")
      .build();
    Response response = client.newCall(request).execute();
    String jsonData = response.body().string();
    System.out.println(jsonData);
    JSONArray jsonarray = new JSONArray(jsonData);
    JSONObject id = null;
    for (int i = 0; i < jsonarray.length(); i++) {
      JSONObject jsonobject = jsonarray.getJSONObject(i);
      id = jsonobject.getJSONObject("data");
    } 
    String idArquivo = id.getString("id_arquivo_arq");
    return idArquivo;
  }
}