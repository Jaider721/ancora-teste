package br.com.ancoraweb.repository;

import br.com.ancoraweb.model.NotaEntrada;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface NotaEntradaRepository extends JpaRepository<NotaEntrada, Long> {
  @Query(value = "select * from nota_entrada order by id desc limit 100", nativeQuery = true)
  List<NotaEntrada> findLast100();
  
  @Query("select n from NotaEntrada n where n.empresa.id = ?1")
  List<NotaEntrada> findNotaCondominio(Long paramLong);
  
  @Query("select n from NotaEntrada n where n.numeroNota = ?1")
  List<NotaEntrada> findPesquisarNota(String paramString);
  
  @Query(value = "select  * from nota_entrada n where DATE(n.data) = ?1", nativeQuery = true)
  List<NotaEntrada> findPeriodoNota(Date paramDate);
  
  @Query("select n from NotaEntrada n where n.empresa.cnpj = ?1")
  List<NotaEntrada> findNotaCondominioCNPJ(String paramString);
  
  @Query("select n from NotaEntrada n where n.enviadoSuperLogica = false and n.empresa.cnpj = ?1")
  List<NotaEntrada> findNotaCondominioCNPJnaoenviadas(String paramString);
  
  @Query("select n from NotaEntrada n where n.enviadoSuperLogica = true and n.empresa.cnpj = ?1")
  List<NotaEntrada> findNotaCondominioCNPJenviadas(String paramString);
  
  @Query("select n from NotaEntrada n where n.enviadoSuperLogica = true")
  Iterable<NotaEntrada> todasNotasEnviadas();
  
  @Query("select n from NotaEntrada n where n.enviadoSuperLogica = false")
  Iterable<NotaEntrada> todasNotasNaoEnviadas();
  
  @Query(value = "SELECT n.id, n.chave, n.cnpj_emitente, n.\"data\", n.id_contato_con, n.natureza_operacao, n.nome_emitente, n.numero_nota,n.\"schema\", n.valor, n.\"xml\", n.empresa_id, n.v_desc, n.v_frete, n.enviado_super_logica from nota_entrada n, nota_ncm nn, ncm n2 where n.id = ?1 and n.id = nn.nota_entada_id and nn.ncm_nota = n2.id order by n2.id ;", nativeQuery = true)
  Optional<NotaEntrada> procurarDespesaNota(Long paramLong);
}