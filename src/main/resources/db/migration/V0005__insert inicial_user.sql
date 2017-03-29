INSERT INTO groups (id, name) VALUES (1, 'Administrador');
INSERT INTO groups (id, name) VALUES (2, 'Atendimento');
INSERT INTO groups (id, name) VALUES (3, 'Financeiro');
INSERT INTO groups (id, name) VALUES (4, 'Marketing');


INSERT INTO user (name, email, password, active) VALUES 
('Admin', 'suporte@alvorecersolucoes.com.br', '$2a$10$g.wT4R0Wnfel1jc/k84OXuwZE02BlACSLfWy6TycGPvvEKvIm86SG', 1);

INSERT INTO user (name, email, password, active) VALUES 
('William Silva', 'william@acquamania.com.br', '$2a$10$g.wT4R0Wnfel1jc/k84OXuwZE02BlACSLfWy6TycGPvvEKvIm86SG', 4);

INSERT INTO permission VALUES (1, 'ROLE_ALVORECER_MASTER');
INSERT INTO permission VALUES (2, 'ROLE_REGISTER_ATTENDENCE');
INSERT INTO permission VALUES (3, 'ROLE_LIST_ATTENDENCE');
INSERT INTO permission VALUES (4, 'ROLE_LIST_CLIENTS');
INSERT INTO permission VALUES (5, 'ROLE_REGISTER_CLIENT');
INSERT INTO permission VALUES (6, 'ROLE_LIST_USERS');
INSERT INTO permission VALUES (7, 'ROLE_REGISTER_USERS');

INSERT INTO group_permission (id_groups, id_permission) VALUES (1, 1);
INSERT INTO group_permission (id_groups, id_permission) VALUES (2, 2);
INSERT INTO group_permission (id_groups, id_permission) VALUES (2, 3);

INSERT INTO user_groups (id_user, id_groups) VALUES (
	(SELECT id FROM user WHERE email = 'suporte@alvorecersolucoes.com.br'), 1);