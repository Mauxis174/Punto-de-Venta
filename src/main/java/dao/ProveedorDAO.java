package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import interfaces.InterfazProveedor;
import modelo.Proveedor;
import util.MySQLConexion;

public class ProveedorDAO implements InterfazProveedor {
	
	private static final String INSERTAR = "INSERT INTO proveedor (ruc, NOMBRE, TELEFONO, DIRECCION, RAZON) VALUES (?, ?, ?, ?, ?)";
	private static final String OBTENER_TODOS = "SELECT * FROM proveedor";
	private static final String ELIMINAR = "DELETE FROM proveedor WHERE id_prov = ?";
	private static final String OBTENER_POR_ID = "SELECT * FROM proveedor WHERE id_prov = ?";
	private static final String ACTUALIZAR = "UPDATE proveedor SET ruc = ?, NOMBRE = ?, TELEFONO = ?, DIRECCION = ?, RAZON = ? WHERE id_prov = ?";
	private static final String BUSCAR = "SELECT * FROM proveedor WHERE ruc LIKE ? OR NOMBRE LIKE ?";

	Connection con;
	PreparedStatement ps;
	ResultSet rs;
	
	@Override
	public List listar() {
		ArrayList<Proveedor> list = new ArrayList();
		String sql = OBTENER_TODOS;
		try {
			con = MySQLConexion.getConexion();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();

			while (rs.next()) {
				Proveedor prov = new Proveedor();
				prov.setId_prov(rs.getInt("id_prov"));
				prov.setRuc(rs.getString("ruc"));
				prov.setNombre(rs.getString("NOMBRE"));
				prov.setTelefono(rs.getString("TELEFONO"));
				prov.setDireccion(rs.getString("DIRECCION"));
				prov.setRazon(rs.getString("RAZON"));
				list.add(prov);
			}
		} catch (Exception e) {

		}
		return list;
	}

	@Override
	public Proveedor list(int cod) {
		Proveedor proveedor = null;
		try (Connection con = MySQLConexion.getConexion();
				PreparedStatement ps = con.prepareStatement(OBTENER_POR_ID)) {
			ps.setInt(1, cod); // Establecer el id como parámetro
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					proveedor = new Proveedor();
					proveedor.setId_prov(rs.getInt("id_prov"));
					proveedor.setRuc(rs.getString("ruc"));
					proveedor.setNombre(rs.getString("NOMBRE"));
					proveedor.setTelefono(rs.getString("TELEFONO"));
					proveedor.setDireccion(rs.getString("DIRECCION"));
					proveedor.setRazon(rs.getString("RAZON"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return proveedor;
	}

	@Override
	public boolean add(Proveedor prov) {
		try (Connection con = MySQLConexion.getConexion(); PreparedStatement ps = con.prepareStatement(INSERTAR)) {
			ps.setString(1, prov.getRuc());
			ps.setString(2, prov.getNombre());
			ps.setString(3, prov.getTelefono());
			ps.setString(4, prov.getDireccion());
			ps.setString(5, prov.getRazon());
			ps.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean edit(Proveedor prov) {
		try (Connection con = MySQLConexion.getConexion(); PreparedStatement ps = con.prepareStatement(ACTUALIZAR)) {
			ps.setString(1, prov.getRuc());
			ps.setString(2, prov.getNombre());
			ps.setString(3, prov.getTelefono());
			ps.setString(4, prov.getDireccion());
			ps.setString(5, prov.getRazon());
			ps.setInt(6, prov.getId_prov());
			ps.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean eliminar(int cod) {
		try (Connection con = MySQLConexion.getConexion(); PreparedStatement ps = con.prepareStatement(ELIMINAR)) {
			ps.setInt(1, cod);
			ps.executeUpdate();
			String resetAutoIncrement = "ALTER TABLE proveedor AUTO_INCREMENT = 1";
			try (PreparedStatement resetPs = con.prepareStatement(resetAutoIncrement)) {
				resetPs.executeUpdate();
			}
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
    public List<Proveedor> buscarPorRucNombre(String buscar){
    	List<Proveedor> proveedores = new ArrayList<>();
        String sql = BUSCAR; // Usamos la consulta SQL definida

        try (Connection con = MySQLConexion.getConexion();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            String searchPattern = "%" + buscar + "%"; // Búsqueda parcial
            stmt.setString(1, searchPattern); // Buscar por DNI
            stmt.setString(2, searchPattern); // Buscar por nombre

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                	Proveedor proveedor = new Proveedor();
                	proveedor.setId_prov(rs.getInt("id_prov"));
                	proveedor.setRuc(rs.getString("ruc"));
                	proveedor.setNombre(rs.getString("NOMBRE"));
                	proveedor.setTelefono(rs.getString("TELEFONO"));
                	proveedor.setDireccion(rs.getString("DIRECCION"));
                	proveedor.setRazon(rs.getString("RAZON"));
                	proveedores.add(proveedor);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return proveedores;
    }


}
