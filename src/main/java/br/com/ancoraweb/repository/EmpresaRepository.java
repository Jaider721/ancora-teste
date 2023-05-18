package br.com.ancoraweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.ancoraweb.model.Empresa;



@Repository
public interface EmpresaRepository extends JpaRepository<Empresa,Long> {

	
    @Query(value = "select n from Empresa n where n.cnpj = ?1")
    Empresa CondominioPorCNPJ(String cnpj);

}
