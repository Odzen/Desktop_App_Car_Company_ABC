-- Dropping tables for testing
DROP TABLE IF EXISTS usuario CASCADE;
DROP TABLE IF EXISTS tipo_usuario CASCADE;
DROP TABLE IF EXISTS administrador CASCADE;
DROP TABLE IF EXISTS jefe_de_taller CASCADE;
DROP TABLE IF EXISTS gerente CASCADE;
DROP TABLE IF EXISTS vendedor CASCADE;

--Table creation

-- id 1 - admin
-- id 2 - gerente
-- id 3 - jefe_de_taller
-- id 4 - vendedor
CREATE TABLE tipo_usuario (
  id_tipo_usuario SERIAL,
  nombre varchar(30) NOT NULL,
  PRIMARY KEY (id_tipo_usuario)
);


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
  fecha_nacimiento date NOT NULL,
  last_session date,
  id_tipo_usuario INT,
  user_type varchar(30) NOT NULL,
  PRIMARY KEY (id_usuario),
  CONSTRAINT "FK_usuario.id_tipo"
    FOREIGN KEY (id_tipo_usuario)
      REFERENCES tipo_usuario(id_tipo_usuario)
);