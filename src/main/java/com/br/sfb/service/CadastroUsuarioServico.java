package com.br.sfb.service;



import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.br.sfb.exception.EmailUsuarioJaCadastradoException;
import com.br.sfb.model.Usuario;
import com.br.sfb.repository.Usuarios;



 

@Service
public class CadastroUsuarioServico {

	@Autowired
	private Usuarios usuarioRepository;
	
	
	
	@Transactional
	public void salvar(Usuario usuario) {
		Optional<Usuario> usuarioExistente = usuarioRepository.findByEmail(usuario.getEmail());

		
		if (usuarioExistente.isPresent() && !usuarioExistente.get().equals(usuario)) {
			throw new EmailUsuarioJaCadastradoException("E-mail já cadastrado");
		}
		usuarioRepository.save(usuario);		
}
}
