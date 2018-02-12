INSERT INTO permission (name) VALUES ('ROLE_REGISTER_PRODUCTS');
INSERT INTO permission (name) VALUES ('ROLE_LIST_PRODUCTS');

INSERT INTO group_permission (id_groups, id_permission) VALUES (2, 
	(SELECT id FROM permission WHERE name = 'ROLE_REGISTER_PRODUCTS'));
	
INSERT INTO group_permission (id_groups, id_permission) VALUES (2, 
	(SELECT id FROM permission WHERE name = 'ROLE_LIST_PRODUCTS'));

INSERT INTO group_permission (id_groups, id_permission) VALUES (2, 
	(SELECT id FROM permission WHERE name = 'ROLE_REGISTER_CLIENT'));
	
INSERT INTO group_permission (id_groups, id_permission) VALUES (2, 
	(SELECT id FROM permission WHERE name = 'ROLE_LIST_CLIENTS'));