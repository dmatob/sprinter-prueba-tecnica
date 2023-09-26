DROP TABLE IF EXISTS ARTICULO;
DROP TABLE IF EXISTS ARTICULO_TIPO;
DROP TABLE IF EXISTS ARTICULO_CATEGORIA;

CREATE TABLE ARTICULO_CATEGORIA (
	ID BIGINT AUTO_INCREMENT PRIMARY KEY,
	CODIGO VARCHAR_IGNORECASE(40) NOT NULL,
	DESCRIPCION VARCHAR(80)
);

ALTER TABLE ARTICULO_CATEGORIA ADD CONSTRAINT AK_ARTICULO_CATEGORIA UNIQUE (CODIGO);

CREATE TABLE ARTICULO_TIPO (
	ID BIGINT AUTO_INCREMENT PRIMARY KEY,
	ID_ARTICULO_CATEGORIA BIGINT NOT NULL,
	CODIGO VARCHAR_IGNORECASE(40) NOT NULL,
	DESCRIPCION VARCHAR(80)
);

ALTER TABLE ARTICULO_TIPO ADD CONSTRAINT AK_ARTICULO_TIPO UNIQUE (CODIGO);
ALTER TABLE ARTICULO_TIPO ADD CONSTRAINT FK_ART_TIPO_ART_CATEGORIA FOREIGN KEY (ID_ARTICULO_CATEGORIA) REFERENCES ARTICULO_CATEGORIA(ID);
CREATE INDEX IDX_ART_TIPO_ART_CATEGORIA ON ARTICULO_TIPO (ID_ARTICULO_CATEGORIA); 


CREATE TABLE ARTICULO (
	ID BIGINT AUTO_INCREMENT PRIMARY KEY,
	CODIGO VARCHAR_IGNORECASE(40) NOT NULL,
	ID_ARTICULO_TIPO BIGINT NOT NULL,
	DESCRIPCION VARCHAR(500),
	PRECIO_EUROS NUMERIC(20,2),
	ULTIMA_MODIFICACION TIMESTAMP
);

ALTER TABLE ARTICULO ADD CONSTRAINT AK_ARTICULO UNIQUE (CODIGO);

ALTER TABLE ARTICULO ADD CONSTRAINT FK_ARTICULO_ARTICULO_TIPO FOREIGN KEY (ID_ARTICULO_TIPO) REFERENCES ARTICULO_TIPO(ID);
CREATE INDEX IDX_ARTICULO_ARTICULO_TIPO ON ARTICULO (ID_ARTICULO_TIPO); 
