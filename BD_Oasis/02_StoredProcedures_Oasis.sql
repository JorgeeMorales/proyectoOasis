-- ------------------------------------------------------------------------ --
-- Archivo: 02_StoredProcedures_Oasis.sql                                        		--
-- Version: 1.0                                                     		--
-- Autor:   Francisco Javier Rocha Aldana   								--
-- Email:   rochaaldanafcojavier@gmail.com / 21000459@alumnos.utleon.edu.mx	--
-- Fecha de elaboracion: 20-12-2021                                 		--
-- ------------------------------------------------------------------------ --

USE oasis;

-- Stored Procedure para insertar nuevos Empleados.
DROP PROCEDURE IF EXISTS insertarEmpleado;
DELIMITER $$
CREATE PROCEDURE insertarEmpleado(	/* Datos de Usuario */
                                    IN var_nombreDeUsuario		VARCHAR(129),	-- 1
									IN var_contrasenia			VARCHAR(129),	-- 2

                                    /* Datos Personales */
                                    IN var_nombre 				VARCHAR(50),	-- 3
                                    IN var_primerApellido	 	VARCHAR(40),	-- 4
                                    IN var_telCelular           VARCHAR(20),	-- 5
                                    IN var_correo 				VARCHAR(129),	-- 6
                                    
                                    /* Datos Empleado */
									IN var_segundoApellido 		VARCHAR(40),	-- 7
									IN var_genero              	VARCHAR(2),		-- 8
									IN var_fechaDeNacimiento 	VARCHAR(11),	-- 9
									IN var_calle 				VARCHAR(129),	-- 10
									IN var_numero 				VARCHAR(20),	-- 11
									IN var_colonia 				VARCHAR(40),	-- 12
									IN var_codigoPostal 		VARCHAR(25),	-- 13
									-- IN var_ciudad 				VARCHAR(40),	-- 14
									-- IN var_estado 				VARCHAR(40),	-- 15
                                    
                                    /* Valores de Retorno */
                                    OUT	var_idUsuario       	INT,            -- 14
                                    OUT	var_idPersona       	INT,            -- 15
                                    OUT	var_idEmpleado      	INT,            -- 16
                                    OUT	var_numeroUnico     	VARCHAR(65),    -- 17
                                    OUT var_lastToken       	VARCHAR(65)     -- 18
				)                                    
    BEGIN        
        -- Insertamos los datos de seguridad del Empleado:
        INSERT INTO usuario(nombreDeUsuario, contrasenia) 
                    VALUES(var_nombreDeUsuario, var_contrasenia);
        -- Obtenemos el ID de Usuario que se generó:
        SET var_idUsuario=LAST_INSERT_ID();
        
        -- Comenzamos insertando los datos de la Persona:
        INSERT INTO persona(nombre, primerApellido, telCelular, correo)
                    VALUES(var_nombre, var_primerApellido, var_telCelular, var_correo);
        -- Obtenemos el ID de Persona que se generó:
        SET var_idPersona=LAST_INSERT_ID();

        --  Generamos el numero unico de empleado.        
        SET var_numeroUnico='';
        --  Agregamos la primera letra del apellidoPaterno:
        IF  LENGTH(var_primerApellido) >= 1 THEN
            SET var_numeroUnico = SUBSTRING(var_primerApellido, 1, 1);
        ELSE
            SET var_numeroUnico = 'X';
        END IF;
        --  Agregamos la segunda letra del apellidoPaterno:
        IF  LENGTH(var_primerApellido) >= 2 THEN
            SET var_numeroUnico = CONCAT(var_numeroUnico, SUBSTRING(var_primerApellido, 2, 1));
        ELSE
            SET var_numeroUnico = CONCAT(var_numeroUnico, 'X');
        END IF;        
        --  Agregamos el timestamp:
        SET var_numeroUnico = CONCAT(var_numeroUnico, CAST(UNIX_TIMESTAMP() AS CHAR));
        -- Codificamos el numero unico generado:
        SET var_numeroUnico = MD5(var_numeroUnico);

        -- Finalmente, insertamos en la tabla Empleado:
        INSERT INTO empleado(numeroUnico, segundoApellido, genero, fechaDeNacimiento, calle, numero, colonia, codigoPostal, ciudad, estado,
																													idUsuario, idPersona)
                    VALUES(var_numeroUnico, var_segundoApellido, var_genero, STR_TO_DATE(var_fechaDeNacimiento, '%d/%m/%Y'), var_calle, var_numero, var_colonia, var_codigoPostal, var_ciudad, var_estado,
																												var_idUsuario, var_idPersona);
        -- Obtenemos el ID del Empleado que se genero:
        SET var_idEmpleado=LAST_INSERT_ID();
    END
$$
DELIMITER ;

-- Stored Procedure para actualizar datos de Empleados.
DROP PROCEDURE IF EXISTS actualizarEmpleado;
DELIMITER $$
CREATE PROCEDURE actualizarEmpleado(	/* Datos de Usuario */
                                    IN var_nombreDeUsuario		VARCHAR(129),	-- 1
									IN var_contrasenia			VARCHAR(129),	-- 2

                                    /* Datos Personales */
                                    IN var_nombre 				VARCHAR(50),	-- 3
                                    IN var_primerApellido	 	VARCHAR(40),	-- 4
                                    IN var_telCelular           VARCHAR(20),	-- 5
                                    IN var_correo 				VARCHAR(129),	-- 6
                                    
                                    /* Datos Empleado */
									IN var_segundoApellido 		VARCHAR(40),	-- 7
									IN var_genero              	VARCHAR(2),		-- 8
									IN var_fechaDeNacimiento 	DATE,			-- 9
									IN var_calle 				VARCHAR(129),	-- 10
									IN var_numero 				VARCHAR(20),	-- 11
									IN var_colonia 				VARCHAR(40),	-- 12
									IN var_codigoPostal 		VARCHAR(25),	-- 13
									-- IN var_ciudad 				VARCHAR(40),	-- 14
									-- IN var_estado 				VARCHAR(40),	-- 15
                                    
                                    IN	var_idUsuario       	INT,            -- 14
                                    IN	var_idPersona       	INT,            -- 15
                                    IN	var_idEmpleado      	INT             -- 16
				)                                    
    BEGIN
		-- Actualizamos los datos de seguridad del Empleado:
        UPDATE usuario SET nombreDeUsuario=var_nombreDeUsuario,
							contrasenia=var_contrasenia
                        WHERE idUsuario=var_idUsuario;
                        
        -- Actualizamos los datos de la Persona:
        UPDATE persona SET nombre=var_nombre, primerApellido=var_primerApellido,
							telCelular=var_telCelular, correo=var_correo
                        WHERE idPersona=var_idPersona;
		
        -- Actualizamos los datos del empleado
		UPDATE empleado SET segundoApellido=var_segundoApellido, genero=var_genero, fechaDeNacimiento=var_fechaDeNacimiento, calle=var_calle,
							numero=var_numero, colonia=var_colonia, codigoPostal=var_codigoPostal, ciudad=var_ciudad, estado=var_estado
					  WHERE idEmpleado=var_idEmpleado;
    END
$$
DELIMITER ;

-- Stored Procedure para insertar nuevos Clientes.
DROP PROCEDURE IF EXISTS insertarCliente;
DELIMITER $$
CREATE PROCEDURE insertarCliente(	/* Datos Personales */
									IN var_nombre 				VARCHAR(50),	-- 1
									IN var_primerApellido	 	VARCHAR(40),	-- 2
									IN var_telCelular           VARCHAR(20),	-- 3
									IN var_correo               VARCHAR(129),	-- 4
                                    
                                    /* Valores de Retorno */
                                    OUT	var_idPersona       	INT,			-- 5
                                    OUT	var_idCliente       	INT				-- 6
				)                                    
    BEGIN        
        -- Comenzamos insertando los datos de la Persona:
        INSERT INTO persona(nombre, primerApellido, telCelular, correo)
                    VALUES(var_nombre, var_primerApellido, var_telCelular, var_correo);
        -- Obtenemos el ID de Persona que se generó:
        SET var_idPersona=LAST_INSERT_ID();

        -- Insertamos en la tabla Cliente:
        INSERT INTO cliente(idPersona)
                    VALUES(var_idPersona);
        -- Obtenemos el ID del Cliente que se genero:
        SET var_idCliente=LAST_INSERT_ID();
    END
$$
DELIMITER ;

-- Stored Procedure para actualizar datos de Clientes.
DROP PROCEDURE IF EXISTS actualizarCliente;
DELIMITER $$
CREATE PROCEDURE actualizarCliente(	/* Datos Personales */
                                    IN var_nombre 				VARCHAR(50),	-- 1
									IN var_primerApellido	 	VARCHAR(40),	-- 2
									IN var_telCelular           VARCHAR(20),	-- 3
									IN var_correo               VARCHAR(129),	-- 4

                                    IN	var_idPersona       	INT 			-- 5
				)                                    
    BEGIN        
        -- Comenzamos actualizando los datos de la Persona:
        UPDATE persona SET nombre=var_nombre, primerApellido=var_primerApellido,
							telCelular=var_telCelular, correo=var_correo
                        WHERE idPersona=var_idPersona;
    END
$$
DELIMITER ;

-- Stored Procedure para insertar una Habitacion.
DROP PROCEDURE IF EXISTS insertarHabitacion;
DELIMITER $$
CREATE PROCEDURE insertarHabitacion(	/* Datos Habitacion */
                                        IN var_nombre				VARCHAR(45),	-- 1
										IN var_descripcion			VARCHAR(500),	-- 2
										IN var_tematica				VARCHAR(45),	-- 3
										IN var_precioRenta			DOUBLE,			-- 4
                                        IN  var_fotografia      	LONGTEXT,		-- 5
                                    
										/* Valores de Retorno */
										OUT	var_idHabitacion       	INT,			-- 6
                                        OUT var_codigoUnico			VARCHAR(65)		-- 7
				)                                    
    BEGIN        
        -- Insertamos los datos de la Habitacion:
        INSERT INTO habitacion(codigoUnico, nombre, descripcion, tematica, precioRenta, fotografia)
						VALUES("Error al cargar codigo", var_nombre, var_descripcion, var_tematica, var_precioRenta, var_fotografia);
		-- Obtenemos el ID del Empleado que se genero:
        SET var_idHabitacion=LAST_INSERT_ID();
        
        -- Generamos el numero unico de la habitacion:
        SET var_codigoUnico=CONCAT("HABITACION",var_idHabitacion,var_tematica);
        
        -- La ingresamos a la tabla donde lo habiamos puesto null
        UPDATE habitacion SET codigoUnico=var_codigoUnico WHERE idHabitacion=var_idHabitacion;
    END
$$
DELIMITER ;

-- Stored Procedure para actualizar datos de la Habitacion.
DROP PROCEDURE IF EXISTS actualizarHabitacion;
DELIMITER $$
CREATE PROCEDURE actualizarHabitacion(	/* Datos Habitacion */
                                        IN var_nombre				VARCHAR(45),	-- 1
										IN var_tipo					VARCHAR(30),	-- 2
										IN var_descripcion			VARCHAR(255),	-- 3
										IN var_tematica				VARCHAR(45),	-- 4
										IN var_precioRenta			DOUBLE,			-- 5

										IN var_idHabitacion       	INT,			-- 6
                                        
                                        OUT var_codigoUnico			VARCHAR(65)		-- 7
				)                                    
    BEGIN        
        -- Generamos el numero unico de la habitacion:
        SET var_codigoUnico=CONCAT("HABITACION"+var_idHabitacion+var_tematica);
        
        -- Comenzamos actualizando los datos de la Habitacion:
        UPDATE habitacion SET codigoUnico=var_codigoUnico, nombre=var_nombre, tipo=var_tipo,
								descripcion=var_descripcion, tematica=var_tematica, precioRentaPorHora=var_precioRentaPorHora
                        WHERE idHabitacion=var_idHabitacion;
    END
$$
DELIMITER ;

-- Stored Procedure para insertar un Producto.
DROP PROCEDURE IF EXISTS insertarProducto;
DELIMITER $$
CREATE PROCEDURE insertarProducto(	/* Datos Producto */
                                        IN var_nombreDelProducto	VARCHAR(45),	-- 1
										IN var_codigoDeBarras		VARCHAR(65),	-- 2
										IN var_marca 				VARCHAR(45),	-- 3
										IN var_categoria			VARCHAR(15),	-- 4
										IN var_existencias			INT,			-- 5
										IN var_precioCompra			DOUBLE,			-- 6
										IN var_precioVenta			DOUBLE,			-- 7
                                    
										/* Valores de Retorno */
										OUT	var_idProducto       	INT				-- 8
				)                                    
    BEGIN        
        -- Insertamos los datos del Producto:
        INSERT INTO producto(nombreDelProducto, codigoDeBarras, marca, categoria, existencias, precioCompra, precioVenta)
						VALUES(var_nombreDelProducto, var_codigoDeBarras, var_marca, var_categoria, var_existencias, var_precioCompra, var_precioVenta);
		-- Obtenemos el ID del Producto que se genero:
        SET var_idProducto=LAST_INSERT_ID();
    END
$$
DELIMITER ;

-- Stored Procedure para actualizar un Producto.
DROP PROCEDURE IF EXISTS actualizarProducto;
DELIMITER $$
CREATE PROCEDURE actualizarProducto(	/* Datos Producto */
                                        IN var_nombreDelProducto	VARCHAR(45),	-- 1
										IN var_codigoDeBarras		VARCHAR(65),	-- 2
										IN var_marca 				VARCHAR(45),	-- 3
										IN var_categoria			VARCHAR(15),	-- 4
										IN var_existencias			INT,			-- 5
										IN var_precioCompra			DOUBLE,			-- 6
										IN var_precioVenta			DOUBLE,			-- 7

										IN var_idProducto       	INT				-- 8
				)                                    
    BEGIN        
        -- Actualizamos los datos de la Habitacion:
        UPDATE producto SET nombreDelProducto=var_nombreDelProducto, codigoDeBarras=var_codigoDeBarras, marca=var_marca,
							categoria=var_categoria, existencias=var_existencias, precioCompra=var_precioCompra,
							precioVenta=var_precioVenta
					WHERE idProducto=var_idProducto;
    END
$$
DELIMITER ;


DROP PROCEDURE IF EXISTS generarToken;
DELIMITER $$
CREATE PROCEDURE generarToken(
							IN var_idUsuario INT,
                            IN var_token VARCHAR(65))
						BEGIN
                        UPDATE usuario SET lastToken = var_token,
                        dateLastToken = CURRENT_TIMESTAMP
					WHERE idUsuario=var_idUsuario;
				END
$$
DELIMITER ;

-- Stored Procedure para generar nuevos tokens de Empleados.
DROP PROCEDURE IF EXISTS generarNuevoTokenEmpleado;
DELIMITER $$
CREATE PROCEDURE generarNuevoTokenEmpleado(IN  var_idUsuario INT, 
                                           OUT var_lastToken VARCHAR(65), 
                                           OUT var_dateLastToken VARCHAR(25))
    BEGIN        
        -- Comenzamos generando el nuevo Token:        
        SET var_lastToken = MD5(CONCAT('UUID-', var_idUsuario, '-', CAST(UNIX_TIMESTAMP() AS CHAR)));
        
        -- Actualizamos la tabla de usuarios:
        SET var_dateLastToken = NOW();
        UPDATE usuario SET lastToken = var_lastToken, dateLastToken = var_dateLastToken WHERE idUsuario = var_idUsuario;
    END
$$
DELIMITER ;
