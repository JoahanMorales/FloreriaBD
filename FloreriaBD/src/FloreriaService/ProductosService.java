package FloreriaService;

import Floreria.Productos;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductosService extends Conexion<ProductosService> {
    
    // Método para agregar un nuevo producto
    public boolean agregarProducto(Productos producto) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = getConnection();
            String query = "INSERT INTO productos (nombre_prod, descrip_prod, precio_prod, stock_dispo, estado_prod, id_categoria) VALUES (?, ?, ?, ?, ?, ?)";
            statement = connection.prepareStatement(query);
            
            statement.setString(1, producto.getNombreProd());
            statement.setString(2, producto.getDescripProd());
            statement.setBigDecimal(3, producto.getPrecioProd());
            statement.setInt(4, producto.getStockDispo());
            statement.setString(5, producto.getEstadoProd());
            statement.setInt(6, producto.getIdCategoria());

            int filasInsertadas = statement.executeUpdate();
            return filasInsertadas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return false;
    }
    
    // Método para obtener todos los productos
    public List<Productos> obtenerTodosLosProductos() {
        List<Productos> productos = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();
            String query = "SELECT * FROM productos ORDER BY id_producto";
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Productos producto = new Productos();
                producto.setIdProducto(resultSet.getInt("id_producto"));
                producto.setNombreProd(resultSet.getString("nombre_prod"));
                producto.setDescripProd(resultSet.getString("descrip_prod"));
                producto.setPrecioProd(resultSet.getBigDecimal("precio_prod"));
                producto.setStockDispo(resultSet.getInt("stock_dispo"));
                producto.setEstadoProd(resultSet.getString("estado_prod"));
                producto.setIdCategoria(resultSet.getInt("id_categoria"));
                
                productos.add(producto);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return productos;
    }
    
    // Método para obtener un producto por ID
    public Productos obtenerProductoPorId(int idProducto) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();
            String query = "SELECT * FROM productos WHERE id_producto = ?";
            statement = connection.prepareStatement(query);
            statement.setInt(1, idProducto);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Productos producto = new Productos();
                producto.setIdProducto(resultSet.getInt("id_producto"));
                producto.setNombreProd(resultSet.getString("nombre_prod"));
                producto.setDescripProd(resultSet.getString("descrip_prod"));
                producto.setPrecioProd(resultSet.getBigDecimal("precio_prod"));
                producto.setStockDispo(resultSet.getInt("stock_dispo"));
                producto.setEstadoProd(resultSet.getString("estado_prod"));
                producto.setIdCategoria(resultSet.getInt("id_categoria"));
                return producto;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return null;
    }
    
    // Método para actualizar un producto
    public boolean actualizarProducto(Productos producto) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = getConnection();
            String query = "UPDATE productos SET nombre_prod = ?, descrip_prod = ?, precio_prod = ?, stock_dispo = ?, estado_prod = ?, id_categoria = ? WHERE id_producto = ?";
            statement = connection.prepareStatement(query);
            
            statement.setString(1, producto.getNombreProd());
            statement.setString(2, producto.getDescripProd());
            statement.setBigDecimal(3, producto.getPrecioProd());
            statement.setInt(4, producto.getStockDispo());
            statement.setString(5, producto.getEstadoProd());
            statement.setInt(6, producto.getIdCategoria());
            statement.setInt(7, producto.getIdProducto());

            int filasActualizadas = statement.executeUpdate();
            return filasActualizadas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return false;
    }
    
    // Método para eliminar un producto (eliminación física)
    public boolean eliminarProducto(int idProducto) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = getConnection();
            String query = "DELETE FROM productos WHERE id_producto = ?";
            statement = connection.prepareStatement(query);
            statement.setInt(1, idProducto);

            int filasEliminadas = statement.executeUpdate();
            return filasEliminadas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return false;
    }
    
    // Método para cambiar el estado de un producto (eliminación lógica)
    public boolean cambiarEstadoProducto(int idProducto, String nuevoEstado) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = getConnection();
            String query = "UPDATE productos SET estado_prod = ? WHERE id_producto = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, nuevoEstado);
            statement.setInt(2, idProducto);

            int filasActualizadas = statement.executeUpdate();
            return filasActualizadas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return false;
    }
    
    // Método para obtener productos por categoría
    public List<Productos> obtenerProductosPorCategoria(int idCategoria) {
        List<Productos> productos = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();
            String query = "SELECT * FROM productos WHERE id_categoria = ? ORDER BY nombre_prod";
            statement = connection.prepareStatement(query);
            statement.setInt(1, idCategoria);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Productos producto = new Productos();
                producto.setIdProducto(resultSet.getInt("id_producto"));
                producto.setNombreProd(resultSet.getString("nombre_prod"));
                producto.setDescripProd(resultSet.getString("descrip_prod"));
                producto.setPrecioProd(resultSet.getBigDecimal("precio_prod"));
                producto.setStockDispo(resultSet.getInt("stock_dispo"));
                producto.setEstadoProd(resultSet.getString("estado_prod"));
                producto.setIdCategoria(resultSet.getInt("id_categoria"));
                
                productos.add(producto);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return productos;
    }
    
    // Método para obtener productos con stock bajo
    public List<Productos> obtenerProductosConStockBajo(int stockMinimo) {
        List<Productos> productos = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();
            String query = "SELECT * FROM productos WHERE stock_dispo <= ? AND estado_prod = 'Activo' ORDER BY stock_dispo ASC";
            statement = connection.prepareStatement(query);
            statement.setInt(1, stockMinimo);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Productos producto = new Productos();
                producto.setIdProducto(resultSet.getInt("id_producto"));
                producto.setNombreProd(resultSet.getString("nombre_prod"));
                producto.setDescripProd(resultSet.getString("descrip_prod"));
                producto.setPrecioProd(resultSet.getBigDecimal("precio_prod"));
                producto.setStockDispo(resultSet.getInt("stock_dispo"));
                producto.setEstadoProd(resultSet.getString("estado_prod"));
                producto.setIdCategoria(resultSet.getInt("id_categoria"));
                
                productos.add(producto);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return productos;
    }
    
    // Método para actualizar solo el stock de un producto
    public boolean actualizarStock(int idProducto, int nuevoStock) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = getConnection();
            String query = "UPDATE productos SET stock_dispo = ? WHERE id_producto = ?";
            statement = connection.prepareStatement(query);
            statement.setInt(1, nuevoStock);
            statement.setInt(2, idProducto);

            int filasActualizadas = statement.executeUpdate();
            return filasActualizadas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return false;
    }
    
    // Método para buscar productos por nombre (búsqueda parcial)
    public List<Productos> buscarProductosPorNombre(String nombre) {
        List<Productos> productos = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();
            String query = "SELECT * FROM productos WHERE nombre_prod LIKE ? ORDER BY nombre_prod";
            statement = connection.prepareStatement(query);
            statement.setString(1, "%" + nombre + "%");
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Productos producto = new Productos();
                producto.setIdProducto(resultSet.getInt("id_producto"));
                producto.setNombreProd(resultSet.getString("nombre_prod"));
                producto.setDescripProd(resultSet.getString("descrip_prod"));
                producto.setPrecioProd(resultSet.getBigDecimal("precio_prod"));
                producto.setStockDispo(resultSet.getInt("stock_dispo"));
                producto.setEstadoProd(resultSet.getString("estado_prod"));
                producto.setIdCategoria(resultSet.getInt("id_categoria"));
                
                productos.add(producto);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return productos;
    }
}