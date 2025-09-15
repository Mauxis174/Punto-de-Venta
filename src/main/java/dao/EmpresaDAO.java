package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import interfaces.InterfazEmpresa;
import modelo.Empresa;
import util.MySQLConexion;

public class EmpresaDAO implements InterfazEmpresa{
	
	private static final String INSERTAR = "INSERT INTO empresa (ruc, gerente, DIRECCION, TELEFONO, RAZON) VALUES (?, ?, ?, ?, ?)";
	private static final String IDMAXIMO = "SELECT MAX(id_emp) FROM empresa;";
    private static final String BUSCAR = "SELECT * FROM empresa WHERE id_emp LIKE ?";

	@Override
	public boolean add(Empresa empresa) {
        try (Connection con = MySQLConexion.getConexion();
                PreparedStatement ps = con.prepareStatement(INSERTAR)) {
	            ps.setString(1, empresa.getRuc());
	            ps.setString(2, empresa.getGerente());
	            ps.setString(3, empresa.getDireccion());
	            ps.setString(4, empresa.getTelefono());
	            ps.setString(5, empresa.getRazon());
               ps.executeUpdate();
               return true;
           } catch (SQLException e) {
               e.printStackTrace();
               return false;
           }
	}

	@Override
	public Empresa buscarPorIdMaximo() {
		int idMaximo = obtenerIdMáximoEmpresa();
		Empresa empresa = null;
		try (
				Connection con = MySQLConexion.getConexion();
				PreparedStatement ps = con.prepareStatement(BUSCAR)){
			ps.setInt(1, idMaximo);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
            	empresa= new Empresa();
            	empresa.setRuc(rs.getString("ruc"));
            	empresa.setGerente(rs.getString("gerente"));
            	empresa.setDireccion(rs.getString("DIRECCION"));
            	empresa.setTelefono(rs.getString("TELEFONO"));
            	empresa.setRazon(rs.getString("RAZON"));
            }			
			} catch (SQLException e) {
				e.printStackTrace();
			}
		return empresa;
	}

	@Override
	public int obtenerIdMáximoEmpresa() {
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
