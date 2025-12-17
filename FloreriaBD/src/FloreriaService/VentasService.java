package FloreriaService;

import Floreria.Ventas;
import Floreria.DetalleVenta;
import Floreria.Empleados;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class VentasService extends Conexion<VentasService> {
    
    // Método para registrar una venta completa (venta + detalles)
    public boolean registrarVenta(Ventas venta, List<DetalleVenta> detalles) {
        Connection connection = null;
        PreparedStatement stmtVenta = null;
        PreparedStatement stmtDetalle = null;
        PreparedStatement stmtStock = null;
        
        try {
            connection = getConnection();
            connection.setAutoCommit(false); // Iniciar transacción
            
            // 1. Insertar la venta
            String queryVenta = "INSERT INTO ventas (fecha_ven, total_ven, metodo_pago, emp_id) VALUES (?, ?, ?, ?)";
            stmtVenta = connection.prepareStatement(queryVenta, Statement.RETURN_GENERATED_KEYS);
            Empleados empleado;
            EmpleadosService emp = new EmpleadosService();
            empleado = emp.obtenerEmpleadoActivo();
            stmtVenta.setDate(1, Date.valueOf(venta.getFechaVen()));
            stmtVenta.setBigDecimal(2, venta.getTotalVen());
            stmtVenta.setString(3, venta.getMetodoPago());
            stmtVenta.setInt(4, empleado.getEmpId());
            
            int filasVenta = stmtVenta.executeUpdate();
            if (filasVenta == 0) {
                connection.rollback();
                return false;
            }
            
            // Obtener el ID de la venta generado
            ResultSet generatedKeys = stmtVenta.getGeneratedKeys();
            int idVenta = 0;
            if (generatedKeys.next()) {
                idVenta = generatedKeys.getInt(1);
            } else {
                connection.rollback();
                return false;
            }
            
            // 2. Insertar los detalles de la venta
            String queryDetalle = "INSERT INTO detalle_venta (cantidad_ven, prec_u_ven, subtotal_ven, id_venta, id_producto) VALUES (?, ?, ?, ?, ?)";
            stmtDetalle = connection.prepareStatement(queryDetalle);
            
            // 3. Preparar consulta para actualizar stock
            String queryStock = "UPDATE productos SET stock_dispo = stock_dispo - ? WHERE id_producto = ?";
            stmtStock = connection.prepareStatement(queryStock);
            
            for (DetalleVenta detalle : detalles) {
                // Insertar detalle
                stmtDetalle.setInt(1, detalle.getCantidadVen());
                stmtDetalle.setBigDecimal(2, detalle.getPrecUVen());
                stmtDetalle.setBigDecimal(3, detalle.getSubtotalVen());
                stmtDetalle.setInt(4, idVenta);
                stmtDetalle.setInt(5, detalle.getIdProducto());
                stmtDetalle.addBatch();
                
                // Actualizar stock
                stmtStock.setInt(1, detalle.getCantidadVen());
                stmtStock.setInt(2, detalle.getIdProducto());
                stmtStock.addBatch();
            }
            
            // Ejecutar los batches
            stmtDetalle.executeBatch();
            stmtStock.executeBatch();
            
            connection.commit(); // Confirmar transacción
            return true;
            
        } catch (SQLException e) {
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (stmtStock != null) stmtStock.close();
                if (stmtDetalle != null) stmtDetalle.close();
                if (stmtVenta != null) stmtVenta.close();
                if (connection != null) {
                    connection.setAutoCommit(true);
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    // Método para obtener todas las ventas
    public List<Ventas> obtenerTodasLasVentas() {
        List<Ventas> ventas = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        
        try {
            connection = getConnection();
            String query = "SELECT * FROM ventas ORDER BY fecha_ven DESC";
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();
            
            while (resultSet.next()) {
                Ventas venta = new Ventas();
                venta.setIdVenta(resultSet.getInt("id_venta"));
                venta.setFechaVen(resultSet.getDate("fecha_ven").toLocalDate());
                venta.setTotalVen(resultSet.getBigDecimal("total_ven"));
                venta.setMetodoPago(resultSet.getString("metodo_pago"));
                venta.setEmpId(resultSet.getObject("emp_id", Integer.class));
                
                ventas.add(venta);
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
        
        return ventas;
    }
    
    // Método para obtener detalles de una venta específica
    public List<DetalleVenta> obtenerDetallesVenta(int idVenta) {
        List<DetalleVenta> detalles = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        
        try {
            connection = getConnection();
            String query = "SELECT dv.*, p.nombre_prod FROM detalle_venta dv " +
                          "JOIN productos p ON dv.id_producto = p.id_producto " +
                          "WHERE dv.id_venta = ?";
            statement = connection.prepareStatement(query);
            statement.setInt(1, idVenta);
            resultSet = statement.executeQuery();
            
            while (resultSet.next()) {
                DetalleVenta detalle = new DetalleVenta();
                detalle.setIdDtllVen(resultSet.getInt("id_dtll_ven"));
                detalle.setCantidadVen(resultSet.getInt("cantidad_ven"));
                detalle.setPrecUVen(resultSet.getBigDecimal("prec_u_ven"));
                detalle.setSubtotalVen(resultSet.getBigDecimal("subtotal_ven"));
                detalle.setIdVenta(resultSet.getInt("id_venta"));
                detalle.setIdProducto(resultSet.getInt("id_producto"));
                detalle.setNombreProducto(resultSet.getString("nombre_prod"));
                
                detalles.add(detalle);
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
        
        return detalles;
    }
}