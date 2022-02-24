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