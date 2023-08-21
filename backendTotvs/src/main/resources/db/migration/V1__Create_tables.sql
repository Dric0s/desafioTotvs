CREATE TABLE client(
    id bigint AUTO_INCREMENT  PRIMARY KEY  NOT NULL ,
    name VARCHAR(255) NOT NULL,
    address varchar(255),
    neighborhood varchar(255)
);

CREATE TABLE client_phone(
    id bigint AUTO_INCREMENT  PRIMARY KEY NOT NULL,
    client_id bigint NOT NULL,
    phone VARCHAR(20) UNIQUE NOT NULL,
    FOREIGN KEY (client_id) REFERENCES client(id)
);
