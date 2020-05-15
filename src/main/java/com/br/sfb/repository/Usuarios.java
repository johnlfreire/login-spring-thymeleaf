package com.br.sfb.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.br.sfb.model.Usuario;



public interface Usuarios extends JpaRepository<Usuario, Long> {

	public Optional<Usuario> findByEmail(String email);

	public List<Usuario> findByCodigoIn(Long[] codigos);
	
	@Query("select distinct p.nome from Usuario u inner join u.grupos g inner join g.permissoes p where u = :usuario")
	public List<String> permissoes(Usuario usuario);
}
