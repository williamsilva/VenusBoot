package com.alvorecer.venus.model.enun;

import com.alvorecer.venus.repository.Users;


public enum StatusUsers {
	ATIVAR {
		@Override
		public void executar(Long[] id, Users users) {
			users.findByIdIn(id).forEach(u -> u.setActive(true));
		}
	},
	
	DESATIVAR {
		@Override
		public void executar(Long[] id, Users users) {
			users.findByIdIn(id).forEach(u -> u.setActive(false));
		}
	};
	
	public abstract void executar(Long[] id, Users users);
	
	
}
