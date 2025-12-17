drop database if exists floreria;
create database if not exists floreria;
use floreria;

create table empleados (
    emp_id int auto_increment primary key,
    emp_nombre varchar(100) not null,
    emp_apellidos varchar(150) not null,
    emp_edad int,
    emp_puesto varchar(50),
    emp_telefono varchar(20),
    emp_correo varchar(100),
    emp_contra varchar(100),
    emp_activo tinyint default 0
);

INSERT INTO empleados (emp_nombre, emp_apellidos, emp_edad, emp_puesto, emp_telefono, emp_correo, emp_contra) VALUES 
('Juan', 'Pérez García', 30, 'Administrador', '5551234567', 'juan.perez@example.com', '123'),
('María', 'López Ramírez', 28, 'Vendedora', '5559876543', 'maria.lopez@example.com', 'maria123'),
('Carlos', 'Hernández Ruiz', 35, 'Encargado de inventario', '5557654321', 'carlos.hernandez@example.com', 'carlos123'),
('Ana', 'Martínez Díaz', 26, 'Diseñadora floral', '5552345678', 'ana.martinez@example.com', 'ana123'),
('Luis', 'Gómez Torres', 40, 'Repartidor', '5558765432', 'luis.gomez@example.com', 'luis123');


select * from empleados;
