package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import modelo.Empresa;

import java.io.IOException;

import dao.EmpresaDAO;

/**
 * Servlet implementation class ServletEmpresa
 */
@WebServlet("/ServletEmpresa")
public class ServletEmpresa extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletEmpresa() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		EmpresaDAO dao = new EmpresaDAO();

        Empresa empresa = dao.buscarPorIdMaximo(); // Obtener la empresa con el ID máximo
        request.setAttribute("empresa", empresa);
        request.getRequestDispatcher("Configuracion.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Obtener los datos del formulario
        String ruc = request.getParameter("ruc");
        String gerente = request.getParameter("gerente");
        String direccion = request.getParameter("direccion");
        String telefono = request.getParameter("telefono");
        String razon = request.getParameter("razon");
        
        Empresa empresa = new Empresa();
        
        EmpresaDAO dao = new EmpresaDAO();
        
        empresa.setRuc(ruc);
        empresa.setGerente(gerente);
        empresa.setDireccion(direccion);
        empresa.setTelefono(telefono);
        empresa.setRazon(razon);


        dao.add(empresa);
        
        response.sendRedirect("ServletEmpresa");
        
        
	}

}
