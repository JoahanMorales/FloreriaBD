DROP DATABASE IF EXISTS floreriaf;
CREATE DATABASE floreriaf CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
USE floreriaf;

-- Tabla de Categorías (necesaria para la FK en productos)
CREATE TABLE categorias (
  id_categoria INT AUTO_INCREMENT PRIMARY KEY,
  nombre_cate VARCHAR(50) NOT NULL,
  descripcion_cate TEXT NOT NULL
);

-- Tabla de Productos
CREATE TABLE productos (
  id_producto INT AUTO_INCREMENT PRIMARY KEY,
  nombre_prod VARCHAR(50) NOT NULL,
  descrip_prod TEXT NOT NULL,
  precio_prod DECIMAL(8,2) NOT NULL,
  stock_dispo INT NOT NULL,
  estado_prod VARCHAR(20) NOT NULL,
  id_categoria INT,
  FOREIGN KEY (id_categoria) REFERENCES categorias(id_categoria)
);

-- Tabla de Empleados
CREATE TABLE empleados (
  emp_id INT AUTO_INCREMENT PRIMARY KEY,
  emp_nombre VARCHAR(100) NOT NULL,
  emp_apellidos VARCHAR(150) NOT NULL,
  emp_edad INT,
  emp_puesto VARCHAR(50),
  emp_telefono VARCHAR(20),
  emp_correo VARCHAR(100),
  emp_contra VARCHAR(100),
  emp_activo TINYINT DEFAULT 0
);

-- Tabla de Ventas
CREATE TABLE ventas (
  id_venta INT AUTO_INCREMENT PRIMARY KEY,
  fecha_ven DATE NOT NULL,
  total_ven DECIMAL(8,2) NOT NULL,
  metodo_pago VARCHAR(15) NOT NULL,
  emp_id INT,
  FOREIGN KEY (emp_id) REFERENCES empleados(emp_id)
);

-- Tabla Detalle de Venta
CREATE TABLE detalle_venta (
  id_dtll_ven INT AUTO_INCREMENT PRIMARY KEY,
  cantidad_ven INT NOT NULL,
  prec_u_ven DECIMAL(8,2) NOT NULL,
  subtotal_ven DECIMAL(8,2) NOT NULL,
  id_venta INT,
  id_producto INT,
  FOREIGN KEY (id_venta) REFERENCES ventas(id_venta),
  FOREIGN KEY (id_producto) REFERENCES productos(id_producto)
);


INSERT INTO empleados (emp_nombre, emp_apellidos, emp_edad, emp_puesto, emp_telefono, emp_correo, emp_contra) VALUES 
('Juan', 'Pérez García', 30, 'Administrador', '5551234567', 'juan.perez@example.com', '123'),
('María', 'López Ramírez', 28, 'Vendedora', '5559876543', 'maria.lopez@example.com', 'maria123'),
('Carlos', 'Hernández Ruiz', 35, 'Encargado de inventario', '5557654321', 'carlos.hernandez@example.com', 'carlos123'),
('Ana', 'Martínez Díaz', 26, 'Diseñadora floral', '5552345678', 'ana.martinez@example.com', 'ana123'),
('Luis', 'Gómez Torres', 40, 'Repartidor', '5558765432', 'luis.gomez@example.com', 'luis123');

INSERT INTO categorias (nombre_cate, descripcion_cate) VALUES
('Rosas', 'Productos hechos con rosas de diferentes colores.'),
('Girasoles', 'Arreglos y ramos que incluyen girasoles.'),
('Tulipanes', 'Flores elegantes en cajas o bouquets con tulipanes.'),
('Eventos', 'Decoraciones florales para bodas, XV años, aniversarios, etc.'),
('Plantas', 'Plantas ornamentales o en maceta, como orquídeas.'),
('Fúnebres', 'Arreglos florales para servicios funerarios.'),
('Bouquets', 'Ramos mixtos de temporada con diferentes flores.'),
('Decorativos', 'Centros de mesa y arreglos para decoración general.');


INSERT INTO productos (nombre_prod, descrip_prod, precio_prod, stock_dispo, estado_prod, id_categoria) VALUES
('Ramo de Rosas Rojas', 'Ramo de 12 rosas rojas con follaje y envoltura decorativa.', 450.00, 20, 'Activo', 1),
('Arreglo Girasoles', 'Arreglo floral con 5 girasoles en base de madera.', 320.00, 15, 'Activo', 2),
('Caja con Tulipanes', 'Caja elegante con 8 tulipanes de colores surtidos.', 500.00, 10, 'Activo', 3),
('Rosa Individual', 'Rosa roja individual con listón decorativo.', 50.00, 100, 'Activo', 1),
('Centro de Mesa', 'Centro de mesa con flores frescas y follaje para eventos.', 600.00, 5, 'Activo', 4),
('Orquídea en Maceta', 'Orquídea blanca en maceta de cerámica decorativa.', 750.00, 7, 'Activo', 5),
('Bouquet Primaveral', 'Bouquet con flores surtidas de temporada.', 380.00, 12, 'Activo', 3),
('Arreglo Fúnebre', 'Arreglo floral para servicios funerarios con flores blancas.', 850.00, 3, 'Activo', 6);
