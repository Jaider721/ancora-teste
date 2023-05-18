package br.com.ancoraweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.ancoraweb.model.Fornecedor;

public interface FornecedorRepository extends JpaRepository<Fornecedor,Long> {
	
	
	@Query(value = "SELECT * FROM FORNECEDOR WHERE  CNPJ = ?1",nativeQuery = true)
	Fornecedor findFornecedor(String CNPJ);
	
}
