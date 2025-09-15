package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import modelo.Proveedor;
import java.io.IOException;
import java.util.List;
import dao.ProveedorDAO;


/**
 * Servlet implementation class ServletProveedor
 */
@WebServlet("/ServletProveedor")
public class ServletProveedor extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletProveedor() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 	String action = request.getParameter("accion");
	        String buscar = request.getParameter("buscar"); // Obtener el valor de busqueda (ruc o nombre)
	        ProveedorDAO dao = new ProveedorDAO();

	        // Si la accion es eliminar
	        if ("eliminar".equals(action)) {
	            int id = Integer.parseInt(request.getParameter("id"));
	            dao.eliminar(id);
	            response.sendRedirect("ServletProveedor"); // Redirigir al servlet sin accion
	        }
	        // Si la accion es editar
	        else if ("editar".equals(action)) {
	            int id = Integer.parseInt(request.getParameter("id"));
	            Proveedor proveedor = dao.list(id); // Obtener el proveedor a editar
	            request.setAttribute("proveedor", proveedor); // Establecer el proveedor en la solicitud
	            List<Proveedor> proveedores = dao.listar();
	            request.setAttribute("proveedores", proveedores);

	            request.getRequestDispatcher("proveedores.jsp").forward(request, response);
	        }
	        // Si hay un término de búsqueda, buscar por ruc o nombre
	        else if (buscar != null && !buscar.trim().isEmpty()) {
	            List<Proveedor> proveedores = dao.buscarPorRucNombre(buscar); // Buscar por nombre o ruc
	            request.setAttribute("proveedores", proveedores); // Pasar los resultados al JSP
	            request.getRequestDispatcher("proveedores.jsp").forward(request, response);
	        }
	        // Por defecto, mostrar la lista de todos los proveedores
	        else {
	            List<Proveedor> proveedores = dao.listar();
	            request.setAttribute("proveedores", proveedores);
	            request.getRequestDispatcher("proveedores.jsp").forward(request, response);
	        }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Obtener los datos del formulario
        String idStr = request.getParameter("id");
        String ruc = request.getParameter("ruc");
        String nombre = request.getParameter("nombre");
        String telefono = request.getParameter("telefono");
        String direccion = request.getParameter("direccion");
        String razon = request.getParameter("razon");

        // Crear el objeto proveedor
        Proveedor proveedor = new Proveedor();
        proveedor.setRuc(ruc);
        proveedor.setNombre(nombre);
        proveedor.setTelefono(telefono);
        proveedor.setDireccion(direccion);
        proveedor.setRazon(razon);

        ProveedorDAO dao = new ProveedorDAO();

        // Si hay un ID, es una actualizacion
        if (idStr != null && !idStr.isEmpty()) {
            int id = Integer.parseInt(idStr);
            proveedor.setId_prov(id);
            dao.edit(proveedor);
        } else {
            // Si no hay ID, es una insercion
            dao.add(proveedor);
        }

        // Redirigir al servlet principal
        response.sendRedirect("ServletProveedor");
	}

}
