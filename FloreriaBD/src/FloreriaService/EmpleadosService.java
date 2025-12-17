package FloreriaService;

import Floreria.Empleados;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmpleadosService extends Conexion<EmpleadosService>{
    
    public  boolean validarCredenciales(Integer empId, String empContra) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();
            String query = "select * from empleados where emp_id = ? and emp_contra = ?";
            statement = connection.prepareStatement(query);
            statement.setInt(1, empId);
            statement.setString(2, empContra);
            resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    return false;
    }
    
    public boolean activarEmpleado(int empId) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = getConnection();
            String query = "UPDATE empleados SET emp_activo = 1 WHERE emp_id = ?";
            statement = connection.prepareStatement(query);
            statement.setInt(1, empId);

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
    
    public boolean desActivarEmpleado(int empId) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = getConnection();
            String query = "UPDATE empleados SET emp_activo = 0 WHERE emp_id = ?";
            statement = connection.prepareStatement(query);
            statement.setInt(1, empId);

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
    
    public Empleados obtenerEmpleadoActivo() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();
            String query = "SELECT * FROM empleados WHERE emp_activo = 1 LIMIT 1";
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Empleados empleado = new Empleados();
                empleado.setEmpId(resultSet.getInt("emp_id"));
                empleado.setEmpNombre(resultSet.getString("emp_nombre"));
                empleado.setEmpApellidos(resultSet.getString("emp_apellidos"));
                empleado.setEmpEdad(resultSet.getInt("emp_edad"));
                empleado.setEmpPuesto(resultSet.getString("emp_puesto"));
                empleado.setEmpTelefono(resultSet.getString("emp_telefono"));
                empleado.setEmpCorreo(resultSet.getString("emp_correo"));
                empleado.setEmpContra(resultSet.getString("emp_contra"));
                empleado.setEmpActivo(resultSet.getInt("emp_activo"));
                return empleado;
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
    
    public boolean hayEmpleadoActivo() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();
            String query = "SELECT 1 FROM empleados WHERE emp_activo = 1 LIMIT 1";
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();

            return resultSet.next();

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

        return false;
    }

}
