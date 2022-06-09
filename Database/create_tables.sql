-- Dropping tables for testing
DROP TABLE IF EXISTS usuario CASCADE;

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
  PRIMARY KEY (id_usuario)
);