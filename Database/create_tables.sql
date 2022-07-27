-- Alterar secuencias
ALTER SEQUENCE IF EXISTS usuario_id_usuario_seq RESTART;
ALTER SEQUENCE IF EXISTS sede_id_sede_seq RESTART;
ALTER SEQUENCE IF EXISTS tipo_usuario_id_tipo_usuario_seq RESTART;
ALTER SEQUENCE IF EXISTS repuesto_id_repuesto_seq RESTART;
ALTER SEQUENCE IF EXISTS venta_id_venta_seq RESTART;
ALTER SEQUENCE IF EXISTS estado_orden_id_estado_orden_seq RESTART;
ALTER SEQUENCE IF EXISTS orden_de_trabajo_id_orden_seq RESTART;
ALTER SEQUENCE IF EXISTS cotizacion_id_cotizacion_seq RESTART;
ALTER SEQUENCE IF EXISTS solicitud_id_solicitud_seq RESTART;

-- Dropping tables for testing
DROP TABLE IF EXISTS usuario CASCADE;
DROP TABLE IF EXISTS tipo_usuario CASCADE;
DROP TABLE IF EXISTS sede CASCADE;
DROP TABLE IF EXISTS repuesto CASCADE;
DROP TABLE IF EXISTS automovil CASCADE;
DROP TABLE IF EXISTS cliente CASCADE;
DROP TABLE IF EXISTS venta CASCADE;
DROP TABLE IF EXISTS estado_orden CASCADE;
DROP TABLE IF EXISTS orden_de_trabajo CASCADE;
DROP TABLE IF EXISTS repuestos_por_ordenes CASCADE;
DROP TABLE IF EXISTS cotizacion CASCADE;
DROP TABLE IF EXISTS solicitud CASCADE;

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
VALUES ('admin'), ('gerente'), ('jefe_taller'),( 'vendedor'),( 'cliente'), ( 'indefinido') ;

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
INSERT INTO sede (direccion, telefono, nombre_sede, activo, ciudad, fecha_creacion, fecha_modificado)
VALUES ('Cra 1cBIs', '342 345 5433', 'Simon Bolivar', true, 'Cali', '08-08-2000', '08-08-2000');



-- Tabla de Automoviles
CREATE TABLE IF NOT EXISTS automovil (
     placa text NOT NULL,
     marca text NOT NULL,
     cilindraje INT NOT NULL,
     color text NOT NULL,
     modelo text NOT NULL,
     año text NOT NULL,
     precio INT NOT NULL,
     activo bool NOT NULL,
     cedula_creado_por text NOT NULL,
     sede text,
     fecha_creacion date NOT NULL,
     fecha_modificado date NOT NULL,
     PRIMARY KEY (placa)
);

-- Tabla repuesto
CREATE TABLE IF NOT EXISTS repuesto (
    id_repuesto SERIAL,
    activo bool NOT NULL,
    marca text NOT NULL,
    nombre text NOT NULL,
    cantidad INT NOT NULL,
    cedula_creado_por text NOT NULL,
    fecha_creacion date NOT NULL,
    fecha_modificado date NOT NULL,
    sede text NOT NULL,
    PRIMARY KEY (id_repuesto)
);


-- Tabla de Clientes
CREATE TABLE IF NOT EXISTS cliente (
   cedula_cliente text NOT NULL,
   email text NOT NULL,
   nombre text NOT NULL,
   apellido text NOT NULL,
   fecha_modificado date NOT NULL,
   fecha_creacion date NOT NULL,
   direccion text NOT NULL,
   activo bool NOT NULL,
   fecha_nacimiento date NOT NULL,
   telefono text NOT NULL,
   id_tipo_usuario INT,
   user_type varchar(20) NOT NULL,
   cedula_creado_por text NOT NULL,
   sede text,
   PRIMARY KEY (cedula_cliente),
   CONSTRAINT "FK_cliente.id_tipo"
       FOREIGN KEY (id_tipo_usuario)
           REFERENCES tipo_usuario(id_tipo_usuario)
);

-- id 1 - en_espera
-- id 2 - en_progreso
-- id 3 - terminada
CREATE TABLE estado_orden (
  id_estado_orden SERIAL,
  nombre varchar(30) NOT NULL,
  PRIMARY KEY (id_estado_orden)
);

INSERT INTO estado_orden(nombre)
VALUES ('en_espera'), ('en_progreso'), ('terminada');


-- Tabla de Ordenes de trabajo
CREATE TABLE IF NOT EXISTS orden_de_trabajo (
     id_orden SERIAL,
     fecha_creacion DATE NOT NULL,
     fecha_modificado DATE NOT NULL,
     activo bool NOT NULL,
     cedula_cliente text NOT NULL,
     cedula_jefe_de_taller text NOT NULL,
     placa_automovil text NOT NULL,
     id_estado_orden INT,
     estado varchar(20) NOT NULL,
     PRIMARY KEY (id_orden),
     CONSTRAINT "FK_orden_de_trabajo.placa_automovil"
         FOREIGN KEY (placa_automovil)
             REFERENCES automovil(placa),
     CONSTRAINT "FK_orden_de_trabajo.cedula_cliente"
         FOREIGN KEY (cedula_cliente)
            REFERENCES cliente(cedula_cliente),
     CONSTRAINT "FK_orden_de_trabajo.id_estado_orden"
         FOREIGN KEY (id_estado_orden)
             REFERENCES estado_orden(id_estado_orden)
);

-- Tabla de Respuestos por ordenes
CREATE TABLE IF NOT EXISTS repuestos_por_ordenes (
    id_orden INT,
    id_repuesto INT,
    cantidad INT NOT NULL,
    cedula_creado_por text NOT NULL,
    fecha_modificado date NOT NULL,
    fecha_creacion date NOT NULL,
    activo bool NOT NULL,
    PRIMARY KEY (id_orden, id_repuesto),
    CONSTRAINT "FK_repuestos_por_ordenes.id_orden"
        FOREIGN KEY (id_orden)
            REFERENCES orden_de_trabajo(id_orden),
    CONSTRAINT "FK_repuestos_por_ordenes.id_repuesto"
        FOREIGN KEY (id_repuesto)
            REFERENCES repuesto(id_repuesto)
);

-- Tabla de Cotizaciones
CREATE TABLE IF NOT EXISTS cotizacion (
    id_cotizacion SERIAL,
    IVA numeric NOT NULL,
    TOTAL_IVA numeric NOT NULL,
    TOTAL_SIN_IVA numeric NOT NULL,
    descripcion text,
    fecha_modificado date NOT NULL,
    fecha_creacion date NOT NULL,
    cedula_cliente text NOT NULL,
    cedula_vendedor text NOT NULL,
    placa_automovil text,
    id_orden_trabajo INT,
    CONSTRAINT "FK_cotizacion.cedula_cliente"
        FOREIGN KEY (cedula_cliente)
            REFERENCES cliente(cedula_cliente)
);


-- Tabla de Ventas
CREATE TABLE IF NOT EXISTS venta (
    id_venta SERIAL,
    IVA INT NOT NULL,
    TOTAL_IVA INT NOT NULL,
    TOTAL_SIN_IVA INT NOT NULL,
    descripcion text,
    fecha_modificado date NOT NULL,
    fecha_creacion date NOT NULL,
    cedula_cliente text NOT NULL,
    cedula_vendedor text NOT NULL,
    placa_automovil text,
    id_orden_trabajo INT,
    sede text NOT NULL,
    PRIMARY KEY (id_venta),
    CONSTRAINT "FK_venta.placa_automovil"
     FOREIGN KEY (placa_automovil)
         REFERENCES automovil(placa),
    CONSTRAINT "FK_venta.cedula_cliente"
     FOREIGN KEY (cedula_cliente)
         REFERENCES cliente(cedula_cliente),
    CONSTRAINT "FK_venta.id_orden_trabajo"
     FOREIGN KEY (id_orden_trabajo)
         REFERENCES orden_de_trabajo(id_orden)
);

-- Tabla de Solicitudes
CREATE TABLE IF NOT EXISTS solicitud (
  id_solicitud SERIAL,
  estado bool NOT NULL,
  fecha_creacion date NOT NULL,
  fecha_modificado date NOT NULL,
  cedula_vendedor text NOT NULL,
  cedula_gerente text NOT NULL,
  placa_automovil text NOT NULL,
  CONSTRAINT "FK_solicitud.placa_automovil"
      FOREIGN KEY (placa_automovil)
          REFERENCES automovil(placa)
);






