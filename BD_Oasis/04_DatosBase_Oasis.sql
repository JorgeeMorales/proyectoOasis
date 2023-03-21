-- ------------------------------------------------------------------------ --
-- Archivo: 03_DatosBase_Oasis.sql                                        		--
-- Version: 1.0                                                     		--
-- Autor:   Francisco Javier Rocha Aldana   								--
-- Email:   rochaaldanafcojavier@gmail.com / 21000459@alumnos.utleon.edu.mx	--
-- Fecha de elaboracion: 20-12-2021                                 		--
-- ------------------------------------------------------------------------ --

USE oasismotel;

-- Insercion del Usuario Raiz (Administrador):
CALL insertarEmpleado("Alda","2418Fran11@@","Francisco Javier","Rocha","4778594709","rochaaldanafcojavier@gmail.com",
						"Aldana","H","02/10/2000","Cromo","202","San Jose del Consuelo","37200",
						@out1,@out2,@out3,@out4,@out5);
                        
-- Insercion del Usuario del Cliente:
CALL insertarEmpleado("","","","","","",
						"","O","01/01/2000","","","","",
						@out1,@out2,@out3,@out4,@out5);

SELECT * FROM v_empleados;

CALL insertarCliente("Kevin","Gomez","4772382389","kevingomez@gmail.com",@out1,@out2);

SELECT * FROM v_clientes;

CALL insertarHabitacion("Sauna","Habitacion sencilla incluye:
											cama, 
											baño, 
											regadera, 
											sillon camasutra",
											"Japonesa", 100.00,"dasdasdasdasdasdasd", @out1, @out2);

CALL insertarHabitacion("Sauna","VIP","Habitacion VIP incluye:
											cama, 
											baño, 
											regadera, 
											sillon camasutra,
                                            Jacuzzie",
											"Japonesa", 250.00, @out1, @out2);
                                            
CALL insertarHabitacion("Sauna","PRIMERA CLASE","Habitacion primera clase incluye:
											cama, 
											baño, 
											regadera, 
											sillon camasutra,
                                            Jacuzzie,
                                            Alberca techada para 10 personas,
                                            Asador,
                                            Botella de cortesia",
											"Japonesa", 500.00, @out1, @out2);

SELECT * FROM habitacion;

CALL insertarProducto("Pizza","1826382","Dominos","COMIDA",10,100.00,150.00,@out1);
CALL insertarProducto("Coca Cola","1877424","Coca Cola","BEBIDA",15,10.50,20.00,@out1);
CALL insertarProducto("Pastel de Chocolate","9376452","Costco","POSTRE",5,150.00,200.00,@out1);
CALL insertarProducto("Chicles Orbit","7362514","Sonrix","DULCE",100,5.50,8.00,@out1);
CALL insertarProducto("Pasta de Dientes","9463540","Colgate","OTROS",8,15.50,20.00,@out1);

SELECT * FROM producto;

SELECT * FROM v_empleados VE WHERE VE.nombreDeUsuario="Alda" AND VE.contrasenia="2418Fran11@@";

CALL generarToken(1, "");

SELECT * FROM v_empleados WHERE lastToken="";

UPDATE usuario SET lastToken="" WHERE idUsuario=1;