package com.alanfalcoski.cursomc.services;

import org.springframework.security.core.context.SecurityContextHolder;

import com.alanfalcoski.cursomc.security.UserSS;

public class UserService {
	
	/* Buscar usuário logado utilizando método do Security efetuando casting para a classe UserSS */
	public static UserSS authenticated() {
		try {
			return (UserSS) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		}
		catch (Exception e) {
			return null;
		}
	}

}
