INSERT INTO groups (id, name) VALUES (1, 'Administrador');
INSERT INTO groups (id, name) VALUES (2, 'Atendimento');
INSERT INTO groups (id, name) VALUES (3, 'Financeiro');
INSERT INTO groups (id, name) VALUES (4, 'Marketing');


INSERT INTO user (name, email, password, active) VALUES 
('Administrador', 'suporte@alvorecersolucoes.com.br', '$2a$10$g.wT4R0Wnfel1jc/k84OXuwZE02BlACSLfWy6TycGPvvEKvIm86SG', 1);

INSERT INTO user (name, email, password, active) VALUES 
('William Silva', 'william@alvorecersolucoes.com.br', '$2a$10$g.wT4R0Wnfel1jc/k84OXuwZE02BlACSLfWy6TycGPvvEKvIm86SG', 1);

INSERT INTO permission (name) VALUES ('ROLE_ALVORECER_MASTER');
INSERT INTO permission (name) VALUES ('ROLE_REGISTER_ATTENDENCE');
INSERT INTO permission (name) VALUES ('ROLE_LIST_ATTENDENCE');
INSERT INTO permission (name) VALUES ('ROLE_LIST_CLIENTS');
INSERT INTO permission (name) VALUES ('ROLE_REGISTER_CLIENT');
INSERT INTO permission (name) VALUES ('ROLE_LIST_USERS');
INSERT INTO permission (name) VALUES ('ROLE_REGISTER_USERS');
INSERT INTO permission (name) VALUES ('ROLE_LIST_RESERVE');
INSERT INTO permission (name) VALUES ('ROLE_REGISTER_RESERVE');

INSERT INTO group_permission (id_groups, id_permission) VALUES (1, 1);
INSERT INTO group_permission (id_groups, id_permission) VALUES (2, 2);
INSERT INTO group_permission (id_groups, id_permission) VALUES (2, 3);

INSERT INTO user_groups (id_user, id_groups) VALUES (
	(SELECT id FROM user WHERE email = 'suporte@alvorecersolucoes.com.br'), 1);
	
INSERT INTO user_groups (id_user, id_groups) VALUES (
	(SELECT id FROM user WHERE email = 'william@alvorecersolucoes.com.br'), 1);