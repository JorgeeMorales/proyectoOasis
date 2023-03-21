-- ------------------------------------------------------------------------ --
-- Archivo: 01_DDL_Oasis.sql                                        		--
-- Version: 1.0                                                     		--
-- Autor:   Francisco Javier Rocha Aldana   								--
-- Email:   rochaaldanafcojavier@gmail.com / 21000459@alumnos.utleon.edu.mx	--
-- Fecha de elaboracion: 20-12-2021                                 		--
-- ------------------------------------------------------------------------ --

DROP DATABASE IF EXISTS oasis;

CREATE DATABASE oasis;

USE oasis;

-- ------------- TABLA USUARIO -------------- --
CREATE TABLE usuario(
	idUsuario           INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    nombreDeUsuario     VARCHAR(129) UNIQUE NOT NULL,
    contrasenia         VARCHAR(129) NOT NULL,
    lastToken           VARCHAR(65) NOT NULL DEFAULT '',
    dateLastToken       DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- ------------- TABLA PERSONA -------------- --
CREATE TABLE persona(
	idPersona 			INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    nombre 				VARCHAR(50) NOT NULL,
	primerApellido	 	VARCHAR(40) NOT NULL,
	telCelular          VARCHAR(20) NOT NULL DEFAULT '',
    correo              VARCHAR(129) NOT NULL DEFAULT ''
);

-- ------------- TABLA EMPLEADO -------------- --
CREATE TABLE empleado(
    idEmpleado			INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    numeroUnico         VARCHAR(65) NOT NULL DEFAULT '',
    segundoApellido 	VARCHAR(40) NOT NULL DEFAULT '',
    genero              VARCHAR(2) NOT NULL DEFAULT 'O', -- Genero: M; F; O;
    fechaDeNacimiento 	DATE NOT NULL,
    estatus             INT NOT NULL DEFAULT 1,
    calle 				VARCHAR(129) NOT NULL DEFAULT '',
	numero 				VARCHAR(20)  NOT NULL DEFAULT '',
	colonia 			VARCHAR(40) NOT NULL DEFAULT '',
	codigoPostal 		VARCHAR(25) NOT NULL DEFAULT '',
    idUsuario           INT NOT NULL,
    idPersona			INT NOT NULL,
    CONSTRAINT fk_empleado_usuario FOREIGN KEY (idUsuario) 
                REFERENCES usuario(idUsuario),
	CONSTRAINT fk_empleado_persona FOREIGN KEY (idPersona) 
                REFERENCES persona(idPersona)
);

-- ------------- TABLA CLIENTE -------------- --
CREATE TABLE cliente(
    idCliente			INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    idPersona			INT NOT NULL,
    CONSTRAINT fk_cliente_persona FOREIGN KEY (idPersona) 
                REFERENCES persona(idPersona) 
);

-- ------------- TABLA HABITACION -------------- --
CREATE TABLE habitacion(
	idHabitacion 		INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    codigoUnico			VARCHAR(65) NOT NULL,
    nombre				VARCHAR(45) NOT NULL,
    -- tipo				VARCHAR(30) NOT NULL, -- SENCILLA, VIP, PRIMERA CLASE
    estatus             INT NOT NULL DEFAULT 1,
    descripcion			VARCHAR(500) NOT NULL,
    tematica			VARCHAR(45) NOT NULL,
    precioRenta			DOUBLE NOT NULL DEFAULT 0.0,
    fotografia			LONGTEXT
);

SELECT * FROM habitacion;

-- ------------- TABLA PRODUCTO -------------- --
CREATE TABLE producto(
	idProducto			INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    nombreDelProducto	VARCHAR(45) NOT NULL,
	codigoDeBarras		VARCHAR(65) NOT NULL DEFAULT '',
    marca 				VARCHAR(45) NOT NULL,
    estatus             INT NOT NULL DEFAULT 1,
    categoria			VARCHAR(15), -- COMIDA, BEBIDA, POSTRE, DULCE, OTROS
    existencias			INT NOT NULL,
    precioCompra		DOUBLE NOT NULL DEFAULT 0.0,
    precioVenta			DOUBLE NOT NULL DEFAULT 0.0
);

-- ------------- TABLA RESERVAR -------------- --
CREATE TABLE reservar(
	idReservar						INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    codigoBarrasReservacion			VARCHAR(65) NOT NULL DEFAULT '',
    fechaCreacionReservacion		DATE NOT NULL,
    fechaAReservar					DATE NOT NULL,
    idProducto						INT NOT NULL,
    idHabitacion					INT NOT NULL,
    idCliente						INT NOT NULL,
    CONSTRAINT fk_reservar_producto FOREIGN KEY (idProducto) 
                REFERENCES producto(idProducto),
	CONSTRAINT fk_reservar_habitacion FOREIGN KEY (idHabitacion) 
                REFERENCES habitacion(idHabitacion),
	CONSTRAINT fk_reservar_cliente FOREIGN KEY (idCliente) 
                REFERENCES cliente(idCliente)
);

-- ------------- TABLA TICKET -------------- --
CREATE TABLE ticket(
	idTicket		INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    total			DOUBLE NOT NULL DEFAULT 0.0,
    cantidad		INT NOT NULL,
    idReservar		INT NOT NULL,
    CONSTRAINT fk_ticket_reservar FOREIGN KEY (idReservar) 
                REFERENCES reservar(idReservar) 
);

-- ------------- TABLA BITACORA RESERVACIONES -------------- --
CREATE TABLE bitacoraReservaciones(
	idBitacoraReservacion 		INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    estatus             		VARCHAR(15), -- PENDIENTE, CANCELO, ASISTIO
    idTicket					INT NOT NULL,
    CONSTRAINT fk_bitacoraReservaciones_ticket FOREIGN KEY (idTicket) 
                REFERENCES ticket(idTicket)
);
