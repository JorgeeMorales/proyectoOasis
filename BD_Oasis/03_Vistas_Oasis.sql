-- ------------------------------------------------------------------------ --
-- Archivo: 03_Vistas_Oasis.sql                                        		--
-- Version: 1.0                                                     		--
-- Autor:   Francisco Javier Rocha Aldana   								--
-- Email:   rochaaldanafcojavier@gmail.com / 21000459@alumnos.utleon.edu.mx	--
-- Fecha de elaboracion: 20-12-2021                                 		--
-- ------------------------------------------------------------------------ --

USE oasis;

-- Vista que obtiene todos los datos de los Empleados:
DROP VIEW IF EXISTS v_empleados;
CREATE VIEW v_empleados AS
    SELECT  P.idPersona,
			E.idEmpleado,
            E.numeroUnico,
			P.nombre,
			P.primerApellido,
            E.segundoApellido,
			P.telCelular,
			P.correo,
            
			E.genero,
			DATE_FORMAT(E.fechaDeNacimiento, '%d/%m/%Y') AS fechaNacimiento,
			E.estatus,
			E.calle,
			E.numero,
			E.colonia,
			E.codigoPostal,
            
            U.idUsuario,
			U.nombreDeUsuario,
			U.contrasenia,
			U.lastToken,
            DATE_FORMAT(U.dateLastToken, '%d/%m/%Y %H:%i:%S') AS dateLastToken
    FROM    persona P
            INNER JOIN empleado E ON E.idPersona = P.idPersona
            INNER JOIN usuario U ON U.idUsuario = E.idUsuario;
            
SELECT * FROM v_empleados;
            
-- Vista que obtiene todos los datos de los Clientes:
DROP VIEW IF EXISTS v_clientes;
CREATE VIEW v_clientes AS
    SELECT  P.idPersona,
			P.nombre,
			P.primerApellido,
			P.telCelular,
			P.correo, 
            
            C.idCliente
    FROM    persona P
            INNER JOIN cliente C ON C.idPersona = P.idPersona;

SELECT * FROM v_clientes;

-- Vista que obtiene todos los datos de las reservacion actual:
DROP VIEW IF EXISTS v_reservar;
CREATE VIEW v_reservar AS
	 SELECT C.idCliente,
			C.idPersona,

			PE.nombre AS nombrePersona,
			PE.primerApellido,
			PE.telCelular,
			PE.correo,

			R.idReservar,
			R.codigoBarrasReservacion,
			R.fechaCreacionReservacion,
			R.fechaAReservar,

            PR.idProducto,
			PR.nombreDelProducto,
			PR.codigoDeBarras,
			PR.marca,
			PR.estatus AS estatusProducto,
			PR.categoria,
			PR.existencias,
			PR.precioCompra,
			PR.precioVenta,
            
            H.idHabitacion,
			H.codigoUnico,
			H.nombre AS nombreHabitacion,
			H.estatus AS estatusHabitacion,
			H.descripcion,
			H.tematica,
			H.precioRenta
    FROM 	reservar R
			INNER JOIN producto PR ON PR.idProducto=R.idProducto
            INNER JOIN habitacion H ON H.idHabitacion=R.idHabitacion
            INNER JOIN cliente C ON C.idCliente=R.idCliente
            INNER JOIN persona PE ON PE.idPersona=C.idPersona;
            
SELECT * FROM v_reservar;

-- Vista que obtiene todos los datos del Ticket:
DROP VIEW IF EXISTS v_ticket;
CREATE VIEW v_ticket AS
	 SELECT T.idTicket,
			T.total,
			T.cantidad,
			T.idReservar,
            
            C.idCliente,
			C.idPersona,

			PE.nombre AS nombrePersona,
			PE.primerApellido,
			PE.telCelular,
			PE.correo,

			R.codigoBarrasReservacion,
			R.fechaCreacionReservacion,
			R.fechaAReservar,

            PR.idProducto,
			PR.nombreDelProducto,
			PR.codigoDeBarras,
			PR.marca,
			PR.estatus AS estatusProducto,
			PR.categoria,
			PR.existencias,
			PR.precioCompra,
			PR.precioVenta,
            
            H.idHabitacion,
			H.codigoUnico,
			H.nombre AS nombreHabitacion,
			H.estatus AS estatusHabitacion,
			H.descripcion,
			H.tematica,
			H.precioRenta
    FROM ticket T
		 INNER JOIN reservar R ON R.idReservar=T.idReservar
         INNER JOIN producto PR ON PR.idProducto=R.idProducto
		 INNER JOIN habitacion H ON H.idHabitacion=R.idHabitacion
		 INNER JOIN cliente C ON C.idCliente=R.idCliente
		 INNER JOIN persona PE ON PE.idPersona=C.idPersona;
         
SELECT * FROM v_ticket;

-- Vista que obtiene todos los datos del Ticket:
DROP VIEW IF EXISTS v_bitacoraReservaciones;
CREATE VIEW v_bitacoraReservaciones AS
	SELECT	BR.idBitacoraReservacion,
			BR.estatus AS estatusBitacora,
			BR.idTicket,
            
			T.total,
			T.cantidad,
			T.idReservar,
            
            C.idCliente,
			C.idPersona,

			PE.nombre AS nombrePersona,
			PE.primerApellido,
			PE.telCelular,
			PE.correo,

			R.codigoBarrasReservacion,
			R.fechaCreacionReservacion,
			R.fechaAReservar,

            PR.idProducto,
			PR.nombreDelProducto,
			PR.codigoDeBarras,
			PR.marca,
			PR.estatus AS estatusProducto,
			PR.categoria,
			PR.existencias,
			PR.precioCompra,
			PR.precioVenta,
            
            H.idHabitacion,
			H.codigoUnico,
			H.nombre AS nombreHabitacion,
			H.estatus AS estatusHabitacion,
			H.descripcion,
			H.tematica,
			H.precioRenta
    FROM bitacoraReservaciones BR
		 INNER JOIN ticket T ON T.idTicket=BR.idTicket
		 INNER JOIN reservar R ON R.idReservar=T.idReservar
         INNER JOIN producto PR ON PR.idProducto=R.idProducto
		 INNER JOIN habitacion H ON H.idHabitacion=R.idHabitacion
		 INNER JOIN cliente C ON C.idCliente=R.idCliente
		 INNER JOIN persona PE ON PE.idPersona=C.idPersona;
         
SELECT * FROM v_bitacoraReservaciones;
     