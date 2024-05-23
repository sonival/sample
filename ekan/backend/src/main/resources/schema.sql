CREATE TABLE beneficiario (
  
  id bigint NOT NULL AUTO_INCREMENT,
  nome varchar(100) NOT NULL,
  telefone varchar(20) NOT NULL,
  data_nascimento date,
  data_inclusao datetime,
  data_atualizacao datetime,
  
  PRIMARY KEY (id)
);

CREATE TABLE documento (
  
  id bigint NOT NULL AUTO_INCREMENT,
  tipo_documento varchar(10) NOT NULL,
  descricao varchar(100) NOT NULL,
  data_inclusao datetime,
  data_atualizacao datetime,
  beneficiario_id bigint NOT NULL,
  
  PRIMARY KEY (id),
  CONSTRAINT fk_documento_beneficiario FOREIGN KEY (beneficiario_id) REFERENCES beneficiario(id)
);

CREATE TABLE authUser (

  id bigint NOT NULL AUTO_INCREMENT,
  login varchar(30) NOT NULL,
  password varchar(100) NOT NULL,
  PRIMARY KEY (id)
);


INSERT INTO `authUser` (`login`, `password`) VALUES ('teste', '$2y$10$6JqrQtgHqVp6c3BJmX7JfeiTSstwU26I.ZrqlUj6fY6XcetIcUIhu');


