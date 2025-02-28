package data;

import java.sql.*;

import java.util.ArrayList;
import java.util.List;
import clases.Cliente;
import clases.Usuario;
import clases.Rol;

public class DataCliente {

	public Cliente getById(int id) {
	    String query = "SELECT c.*, u.idUsuario, u.nombreUsuario, u.contraseña, r.idRol, r.nombre AS nombreRol " +
	                   "FROM Cliente c " +
	                   "JOIN Usuarios u ON c.idUsuario = u.idUsuario " +
	                   "JOIN Roles r ON u.idRol = r.idRol " +
	                   "WHERE c.id = ?";
	    Cliente cliente = null;
	    Connection conn = null;
	    PreparedStatement stmt = null;
	    ResultSet rs = null;
	    
	    try {
	        conn = DbConnector.getInstancia().getConn();
	        System.out.println("Conexión a la base de datos establecida."); // Depuración

	        stmt = conn.prepareStatement(query);
	        stmt.setInt(1, id);
	        rs = stmt.executeQuery();
	        
	        if (rs.next()) {
	            System.out.println("Cliente encontrado con ID: " + id); // Depuración

	            // Crear el objeto Usuario
	            Usuario usuario = new Usuario();
	            usuario.setIdUsuario(rs.getInt("idUsuario"));
	            usuario.setNombreUsuario(rs.getString("nombreUsuario"));
	            usuario.setContraseña(rs.getString("contraseña"));
	            
	            // Crear el objeto Rol
	            Rol rol = new Rol();
	            rol.setIdRol(rs.getInt("idRol"));
	            rol.setNombre(rs.getString("nombreRol"));
	            
	            // Asignar el Rol al Usuario
	            usuario.setRol(rol);

	            // Crear el objeto Cliente
	            cliente = new Cliente();
	            cliente.setIdCliente(rs.getInt("id"));
	            cliente.setUsuario(usuario);
	            cliente.setDni(rs.getString("dni"));
	            cliente.setNombre(rs.getString("nombre"));
	            cliente.setDireccion(rs.getString("direccion"));
	            cliente.setTelefono(rs.getString("telefono"));
	            cliente.setEmail(rs.getString("email"));

	            System.out.println("Cliente creado: " + cliente.getNombre()); // Depuración
	        } else {
	            System.out.println("No se encontró un cliente con ID: " + id); // Depuración
	        }
	    } catch (SQLException e) {
	        System.out.println("Error en la consulta SQL: " + e.getMessage()); // Depuración
	        e.printStackTrace();
	    } finally {
	        try {
	            if (rs != null) rs.close();
	            if (stmt != null) stmt.close();
	            if (conn != null) DbConnector.getInstancia().releaseConn();
	            System.out.println("Recursos liberados."); // Depuración
	        } catch (SQLException e) {
	            System.out.println("Error al cerrar recursos: " + e.getMessage()); // Depuración
	            e.printStackTrace();
	        }
	    }
	    return cliente;
	}

	public List<Cliente> getAll() {
	    List<Cliente> clientes = new ArrayList<>();
	    String query = "SELECT c.*, u.idUsuario, u.nombreUsuario, u.contraseña, r.idRol, r.nombre AS nombreRol " +
	                   "FROM Cliente c " +
	                   "JOIN Usuarios u ON c.idUsuario = u.idUsuario " +  // Corregí "Usuarios" a "Usuario"
	                   "JOIN Roles r ON u.idRol = r.idRol";  // Corregí "Roles" a "Rol"
	    Connection conn = null;
	    PreparedStatement stmt = null;
	    ResultSet rs = null;

	    try {
	        conn = DbConnector.getInstancia().getConn();
	        System.out.println("Conexión a la base de datos establecida."); // Depuración
	        stmt = conn.prepareStatement(query);
	        rs = stmt.executeQuery();

	        while (rs.next()) {
	            // Crear el objeto Usuario
	            Usuario usuario = new Usuario();
	            usuario.setIdUsuario(rs.getInt("idUsuario"));
	            usuario.setNombreUsuario(rs.getString("nombreUsuario"));
	            usuario.setContraseña(rs.getString("contraseña"));
	            
	            // Crear el objeto Rol
	            Rol rol = new Rol();
	            rol.setIdRol(rs.getInt("idRol"));
	            rol.setNombre(rs.getString("nombreRol"));
	            
	            // Asignar el Rol al Usuario
	            usuario.setRol(rol);

	            // Crear el objeto Cliente
	            Cliente cliente = new Cliente();
	            cliente.setIdCliente(rs.getInt("id"));
	            cliente.setUsuario(usuario);
	            cliente.setDni(rs.getString("dni"));
	            cliente.setNombre(rs.getString("nombre"));
	            cliente.setDireccion(rs.getString("direccion"));
	            cliente.setTelefono(rs.getString("telefono"));
	            cliente.setEmail(rs.getString("email"));

	            // Agregar el cliente a la lista
	            clientes.add(cliente);
	            System.out.println("Cliente agregado: " + cliente); // Depuración
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (rs != null) rs.close();
	            if (stmt != null) stmt.close();
	            if (conn != null) DbConnector.getInstancia().releaseConn();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    return clientes;
	}

	public void add(Cliente cliente, Usuario usuario) {
	    Connection conn = null;
	    PreparedStatement stmtUsuario = null;
	    PreparedStatement stmtCliente = null;

	    try {
	        // Obtener la conexión a la base de datos
	        conn = DbConnector.getInstancia().getConn();
	        System.out.println("Conexión a la base de datos establecida."); // Depuración

	        // Desactivar el autocommit para manejar la transacción manualmente
	        conn.setAutoCommit(false);
	        System.out.println("Autocommit desactivado."); // Depuración

	        // 1. Insertar el Usuario
	        String queryUsuario = "INSERT INTO Usuarios (nombreUsuario, contraseña, idRol) VALUES (?, ?, ?)";
	        stmtUsuario = conn.prepareStatement(queryUsuario, Statement.RETURN_GENERATED_KEYS);
	        stmtUsuario.setString(1, usuario.getNombreUsuario());
	        stmtUsuario.setString(2, usuario.getContraseña()); // La contraseña ya debe estar hasheada
	        stmtUsuario.setInt(3, usuario.getRol().getIdRol()); // Asignar el rol (idRol = 3 para Cliente)

	        System.out.println("Ejecutando inserción de Usuario..."); // Depuración
	        int filasUsuario = stmtUsuario.executeUpdate();
	        System.out.println("Filas afectadas en Usuario: " + filasUsuario); // Depuración

	        // Obtener el ID generado para el Usuario
	        ResultSet rs = stmtUsuario.getGeneratedKeys();
	        int idUsuario = 0;
	        if (rs.next()) {
	            idUsuario = rs.getInt(1);
	            System.out.println("Usuario insertado con ID: " + idUsuario); // Depuración
	        } else {
	            System.out.println("Error: No se pudo obtener el ID del Usuario."); // Depuración
	            throw new SQLException("No se pudo obtener el ID del Usuario.");
	        }

	        // 2. Asignar el Usuario al Cliente
	        usuario.setIdUsuario(idUsuario); // Asignar el ID generado al objeto Usuario
	        cliente.setUsuario(usuario); // Asignar el Usuario al Cliente
	        System.out.println("Usuario asignado al Cliente."); // Depuración

	        // 3. Insertar el Cliente
	        String queryCliente = "INSERT INTO Cliente (dni, nombre, direccion, telefono, email, idUsuario) VALUES (?, ?, ?, ?, ?, ?)";
	        stmtCliente = conn.prepareStatement(queryCliente);
	        stmtCliente.setString(1, cliente.getDni());
	        stmtCliente.setString(2, cliente.getNombre());
	        stmtCliente.setString(3, cliente.getDireccion());
	        stmtCliente.setString(4, cliente.getTelefono());
	        stmtCliente.setString(5, cliente.getEmail());
	        stmtCliente.setInt(6, idUsuario); // Asignar el idUsuario generado

	        System.out.println("Ejecutando inserción de Cliente..."); // Depuración
	        int filasCliente = stmtCliente.executeUpdate();
	        System.out.println("Filas afectadas en Cliente: " + filasCliente); // Depuración

	        // Confirmar la transacción
	        conn.commit();
	        System.out.println("Transacción completada: Usuario y Cliente insertados correctamente."); // Depuración

	    } catch (SQLException e) {
	        System.out.println("Error en la transacción: " + e.getMessage()); // Depuración
	        e.printStackTrace();
	        // Revertir la transacción en caso de error
	        if (conn != null) {
	            try {
	                conn.rollback();
	                System.out.println("Transacción revertida debido a un error."); // Depuración
	            } catch (SQLException ex) {
	                System.out.println("Error al revertir la transacción: " + ex.getMessage()); // Depuración
	                ex.printStackTrace();
	            }
	        }
	    
	    } finally {
	        // Cerrar recursos y restaurar el autocommit
	        try {
	            if (stmtUsuario != null) stmtUsuario.close();
	            if (stmtCliente != null) stmtCliente.close();
	            if (conn != null) {
	                conn.setAutoCommit(true); // Restaurar el autocommit
	                DbConnector.getInstancia().releaseConn();
	                System.out.println("Recursos liberados y autocommit restaurado."); // Depuración
	            }
	        } catch (SQLException e) {
	            System.out.println("Error al cerrar recursos: " + e.getMessage()); // Depuración
	            e.printStackTrace();
	        }
	    }
	}

	public void edit(Cliente cliente) {
	    String query = "UPDATE Cliente SET dni = ?, nombre = ?, direccion = ?, telefono = ?, email = ?, idUsuario = ? WHERE id = ?";
	    Connection conn = null;
	    PreparedStatement stmt = null;
	    
	    try {
	        conn = DbConnector.getInstancia().getConn();
	        stmt = conn.prepareStatement(query);
	        stmt.setString(1, cliente.getDni());
	        stmt.setString(2, cliente.getNombre());
	        stmt.setString(3, cliente.getDireccion());
	        stmt.setString(4, cliente.getTelefono());
	        stmt.setString(5, cliente.getEmail());
	        stmt.setInt(6, cliente.getUsuario().getIdUsuario()); 
	        stmt.setInt(7, cliente.getIdCliente());
	        stmt.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (stmt != null) stmt.close();
	            if (conn != null) DbConnector.getInstancia().releaseConn();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	}
	
	public void editII(Cliente cliente) {
	    String query = "UPDATE Cliente SET dni = ?, nombre = ?, direccion = ?, telefono = ?, email = ? WHERE id = ?";
	    Connection conn = null;
	    PreparedStatement stmt = null;
	    
	    try {
	        conn = DbConnector.getInstancia().getConn();
	        stmt = conn.prepareStatement(query);
	        stmt.setString(1, cliente.getDni());
	        stmt.setString(2, cliente.getNombre());
	        stmt.setString(3, cliente.getDireccion());
	        stmt.setString(4, cliente.getTelefono());
	        stmt.setString(5, cliente.getEmail());
	        stmt.setInt(6, cliente.getIdCliente()); 
	        stmt.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (stmt != null) stmt.close();
	            if (conn != null) DbConnector.getInstancia().releaseConn();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	}
	
	
    public void remove(int id) {
        String query = "DELETE FROM Cliente WHERE id = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = DbConnector.getInstancia().getConn();
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) DbConnector.getInstancia().releaseConn();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    public Cliente getByDni(String dni) {
        String query = "SELECT c.*, u.idUsuario, u.nombreUsuario, u.contraseña, r.idRol, r.nombre AS nombreRol " +
                       "FROM Cliente c " +
                       "JOIN Usuarios u ON c.idUsuario = u.idUsuario " +
                       "JOIN Roles r ON u.idRol = r.idRol " +
                       "WHERE c.dni = ?";
        Cliente cliente = null;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DbConnector.getInstancia().getConn();
            stmt = conn.prepareStatement(query);
            stmt.setString(1, dni);
            rs = stmt.executeQuery();

            if (rs.next()) {
                // Crear el objeto Usuario
                Usuario usuario = new Usuario();
                usuario.setIdUsuario(rs.getInt("idUsuario"));
                usuario.setNombreUsuario(rs.getString("nombreUsuario"));
                usuario.setContraseña(rs.getString("contraseña"));
                
                // Crear el objeto Rol
                Rol rol = new Rol();
                rol.setIdRol(rs.getInt("idRol"));
                rol.setNombre(rs.getString("nombreRol"));
                
                // Asignar el Rol al Usuario
                usuario.setRol(rol);

                // Crear el objeto Cliente
                cliente = new Cliente();
                cliente.setIdCliente(rs.getInt("id"));
                cliente.setUsuario(usuario);
                cliente.setDni(rs.getString("dni"));
                cliente.setNombre(rs.getString("nombre"));
                cliente.setDireccion(rs.getString("direccion"));
                cliente.setTelefono(rs.getString("telefono"));
                cliente.setEmail(rs.getString("email"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) DbConnector.getInstancia().releaseConn();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return cliente;
    }

}
