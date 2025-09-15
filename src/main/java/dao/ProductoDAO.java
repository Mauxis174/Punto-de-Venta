package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import interfaces.InterfazProducto;
import modelo.Producto;
import util.MySQLConexion;

public class ProductoDAO implements InterfazProducto {

	private static final String INSERTAR = "INSERT INTO producto (codigo, nombre, precio, stock) VALUES (?, ?, ?, ?)";
	private static final String OBTENER_POR_ID = "SELECT * FROM producto WHERE id_producto = ?";
	private static final String ACTUALIZAR = "UPDATE producto SET codigo = ?, nombre = ?, precio = ?, stock = ? WHERE id_producto = ?";
	private static final String ACTUALIZARSTOCK = "UPDATE producto SET stock = ? WHERE id_producto = ?";
	
	Connection con;
	PreparedStatement ps;
	ResultSet rs;

	@Override
	public List listar() {
	    ArrayList<Producto> list = new ArrayList();
	    String sql = "SELECT * FROM producto WHERE estado = TRUE";
	    try {
	        con = MySQLConexion.getConexion();
	        ps = con.prepareStatement(sql);
	        rs = ps.executeQuery();

	        while (rs.next()) {
	            Producto pro = new Producto();
	            pro.setId_producto(rs.getInt("id_producto"));
	            pro.setCodigo(rs.getInt("codigo"));
	            pro.setNombre(rs.getString("nombre"));
	            pro.setPrecio(rs.getDouble("precio"));
	            pro.setStock(rs.getInt("stock"));
	            list.add(pro);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        // Cerrar recursos
	        try {
	            if (rs != null) rs.close();
	            if (ps != null) ps.close();
	            if (con != null) con.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    return list;
	}

	@Override
	public Producto list(int cod) {
		Producto producto = null;
		try (Connection con = MySQLConexion.getConexion();
				PreparedStatement ps = con.prepareStatement(OBTENER_POR_ID)) {
			ps.setInt(1, cod);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					producto = new Producto();
					producto.setId_producto(rs.getInt("id_producto"));
					producto.setCodigo(rs.getInt("codigo"));
					producto.setNombre(rs.getString("nombre"));
					producto.setPrecio(rs.getDouble("precio"));
					producto.setStock(rs.getInt("stock"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return producto;
	}

	@Override
	public boolean add(Producto pro) {
		try (Connection con = MySQLConexion.getConexion(); 
		     PreparedStatement ps = con.prepareStatement(INSERTAR)) {
			ps.setInt(1, pro.getCodigo());
			ps.setString(2, pro.getNombre());
			ps.setDouble(3, pro.getPrecio());
			ps.setInt(4, pro.getStock());
			int rowsAffected = ps.executeUpdate();
			return rowsAffected > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean edit(Producto pro) {
		try (Connection con = MySQLConexion.getConexion(); 
		     PreparedStatement ps = con.prepareStatement(ACTUALIZAR)) {
			ps.setInt(1, pro.getCodigo());
			ps.setString(2, pro.getNombre());
			ps.setDouble(3, pro.getPrecio());
			ps.setInt(4, pro.getStock());
			ps.setInt(5, pro.getId_producto());
			int rowsAffected = ps.executeUpdate();
			return rowsAffected > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean editPorIdyStock(int id, int stock) {
		try (Connection con = MySQLConexion.getConexion(); 
		     PreparedStatement ps = con.prepareStatement(ACTUALIZARSTOCK)) {
			ps.setInt(1, stock);
			ps.setInt(2, id);
			int rowsAffected = ps.executeUpdate();
			return rowsAffected > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean eliminar(int cod) {
	    try (Connection con = MySQLConexion.getConexion()) {
	        
	        // Primero verificar si el producto tiene ventas registradas
	    	String verificarVentas =
	    		    "SELECT COUNT(*) AS total FROM detalleventa WHERE id_producto = ?";

	        try (PreparedStatement psVerificar = con.prepareStatement(verificarVentas)) {
	            psVerificar.setInt(1, cod);
	            try (ResultSet rs = psVerificar.executeQuery()) {
	                if (rs.next()) {
	                    int totalVentas = rs.getInt("total");
	                    int rowsAffected = 0;
	                    
	                    if (totalVentas == 0) {
	                        // Si no tiene ventas, eliminar físicamente de la base de datos
	                        String eliminarFisico = "DELETE FROM producto WHERE id_producto = ?";
	                        try (PreparedStatement psEliminar = con.prepareStatement(eliminarFisico)) {
	                            psEliminar.setInt(1, cod);
	                            rowsAffected = psEliminar.executeUpdate();
	                            
	                            if (rowsAffected > 0) {
	                                // Resetear el auto_increment solo si se eliminó físicamente
	                                String resetAutoIncrement = "ALTER TABLE producto AUTO_INCREMENT = 1";
	                                try (PreparedStatement resetPs = con.prepareStatement(resetAutoIncrement)) {
	                                    resetPs.executeUpdate();
	                                }
	                                System.out.println("Producto eliminado físicamente (sin historial de ventas)");
	                            }
	                        }
	                    } else {
	                        // Si tiene ventas, solo marcar como inactivo (eliminación lógica)
	                        String eliminarLogico = "UPDATE producto SET estado = FALSE WHERE id_producto = ?";
	                        try (PreparedStatement psEliminar = con.prepareStatement(eliminarLogico)) {
	                            psEliminar.setInt(1, cod);
	                            rowsAffected = psEliminar.executeUpdate();
	                            
	                            if (rowsAffected > 0) {
	                                System.out.println("Producto marcado como inactivo (tiene historial de ventas: " + totalVentas + " registros)");
	                            }
	                        }
	                    }
	                    
	                    return rowsAffected > 0;
	                }
	            }
	        }
	        return false;
	        
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	
	// Método auxiliar para verificar si un producto tiene ventas
	public boolean tieneVentas(int idProducto) {
	    try (Connection con = MySQLConexion.getConexion()) {
	    	// -- estaba detalle_venta --
	    	String sql =
	    	    "SELECT COUNT(*) AS total FROM detalleventa WHERE id_producto = ?";
	        try (PreparedStatement ps = con.prepareStatement(sql)) {
	            ps.setInt(1, idProducto);
	            try (ResultSet rs = ps.executeQuery()) {
	                if (rs.next()) {
	                    return rs.getInt("total") > 0;
	                }
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return false;
	}

	// Método para obtener el número de ventas de un producto
	public int obtenerNumeroVentas(int idProducto) {
	    try (Connection con = MySQLConexion.getConexion()) {
	    	// -- estaba detalle_venta --
	    	String sql =
	    	    "SELECT COUNT(*) AS total FROM detalleventa WHERE id_producto = ?";
	        try (PreparedStatement ps = con.prepareStatement(sql)) {
	            ps.setInt(1, idProducto);
	            try (ResultSet rs = ps.executeQuery()) {
	                if (rs.next()) {
	                    return rs.getInt("total");
	                }
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return 0;
	}
	
    // Metodo para buscar un producto por codigo o nombre
	public List<Producto> buscarPorCodigoNombre(String buscar) {
	    List<Producto> productos = new ArrayList<>();
	    String sql = "SELECT * FROM producto WHERE (codigo LIKE ? OR nombre LIKE ?) AND estado = TRUE";

	    try (Connection con = MySQLConexion.getConexion();
	         PreparedStatement stmt = con.prepareStatement(sql)) {

	        String searchPattern = "%" + buscar + "%";
	        stmt.setString(1, searchPattern);
	        stmt.setString(2, searchPattern);

	        try (ResultSet rs = stmt.executeQuery()) {
	            while (rs.next()) {
	                Producto producto = new Producto();
	                producto.setId_producto(rs.getInt("id_producto"));
	                producto.setCodigo(rs.getInt("codigo"));
	                producto.setNombre(rs.getString("nombre"));
	                producto.setPrecio(rs.getDouble("precio"));
	                producto.setStock(rs.getInt("stock"));
	                productos.add(producto);
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return productos;
	}
}