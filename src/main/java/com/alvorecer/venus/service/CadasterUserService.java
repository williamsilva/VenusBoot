package com.alvorecer.venus.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.alvorecer.venus.model.Use;
import com.alvorecer.venus.model.enun.StatusUsers;
import com.alvorecer.venus.repository.Users;
import com.alvorecer.venus.service.excepition.SenhaObrigatorioException;
import com.alvorecer.venus.service.excepition.UserRegisterException;

@Service
public class CadasterUserService {

	@Autowired
	private Users users;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Transactional
	public void save(Use user) {

		Optional<Use> userOptional = users.findByEmail(user.getEmail());

		if (userOptional.isPresent() && !userOptional.get().equals(user)) {
			throw new UserRegisterException("E-mail já cadastrado");
		}
		
		if(user.isNew() && StringUtils.isEmpty(user.getPassword())){
			throw new SenhaObrigatorioException("Senha Obrigatoria!");
		}
		
		if(user.isNew() || !StringUtils.isEmpty(user.getPassword())){
			user.setPassword(this.passwordEncoder.encode(user.getPassword()));
		} else if(StringUtils.isEmpty(user.getPassword())){
			user.setPassword(userOptional.get().getPassword());
		}
		
		if (!user.isNew() && user.getActive() == null) {
			user.setActive(userOptional.get().getActive());
		}
		
		user.setConfirmacaoSenha(user.getPassword());
		
		users.save(user);
	}
	
	@Transactional
	public void alterarStatus(Long[] id, StatusUsers status) {
			status.executar(id, users);
	}
}
