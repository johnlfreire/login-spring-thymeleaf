package com.br.sfb.repository;

import org.springframework.stereotype.Repository;

import com.br.sfb.model.Usuario;

@Repository
public interface PasswordResetEmailRepository {

public void sends(Usuario usuario);
	
}
