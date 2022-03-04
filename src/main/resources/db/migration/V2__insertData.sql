INSERT INTO ROLES (name, description, creation_date) values
                                        ('ADMIN', 'Administrator Role', NOW()),
                                        ('USER', 'User Role', NOW());

INSERT INTO ORGANIZATIONS (name, image, address, phone, email, welcome_text, about_us_text ,creation_date) values
                                        ('Somos Mas', 'somos-mas.jpg', 'Direccion n', 1212323, 'somosmas@gmail.com', 'Welcome to Somos Mas', 'Somos una organizacion',NOW()),
                                        ('Unicef', 'unicef.jpg', 'Direccion u', 1212321, 'unicef@gmail.com', 'Welcome to Unicef', 'Somos una organizacion',NOW());

INSERT INTO ACTIVITIES (name, content, image, deleted, creation_date) values
                                        ('Actividades deportivas', 'Deporte para los niños','img.jpg',false,NOW()),
                                        ('Colecta de alimentos', 'Colecta de ropa','img.jpg',false,NOW());
