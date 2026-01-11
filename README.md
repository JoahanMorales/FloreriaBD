# FloreriaBD

Sistema de gestion para floreria desarrollado en Java con interfaz grafica Swing y base de datos MySQL.

## Descripcion

FloreriaBD es una aplicacion de escritorio para la administracion de una floreria. Permite gestionar productos, empleados, ventas y generar reportes en PDF. Cuenta con un sistema de autenticacion para empleados y una interfaz grafica moderna con fuentes personalizadas.

## Funcionalidades

- Inicio de sesion con autenticacion de empleados
- Gestion de productos (nombre, descripcion, precio, stock, categoria)
- Registro y consulta de ventas con detalle
- Carrito de compras con calculo automatico de totales
- Multiples metodos de pago (efectivo, tarjeta, transferencia)
- Generacion de tickets de venta en formato PDF
- Panel de perfil de usuario
- Interfaz grafica personalizada con colores y fuentes propias

## Tecnologias

- Java (Swing para GUI)
- MySQL (base de datos relacional)
- JDBC (conexion a base de datos)
- iTextPDF (generacion de reportes PDF)
- Apache NetBeans (IDE de desarrollo)

## Estructura del proyecto

```
FloreriaBD/
├── src/
│   ├── Floreria/           # Modelos (Productos, Empleados, Ventas, DetalleVenta)
│   ├── FloreriaService/    # Servicios de conexion y operaciones con BD
│   ├── floreriabd/         # Interfaz grafica principal
│   │   └── panel/          # Paneles (Inicio, Perfil, Productos, Ventas)
│   ├── Imagenes/           # Recursos graficos
│   └── Recursos/           # Fuentes personalizadas
└── sql/                    # Scripts de base de datos
```

## Base de datos

El sistema utiliza MySQL con las siguientes tablas:
- categorias: Clasificacion de productos
- productos: Inventario de flores y arreglos
- empleados: Usuarios del sistema
- ventas: Registro de transacciones
- detalle_venta: Productos incluidos en cada venta

## Configuracion

1. Crear la base de datos ejecutando el script `sql/floreriaf.sql`
2. Configurar las credenciales de conexion en `FloreriaService/Conexion.java`
3. Compilar y ejecutar desde NetBeans o con el archivo `build.xml`