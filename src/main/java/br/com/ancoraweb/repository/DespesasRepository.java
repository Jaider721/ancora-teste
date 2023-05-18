package br.com.ancoraweb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.ancoraweb.model.Despesas;

@Repository
public interface DespesasRepository extends JpaRepository<Despesas,Long> {

	@Query("select d from Despesas d where UPPER(d.descricao) LIKE CONCAT('%', UPPER(:searchTerm), '%')")
	List<Despesas> findDescricaoDespesa(@Param("searchTerm") String searchTerm);
	
	

}	
