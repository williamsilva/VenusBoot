CREATE TABLE client(
	id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(80) NOT NULL,
    cpf_cnpj VARCHAR(30),
    phone_number VARCHAR(20),
    date_nascimento DATE,
    reference VARCHAR(100),    
    street VARCHAR(100),    
    code_postal VARCHAR(15), 
	neighborhood VARCHAR(50),
    cell_phone VARCHAR(20),
    email VARCHAR(50) NOT NULL,    
    number VARCHAR(15),
    comments VARCHAR(500),       
    id_city BIGINT(5),
	type_client VARCHAR(15) NOT NULL,
    FOREIGN KEY (id_city) REFERENCES city(id)

) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE attendance(
	id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    hour_register VARCHAR(10),
    date_register DATE NOT NULL,
    channel VARCHAR(30) NOT NULL,
    knws_park VARCHAR(5) NOT NULL,
    as_park VARCHAR(30) NOT NULL,
    subject VARCHAR(30) NOT NULL,
    closed VARCHAR(5) NOT NULL,
    return_contact VARCHAR(5),
    protocol VARCHAR(45) NOT NULL,
    date_return DATETIME,
    comments VARCHAR(200) NOT NULL,
    id_client BIGINT(5) NOT NULL,
    FOREIGN KEY (id_client) REFERENCES client(id)
    
) ENGINE=InnoDB DEFAULT CHARSET=utf8;   