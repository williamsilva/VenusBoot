CREATE TABLE products(
	id BIGINT(5) PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    description VARCHAR(50) NOT NULL,
    valid DATE,
    status VARCHAR(30) NOT NULL,
    value DECIMAL(10, 2) NOT NULL

) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE reserve(
	id BIGINT(5) PRIMARY KEY AUTO_INCREMENT,
	creation_date DATETIME NOT NULL,
    visit_date DATE,
    valor DECIMAL(10,2),
    valor_antecipado DECIMAL(10,2),
    comments VARCHAR(500),
    voucher VARCHAR(10) NOT NULL,
    status_voucher VARCHAR(20) NOT NULL,
    id_client BIGINT(20) NOT NULL,
    id_user BIGINT(20) NOT NULL,
    FOREIGN KEY (id_client) REFERENCES client(id),
    FOREIGN KEY (id_user) REFERENCES user(id)

) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE item_voucher(
	id BIGINT(5) PRIMARY KEY AUTO_INCREMENT,
	amount INTEGER NOT NULL,
	unitaryValue DECIMAL(10,2),
	id_product BIGINT(20) NOT NULL,
	id_reserve BIGINT(20) NOT NULL,
    FOREIGN KEY (id_product) REFERENCES products(id),
    FOREIGN KEY (id_reserve) REFERENCES reserve(id)

) ENGINE=InnoDB DEFAULT CHARSET=utf8;