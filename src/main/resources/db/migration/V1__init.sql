CREATE TABLE ROLES
(
    id            INTEGER      NOT NULL AUTO_INCREMENT,
    name          VARCHAR(128) NOT NULL,
    description   VARCHAR(128) NOT NULL,
    creation_date VARCHAR(128),
    PRIMARY KEY (id)
);

CREATE TABLE ORGANIZATIONS
(
    id            INTEGER      NOT NULL AUTO_INCREMENT,
    name          VARCHAR(128) NOT NULL,
    image         VARCHAR(128) NOT NULL,
    address       VARCHAR(128),
    phone         INTEGER,
    email         VARCHAR(128) NOT NULL UNIQUE,
    welcome_text  TEXT         NOT NULL,
    about_us_text TEXT,
    creation_date VARCHAR(128),
    PRIMARY KEY (id)
);

CREATE TABLE ACTIVITIES
(
    id            INTEGER      NOT NULL AUTO_INCREMENT,
    content       VARCHAR(128) NOT NULL,
    creation_date DATE,
    deleted       BOOLEAN,
    image         VARCHAR(128) NOT NULL,
    name          VARCHAR(128) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE USERS
(
    id            INTEGER      NOT NULL AUTO_INCREMENT,
    first_name    VARCHAR(128) NOT NULL,
    creation_date DATE,
    deleted       BOOLEAN,
    photo         VARCHAR(128),
    last_name     VARCHAR(128) NOT NULL,
    email         VARCHAR(128) NOT NULL UNIQUE,
    password      VARCHAR(128) NOT NULL,
    role_id       BIGINT,
    PRIMARY KEY (id)
);