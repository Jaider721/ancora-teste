package br.com.ancoraweb.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.ancoraweb.model.TbPerfil;
import br.com.ancoraweb.model.TbUsuario;

@Repository
@Transactional
public interface PerfilRepository extends JpaRepository<TbPerfil, Long>{
	
	@Query("select u from TbUsuario u where u.login =?1")
	TbUsuario findUserByLogin(String login);

}
