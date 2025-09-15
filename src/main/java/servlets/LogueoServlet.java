package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import dao.UsuarioDAO;

import modelo.Usuario;

/**
 * Servlet implementation class LogueoServlet
 */
@WebServlet("/LogueoServlet")
public class LogueoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LogueoServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String correo, clave;

		correo = request.getParameter("txtCorreo");
		clave = request.getParameter("txtClave");

		UsuarioDAO gu = new UsuarioDAO();
		Usuario u = gu.validarUsuario(correo, clave);

		if (u != null) {

			response.sendRedirect("principal.jsp");

		} else {
			request.setAttribute("msg", "Usuario o clave incorrecto");
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
