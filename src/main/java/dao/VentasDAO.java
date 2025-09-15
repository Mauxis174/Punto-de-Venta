package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import interfaces.InterfazVentas;
import modelo.Ventas;
import util.MySQLConexion;

public class VentasDAO implements InterfazVentas{
	private static final String INSERTAR = "INSERT INTO venta (total) VALUES (?)";
    private static final String BUSCAR = "SELECT * FROM venta WHERE total LIKE ?";

    
	@Override
	public boolean add(double totalVenta) {
		try (Connection con = MySQLConexion.getConexion(); PreparedStatement ps = con.prepareStatement(INSERTAR)) {
			ps.setDouble(1, totalVenta);
			ps.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public List<Ventas> buscarPorTotalFinal(String buscar) {
        List<Ventas> ventas = new ArrayList<>();
        String sql = BUSCAR; // Usamos la consulta SQL definida

        try (Connection con = MySQLConexion.getConexion();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, buscar); // Buscar por DNI

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                	Ventas venta = new Ventas();
                	venta.setVentaTotal(rs.getDouble("total"));
                	ventas.add(venta);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ventas;
	}
	
}
