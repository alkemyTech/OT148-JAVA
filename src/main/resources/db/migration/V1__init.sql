CREATE TABLE ROLES (
    id INTEGER NOT NULL AUTO_INCREMENT,
    name VARCHAR (128) NOT NULL,
    description VARCHAR (128) NOT NULL,
    creation_date VARCHAR (128),
    PRIMARY KEY (id)
);

CREATE TABLE ORGANIZATIONS (
    id INTEGER NOT NULL AUTO_INCREMENT,
    name VARCHAR (128) NOT NULL,
    image VARCHAR (128) NOT NULL,
    address VARCHAR (128) ,
    phone INTEGER,
    email VARCHAR (128) NOT NULL UNIQUE,
    welcome_text VARCHAR (128) NOT NULL,
    about_us_text VARCHAR (128),
    creation_date VARCHAR (128),
    PRIMARY KEY (id)
);