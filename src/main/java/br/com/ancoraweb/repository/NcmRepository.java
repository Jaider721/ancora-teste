package br.com.ancoraweb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.ancoraweb.model.Ncm;
import br.com.ancoraweb.model.NotaEntrada;



@Repository
public interface NcmRepository extends JpaRepository<Ncm,Long> {

	
	@Query(value = "select n from Ncm n where n.ncm = ?1")
	Ncm buscaNCM(String ncm);
	
}
