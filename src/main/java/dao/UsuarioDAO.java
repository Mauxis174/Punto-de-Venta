package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import interfaces.InterfaceUsuario;
import modelo.Usuario;
import util.MySQLConexion;

public class UsuarioDAO implements InterfaceUsuario {

	@Override
	public Usuario validarUsuario(String usuario, String clave) {
		Usuario u = null;
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {

			con = MySQLConexion.getConexion();
			String sql = "{CALL usp_validaAcceso(?,?)}";
			pst = con.prepareStatement(sql);
			pst.setString(1, usuario);
			pst.setString(2, clave);
			rs = pst.executeQuery();

			if (rs.next()) {

				u = new Usuario();

				u.setApellido(rs.getString(3));
				u.setCodigo(rs.getInt(1));
				u.setNombre(rs.getString(2));

				u.setUsuario(usuario);
				u.setClave(clave);
				u.setTipo(rs.getInt(7));

			}

		} catch (Exception e) {
			System.out.println("Error al validar: " + e.getMessage());
		} finally {
			MySQLConexion.closeConexion(con);
		}

		return u;
	}

	@Override
	public Usuario list(String usuario) {
		Usuario u = null;
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs;
		String sql3 = "SELECT * FROM tb_usuarios WHERE usuario = '" + usuario + "'";
		
		try {
			con = MySQLConexion.getConexion();
			pst = con.prepareStatement(sql3);
			rs = pst.executeQuery();
			
			if (rs.next()) {

				u = new Usuario();

				u.setApellido(rs.getString(3));
				u.setCodigo(rs.getInt(1));
				u.setNombre(rs.getString(2));

				u.setUsuario(usuario);
				u.setClave(rs.getString(5));
				u.setTipo(rs.getInt(7));

			}

			
		} catch (Exception e) {
			System.out.println("Error al verificar usuario: " + e.getMessage());
			e.printStackTrace();
		} finally {
			MySQLConexion.closeConexion(con);
		}
		
		return u;
	}
	
	@Override
	public boolean existeUsuario(Usuario u) {
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs;
		boolean existe = false;
		String sql1 = "select * from tb_usuarios where usuario like '%" + u.getUsuario() + "%'";

		try {
			con = MySQLConexion.getConexion();
			pst = con.prepareStatement(sql1);
			rs = pst.executeQuery();
			if (rs.next()) {
				existe = true;
			}

		} catch (Exception e) {
			System.out.println("Error al verificar usuario: " + e.getMessage());
			e.printStackTrace();
		} finally {
			MySQLConexion.closeConexion(con);
		}

		return existe;
	}

	@Override
	public boolean registrarUsuario(Usuario u) {
		Connection con = null;
		PreparedStatement pst = null;
		String sql2 = "INSERT INTO tb_usuarios (nombre, apellido, usuario, clave, tipo) VALUES ('" + u.getNombre() + "','"
				+ u.getApellido() + "','" + u.getUsuario() + "','" + u.getClave() + "'," + u.getTipo() + ")";
		try {
			con = MySQLConexion.getConexion();
			pst = con.prepareStatement(sql2);
			pst.executeUpdate();

		} catch (Exception e) {
			System.out.println("Error al registrar usuario: " + e.getMessage());
			e.printStackTrace();
		} finally {
			MySQLConexion.closeConexion(con);
		}
		return false;
	}
}