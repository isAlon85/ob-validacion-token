INSERT INTO role(name, description) VALUES ('USER' , 'User role');
INSERT INTO role(name, description) VALUES ('ADMIN' , 'Admin role');
INSERT INTO ob_user(username, password, email, name , surname, validated, rejected) VALUES ('admin@validation.com' , '$2a$10$bPkErVt0Zii5mRrN4wfU2OeMGxzvvD3CgWzFdOiQgSOGMiknJUfam' , 'admin@validation.com', 'Admin', 'Admin', true, false);
INSERT INTO ob_user(username, password, email, name , surname, validated, rejected) VALUES ('prueba1@postman.com' , '$2a$10$bPkErVt0Zii5mRrN4wfU2OeMGxzvvD3CgWzFdOiQgSOGMiknJUfam' , 'prueba1@postman.com', 'Pepito', 'Grillo', false, false);
INSERT INTO user_roles(user_id, role_id) VALUES (1 , 1);
INSERT INTO user_roles(user_id, role_id) VALUES (1 , 2);
INSERT INTO user_roles(user_id, role_id) VALUES (2 , 1);