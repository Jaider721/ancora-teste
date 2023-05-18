package br.com.ancoraweb.model;

import java.util.Collection;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


/**
 *
 * @author jdelfes
 */
@Entity
public class TbUsuario implements UserDetails {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codUsuario;

	private String login;
	private String senha;
	
	@OneToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "TbUsuarioPerfil", 
    joinColumns = @JoinColumn(name = "codUsuario", referencedColumnName = "codUsuario",table = "TbUsuario"),  // cria tabela de acesso do usuário
	inverseJoinColumns = @JoinColumn(name="codPerfil",referencedColumnName = "codPerfil",table = "TbPerfil"))
	private List<TbPerfil>  perfis;

	public Long getCodUsuario() {
		return codUsuario;
	}

	public void setCodUsuario(Long codUsuario) {
		this.codUsuario = codUsuario;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public List<TbPerfil> getPerfis() {
		return perfis;
	}

	public void setPerfis(List<TbPerfil> perfis) {
		this.perfis = perfis;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return perfis;
	}

	@Override
	public String getPassword() {
		return senha;
	}

	@Override
	public String getUsername() {
		return login;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
