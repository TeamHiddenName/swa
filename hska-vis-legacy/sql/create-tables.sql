CREATE TABLE webshop.category (
	id INT NOT NULL AUTO_INCREMENT,
	name VARCHAR(255) NOT NULL,
	PRIMARY KEY (id)
) ENGINE=InnoDB;

CREATE TABLE webshop.role (
	id INT NOT NULL AUTO_INCREMENT,
	level1 INT,
	type VARCHAR(255),
	PRIMARY KEY (id)
) ENGINE=InnoDB;

CREATE TABLE webshop.product (
	id INT NOT NULL AUTO_INCREMENT,
	details VARCHAR(255),
	name VARCHAR(255),
	price DOUBLE,
	category_id INT,
	PRIMARY KEY (id)
) ENGINE=InnoDB;

CREATE TABLE webshop.customer (
	id INT NOT NULL AUTO_INCREMENT,
	name VARCHAR(255) NOT NULL,
	lastname VARCHAR(255) NOT NULL,
	password VARCHAR(255) NOT NULL,
	username VARCHAR(255) NOT NULL,
	role INT NOT NULL,
	PRIMARY KEY (id)
) ENGINE=InnoDB;

CREATE UNIQUE INDEX UK_mufchskagt7e1w4ksmt9lum5l ON webshop.customer (username ASC);

CREATE INDEX FK74aoh99stptslhotgf41fitt0 ON webshop.customer (role ASC);

CREATE INDEX FK1mtsbur82frn64de7balymq9s ON webshop.product (category_id ASC);


CREATE TABLE product-db.product (
    id INT NOT NULL AUTO_INCREMENT,
    details VARCHAR(255),
    name VARCHAR(255),
    price DOUBLE,
    category_id INT,
    PRIMARY KEY (id)
) ENGINE=InnoDB;

CREATE INDEX FK1mtsbur82frn64de7balymq9t ON product-db.product (category_id ASC);

CREATE TABLE category-db.category (
                                  id INT NOT NULL AUTO_INCREMENT,
                                  name VARCHAR(255) NOT NULL,
                                  PRIMARY KEY (id)
) ENGINE=InnoDB;

