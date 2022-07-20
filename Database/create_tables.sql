-- Alterar secuencias
ALTER SEQUENCE IF EXISTS usuario_id_usuario_seq RESTART;
ALTER SEQUENCE IF EXISTS sede_id_sede_seq RESTART;

-- Dropping tables for testing
DROP TABLE IF EXISTS usuario CASCADE;
DROP TABLE IF EXISTS tipo_usuario CASCADE;
DROP TABLE IF EXISTS sede CASCADE;

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

-- Datos estáticos, no se cambian a menos que queramos agregar más tipos de usuarios
INSERT INTO tipo_usuario (nombre)
VALUES ('admin'), ('gerente'), ('jefe_taller'),( 'vendedor'), ( 'indefinido') ;

-- Tabla usuario
CREATE TABLE IF NOT EXISTS usuario (
     id_usuario SERIAL,
     cedula text NOT NULL,
     contraseña text NOT NULL,
     email text NOT NULL,
     nombre text NOT NULL,
     apellido text NOT NULL,
     modificado date NOT NULL,
     avatar text,
     activo bool,
     joined date NOT NULL,
     fecha_nacimiento date NOT NULL,
     telefono text,
     last_session text,
     id_tipo_usuario INT,
     user_type varchar(20) NOT NULL,
     sede text,
     cedula_creado_por text NOT NULL,
     PRIMARY KEY (id_usuario),
     CONSTRAINT "FK_usuario.id_tipo"
         FOREIGN KEY (id_tipo_usuario)
             REFERENCES tipo_usuario(id_tipo_usuario)
);

-- Tabla sede
CREATE TABLE IF NOT EXISTS sede (
     id_sede SERIAL,
     direccion text NOT NULL,
     telefono text NOT NULL,
     nombre_sede text NOT NULL,
     activo boolean NOT NULL,
     ciudad text NOT NULL,
     fecha_creacion date NOT NULL,
     fecha_modificado date NOT NULL,
     PRIMARY KEY (id_sede)
);

-- Tabla sesión, guarda los usuarios que están loggeados en este momento en la aplicación
-- Una vez los usuarios cierran la aplicación o cierran sesión, el usuario se borra de esta tabla.
CREATE TABLE IF NOT EXISTS sesion (
    id_sesion SERIAL,
    id_usuario INT,
    CONSTRAINT "FK_usuario.id_usuario"
        FOREIGN KEY (id_usuario)
            REFERENCES usuario(id_usuario)

);

INSERT INTO sede (direccion, telefono, nombre_sede, activo, ciudad, fecha_creacion, fecha_modificado)
VALUES ('Cra 1cBIs', '342 345 5433', 'Simon Bolivar', true, 'Cali', '08-08-2000', '08-08-2000');

