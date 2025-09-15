package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import modelo.Usuario;

import java.io.IOException;

import dao.UsuarioDAO;

/**
 * Servlet implementation class Registrarse
 */
@WebServlet("/Registrarse")
public class Registrarse extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Registrarse() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String nom = request.getParameter("txtNombre");
		String ape = request.getParameter("txtApellido");
		String usr = request.getParameter("txtUsuario");
		String psw = request.getParameter("txtContrasenia");
		String tipo = request.getParameter("tipoUsuario");
		
		Usuario u = new Usuario();
		u.setNombre(nom);
		u.setApellido(ape);
		u.setUsuario(usr);
		u.setClave(psw);
		u.setTipo(Integer.parseInt(tipo));

		UsuarioDAO dao = new UsuarioDAO();
		boolean existe = dao.existeUsuario(u);

		// Validaciones
		if (existe) {
			
			String us = request.getParameter("txtUsuario");
			u = dao.list(us);
			request.setAttribute("u", u);
			
			request.setAttribute("msg", "Este usuario ya existe");
			request.getRequestDispatcher("registrarse.jsp").forward(request, response);

		} else {
			dao.registrarUsuario(u);
			request.getRequestDispatcher("login.jsp").forward(request, response);

		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

}