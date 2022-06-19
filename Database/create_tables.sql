-- Dropping tables for testing
DROP TABLE IF EXISTS usuario CASCADE;
DROP TABLE IF EXISTS administrador CASCADE;
DROP TABLE IF EXISTS jefe_de_taller CASCADE;
DROP TABLE IF EXISTS gerente CASCADE;
DROP TABLE IF EXISTS vendedor CASCADE;

--Table creation
CREATE TABLE usuario (
  id_usuario SERIAL,
  contraseña varchar(30) NOT NULL,
  email varchar(30) NOT NULL,
  nombre varchar(30) NOT NULL,
  apellido varchar(30) NOT NULL,
  modificado date NOT NULL,
  avatar varchar(30),
  activo bool,
  joined date NOT NULL,
  user_type varchar(30) NOT NULL,
  fecha_nacimiento date NOT NULL,
  PRIMARY KEY (id_usuario)
);

CREATE TABLE administrador(
  cedula_admin VARCHAR(30),
  id_administrador INT,
  id_creado_por VARCHAR(30),
  PRIMARY KEY (cedula_admin),
  CONSTRAINT "FK_admin.id_creado_por"
    FOREIGN KEY (id_creado_por)
      REFERENCES administrador(cedula_admin),
  CONSTRAINT "FK_admin.id_administrador"
    FOREIGN KEY (id_administrador)
	  REFERENCES usuario(id_usuario)
);

CREATE TABLE gerente(
  cedula_gerente VARCHAR(30),
  id_gerente INT,
  id_creado_por VARCHAR(30),
  PRIMARY KEY (cedula_gerente),
  CONSTRAINT "FK_gerente.id_creado_por"
    FOREIGN KEY (id_creado_por)
      REFERENCES administrador(cedula_admin),
  CONSTRAINT "FK_gerente.id_gerente"
    FOREIGN KEY (id_gerente)
	  REFERENCES usuario(id_usuario)
);

CREATE TABLE jefe_de_taller(
  cedula_jefe_de_taller VARCHAR(30),
  id_jefe_de_taller INT,
  id_creado_por VARCHAR(30),
  PRIMARY KEY (cedula_jefe_de_taller),
  CONSTRAINT "FK_jefe_de_taller.id_creado_por"
    FOREIGN KEY (id_creado_por)
      REFERENCES gerente(cedula_gerente),
  CONSTRAINT "FK_jefe_de_taller.id_jefe_de_taller"
    FOREIGN KEY (id_jefe_de_taller)
	  REFERENCES usuario(id_usuario)
);

CREATE TABLE vendedor(
  cedula_vendedor VARCHAR(30),
  id_vendedor INT,
  id_creado_por VARCHAR(30),
  PRIMARY KEY (cedula_vendedor),
  CONSTRAINT "FK_vendedor.id_creado_por"
    FOREIGN KEY (id_creado_por)
      REFERENCES gerente(cedula_gerente),
  CONSTRAINT "FK_vendedor.id_vendedor"
    FOREIGN KEY (id_vendedor)
	  REFERENCES usuario(id_usuario)
);