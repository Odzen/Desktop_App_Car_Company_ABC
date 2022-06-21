-- Dropping tables for testing
DROP TABLE IF EXISTS usuario CASCADE;
DROP TABLE IF EXISTS tipo_usuario CASCADE;

--Table creation

-- id 1 - admin
-- id 2 - gerente
-- id 3 - jefe_de_taller
-- id 4 - vendedor
-- id 5 - indefinido
CREATE TABLE tipo_usuario (
                              id_tipo_usuario SERIAL,
                              nombre varchar(30) NOT NULL,
                              PRIMARY KEY (id_tipo_usuario)
);

INSERT INTO tipo_usuario (nombre)
VALUES ('admin'), ('gerente'), ('jefe_taller'),( 'vendedor'), ( 'indefinido') ;


CREATE TABLE usuario (
                         id_usuario SERIAL,
                         contraseña text NOT NULL,
                         email text NOT NULL,
                         nombre text NOT NULL,
                         apellido text NOT NULL,
                         modificado date NOT NULL,
                         avatar text,
                         activo bool,
                         joined date NOT NULL,
                         fecha_nacimiento date NOT NULL,
                         last_session timestamp,
                         id_tipo_usuario INT,
                         user_type varchar(20) NOT NULL,
                         PRIMARY KEY (id_usuario),
                         CONSTRAINT "FK_usuario.id_tipo"
                             FOREIGN KEY (id_tipo_usuario)
                                 REFERENCES tipo_usuario(id_tipo_usuario)
);