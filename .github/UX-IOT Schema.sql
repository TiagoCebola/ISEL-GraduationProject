-- PostgreSQL
-- #### Schema ou Database ####

/* DROPS ALL TABLES */
drop table if exists COORDENADAS;
drop table if exists SINOTIC_WIDGET;

drop table if exists SINOTICO ;
drop table if exists WIDGET_IOT ;

drop table if exists PESSOA ;

-- #### Cria a base de dados UX-IoT ####
CREATE SCHEMA if not exists UX_IoT;

-- #### Posiciona como Database corrente ####
-- Torna a execucao independente da selecao acima: 'Active Catalog/Schema'
SET search_path TO UX_IoT;
DROP EXTENSION IF EXISTS "uuid-ossp";
CREATE EXTENSION IF NOT EXISTS "uuid-ossp" schema UX_IoT;

CREATE TABLE PESSOA (
	username varchar(200) not null,
	password varchar(200) not null,
	role varchar(200) not null,
	
	email varchar(200) not null,
	dateOfBirth DATE not null,
	image varchar(200),
	
	PRIMARY KEY (username) 
);


CREATE TABLE WIDGET_IOT (
	wiotId UUID NOT NULL DEFAULT uuid_generate_v4(),
	iotType varchar(200) not null,
	comun_proto varchar(200) not null,
	svgImage varchar(200) not null,
	
	primary key (wiotID)
);


CREATE TABLE SINOTICO (
	designation varchar(200) not null,
	username varchar(200) not null,
	
	foreign key (username) REFERENCES PESSOA (username) on delete cascade,
	primary key (designation, username)
);

CREATE TABLE SINOTIC_WIDGET (
	name varchar(200) not null,

	designation varchar(200) not null,
	username varchar(200) not null,
	wiotId UUID not null,
	restID SERIAL not null,

	foreign key (designation, username) references SINOTICO (designation, username) on delete cascade,
	foreign key (wiotId) references WIDGET_IOT (wiotId) on delete cascade,
	primary key (name, designation, username)
);


CREATE TABLE COORDENADAS (
	eixo_x float not null,
	eixo_y float not null,
	CHECK (eixo_x BETWEEN 1 AND 200),
	CHECK (eixo_y BETWEEN 1 AND 200),
	
	name varchar(200) not null,
	designation varchar(200) not null,
	username varchar(200) not null,
	
	foreign key (name, designation, username) references SINOTIC_WIDGET (name, designation, username) on delete cascade,
	primary key (eixo_x, eixo_y)
);


CREATE OR REPLACE FUNCTION SETUP_DEFAULT_SYNOPTIC_DESIGNATION()
RETURNS TRIGGER AS
$$
BEGIN
	INSERT INTO SINOTICO(designation, username) VALUES ('SYNOPTIC 1', NEW.USERNAME); 
	RETURN NEW;
END;
$$ 
LANGUAGE PLPGSQL;


CREATE TRIGGER SETUP_DEFAULT_STATES
AFTER INSERT
ON PESSOA
FOR EACH ROW
EXECUTE FUNCTION SETUP_DEFAULT_SYNOPTIC_DESIGNATION();


/* DEFAULT UUID
 * WIDGET_IOT ID: 3779a41b-7920-4f9c-b3aa-75d9302a2abe
 */


/*password = 1234 */
INSERT INTO PESSOA (username, password, role, email, dateOfBirth, image) 
	VALUES ('Tiago Macker', '$2a$12$EBGw5t0mVB1u6QenwspUWesaTRkObEsxn5CZUKlrqHx6eX7sAOtQ6', 'admin', 'tiago@gmail.com', '01-01-2020', 'https://randomuser.me/api/portraits/men/42.jpg');
	
INSERT INTO PESSOA (username, password, role, email, dateOfBirth, image) 
	VALUES ('Ruben', '$2a$12$EBGw5t0mVB1u6QenwspUWesaTRkObEsxn5CZUKlrqHx6eX7sAOtQ6', 'user', 'tiago@gmail.com', '01-01-2020', 'https://randomuser.me/api/portraits/men/42.jpg');

INSERT INTO PESSOA (username, password, role, email, dateOfBirth, image) 
	VALUES ('Gustavo', '$2a$12$EBGw5t0mVB1u6QenwspUWesaTRkObEsxn5CZUKlrqHx6eX7sAOtQ6', 'admin', 'gustavo@gmail.com', '01-01-2020', 'https://randomuser.me/api/portraits/men/26.jpg');	

INSERT into WIDGET_IOT (wiotId, iotType, comun_proto, svgImage) 
	values ('3779a41b-7920-4f9c-b3aa-75d9302a2abe', 'CAMARA', 'ONVIF', '\widgets\cam.svg');

insert into SINOTIC_WIDGET (name, designation, username, wiotId)
	values ('CAM 1', 'SYNOPTIC 1', 'Tiago Macker', '3779a41b-7920-4f9c-b3aa-75d9302a2abe');

insert into	COORDENADAS (eixo_x, eixo_y, name, designation, username)
	values ('101', '101', 'CAM 1','SYNOPTIC 1', 'Tiago Macker');
