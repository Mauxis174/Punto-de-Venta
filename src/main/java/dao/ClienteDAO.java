package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import interfaces.InterfazCliente;
import modelo.Cliente;
import util.MySQLConexion;

public class ClienteDAO implements InterfazCliente{
	
	private static final String INSERTAR = "INSERT INTO cliente (nombre, apellido, telefono, dni, contacto_dni, contacto_nombre) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String OBTENER_TODOS = "SELECT * FROM cliente";
    private static final String ELIMINAR = "DELETE FROM cliente WHERE id_cliente = ?";
    private static final String OBTENER_POR_ID = "SELECT * FROM cliente WHERE id_cliente = ?";
    private static final String ACTUALIZAR = "UPDATE cliente SET nombre = ?, apellido = ?, telefono = ?, dni = ?, contacto_dni = ?, contacto_nombre = ? WHERE id_cliente = ?";
    private static final String BUSCAR = "SELECT * FROM cliente WHERE dni LIKE ? OR nombre LIKE ?";
    private static final String BUSCAR1 = "SELECT * FROM cliente WHERE dni LIKE ?";
    
	@Override
	public List<Cliente> listar() {
		List<Cliente> clientes = new ArrayList<>();
        try (Connection con = MySQLConexion.getConexion();
             PreparedStatement ps = con.prepareStatement(OBTENER_TODOS);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
            	Cliente cliente = new Cliente();
                cliente.setId(rs.getInt("id_cliente"));
                cliente.setNombre(rs.getString("nombre"));
                cliente.setApellido(rs.getString("apellido"));
                cliente.setTelefono(rs.getString("telefono"));
                cliente.setDni(rs.getString("dni"));
                cliente.setContactoDni(rs.getString("contacto_dni"));
                cliente.setContactoNombre(rs.getString("contacto_nombre"));
                clientes.add(cliente);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clientes;
	}

	@Override
	public List<Cliente> buscarPorDniONombre(String buscar) {
		 List<Cliente> clientes = new ArrayList<>();
	        String sql = BUSCAR; // Usamos la consulta SQL definida

	        try (Connection con = MySQLConexion.getConexion();
	             PreparedStatement stmt = con.prepareStatement(sql)) {

	            String searchPattern = "%" + buscar + "%"; // Búsqueda parcial
	            stmt.setString(1, searchPattern); // Buscar por DNI
	            stmt.setString(2, searchPattern); // Buscar por nombre

	            try (ResultSet rs = stmt.executeQuery()) {
	                while (rs.next()) {
	                	Cliente cliente= new Cliente();
	                	cliente.setId(rs.getInt("id_cliente"));
	                	cliente.setNombre(rs.getString("nombre"));
	                    cliente.setApellido(rs.getString("apellido"));
	                    cliente.setTelefono(rs.getString("telefono"));
	                    cliente.setDni(rs.getString("dni"));
	                    cliente.setContactoDni(rs.getString("contacto_dni"));
	                    cliente.setContactoNombre(rs.getString("contacto_nombre"));
	                    clientes.add(cliente);
	                }
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }

	        return clientes;
	}
	
	

	@Override
	public Cliente list(int id) {
		Cliente cliente = null;
        try (Connection con = MySQLConexion.getConexion();
             PreparedStatement ps = con.prepareStatement(OBTENER_POR_ID)) {
            ps.setInt(1, id); // Establecer el id como parámetro
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                	cliente = new Cliente();
                	cliente.setId(rs.getInt("id_cliente"));
                	cliente.setNombre(rs.getString("nombre"));
                	cliente.setApellido(rs.getString("apellido"));
                	cliente.setTelefono(rs.getString("telefono"));
                	cliente.setDni(rs.getString("dni"));
                	cliente.setContactoDni(rs.getString("contacto_dni"));
                	cliente.setContactoNombre(rs.getString("contacto_nombre"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cliente;
	}

	@Override
	public boolean add(Cliente cliente) {
        try (Connection con = MySQLConexion.getConexion();
                PreparedStatement ps = con.prepareStatement(INSERTAR)) {
               ps.setString(1, cliente.getNombre());
               ps.setString(2, cliente.getApellido());
               ps.setString(3, cliente.getTelefono());
               ps.setString(4, cliente.getDni());
               ps.setString(5, cliente.getContactoDni());
               ps.setString(6, cliente.getContactoNombre());
               ps.executeUpdate();
               return true;
           } catch (SQLException e) {
               e.printStackTrace();
               return false;
           }
	}

	@Override
	public boolean edit(Cliente cliente) {
        try (Connection con = MySQLConexion.getConexion();
                PreparedStatement ps = con.prepareStatement(ACTUALIZAR)) {
               ps.setString(1, cliente.getNombre());
               ps.setString(2, cliente.getApellido());
               ps.setString(3, cliente.getTelefono());
               ps.setString(4, cliente.getDni());
               ps.setString(5, cliente.getContactoDni());
               ps.setString(6, cliente.getContactoNombre());
               ps.setInt(7, cliente.getId());
               ps.executeUpdate();
               return true;
           } catch (SQLException e) {
               e.printStackTrace();
               return false;
           }
	}

	@Override
	public boolean eliminar(int id) {
        try (Connection con = MySQLConexion.getConexion();
                PreparedStatement ps = con.prepareStatement(ELIMINAR)) {
               ps.setInt(1, id);
               ps.executeUpdate();
               String resetAutoIncrement = "ALTER TABLE cliente AUTO_INCREMENT = 1";
               try (PreparedStatement resetPs = con.prepareStatement(resetAutoIncrement)) {
                   resetPs.executeUpdate();
               }
               return true;
           } catch (SQLException e) {
               e.printStackTrace();
               return false;
           }
	}

	@Override
	public Cliente buscar(String buscar) {
			String sql = BUSCAR1; // Usamos la consulta SQL definida
			Cliente cliente = null;
			Connection con = null;
			PreparedStatement pst = null;
			ResultSet rs;
			
			try{
				con = MySQLConexion.getConexion();
				pst = con.prepareStatement(sql);
				pst.setString(1, buscar);
				rs = pst.executeQuery();
		           	                
		                if (rs.next()) {
		                	cliente= new Cliente();
		                	cliente.setId(rs.getInt("id_cliente"));
		                	cliente.setNombre(rs.getString("nombre"));
		                    cliente.setApellido(rs.getString("apellido"));
		                    cliente.setTelefono(rs.getString("telefono"));
		                    cliente.setDni(rs.getString("dni"));
		                    cliente.setContactoDni(rs.getString("contacto_dni"));
		                    cliente.setContactoNombre(rs.getString("contacto_nombre"));
		                }
		            } catch (Exception e) {
				System.out.println("Error al verificar usuario: " + e.getMessage());
				e.printStackTrace();
			} finally {
				MySQLConexion.closeConexion(con);
			}

			return cliente;
	}

}
