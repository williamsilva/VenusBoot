CREATE TABLE client(
	id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(80) NOT NULL,
    type_client VARCHAR(15) NOT NULL,
    cpf_cnpj VARCHAR(30),
    phone_number VARCHAR(20),
    reference VARCHAR(100),    
    street VARCHAR(100),    
    code_postal VARCHAR(15), 
	neighborhood VARCHAR(50),
    celular VARCHAR(20),
    email VARCHAR(50) NOT NULL,    
    number VARCHAR(15),       
    id_city BIGINT(20),
    FOREIGN KEY (id_city) REFERENCES city(id)

) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE attendance(
	id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    hour_register DATETIME,
    date_register DATE NOT NULL,
    channel VARCHAR(30) NOT NULL,
    knws_park VARCHAR(5) NOT NULL,
    as_park VARCHAR(30) NOT NULL,
    subject VARCHAR(30) NOT NULL,
    closed VARCHAR(5) NOT NULL,
    return_contact VARCHAR(5),
    date_return DATETIME,
    comments VARCHAR(200) NOT NULL,
    id_client BIGINT(20) NOT NULL,
    FOREIGN KEY (id_client) REFERENCES client(id)
    
) ENGINE=InnoDB DEFAULT CHARSET=utf8;   