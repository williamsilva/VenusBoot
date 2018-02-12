INSERT INTO permission (name) VALUES ('ROLE_CANCELAR_VOUCHER');

INSERT INTO group_permission (id_groups, id_permission) VALUES (1, 
	(SELECT id FROM permission WHERE name = 'ROLE_CANCELAR_VOUCHER'));

INSERT INTO group_permission (id_groups, id_permission) VALUES (2, 
	(SELECT id FROM permission WHERE name = 'ROLE_REGISTER_RESERVE'));
	
INSERT INTO group_permission (id_groups, id_permission) VALUES (2, 
	(SELECT id FROM permission WHERE name = 'ROLE_LIST_RESERVE'));