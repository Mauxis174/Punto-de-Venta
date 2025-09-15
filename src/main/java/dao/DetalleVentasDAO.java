package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import interfaces.InterfazDetalleVentas;
import modelo.DetalleVenta;
import util.MySQLConexion;

public class DetalleVentasDAO implements InterfazDetalleVentas {

	private static final String INSERTAR = "INSERT INTO detalleventa (cantidad, precio, subtotal, id_venta, id_producto) VALUES (?, ?, ?, ?, ?)";
	private static final String IDMAXIMO = "SELECT MAX(id_venta) FROM venta;";

	
	@Override
	public boolean add(DetalleVenta deve) {
		try (
			Connection con = MySQLConexion.getConexion();
			PreparedStatement ps = con.prepareStatement(INSERTAR)) 
		{
			ps.setInt(1, deve.getCantidad());
			ps.setDouble(2, deve.getPrecio());
			ps.setDouble(3, deve.getSubtotal());
			ps.setInt(4, deve.getId_venta());
			ps.setInt(5, deve.getId_producto());
			ps.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}


	@Override
	//Con esto obtengo el id de venta mayor
	//osea el más reciente el último,
	//la venta más reciente más realizada.
	public int obtenerIdVenta() {
		int id = 0;
		try (
				Connection con = MySQLConexion.getConexion();
				PreparedStatement ps = con.prepareStatement(IDMAXIMO);
				ResultSet rs = ps.executeQuery())
			{
				if(rs.next()) {
					id = rs.getInt(1);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		return id;
	}
	
	
	
}
