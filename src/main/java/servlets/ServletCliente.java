package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import modelo.Cliente;
import java.io.IOException;
import java.util.List;
import dao.ClienteDAO;

/**
 * Servlet implementation class ServletCliente
 */
@WebServlet("/ServletCliente")
public class ServletCliente extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletCliente() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("accion");
        String buscar = request.getParameter("buscar"); // Obtener el valor de busqueda (DNI o nombre)
        ClienteDAO dao = new ClienteDAO();

        // Si la accion es eliminar
        if ("eliminar".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            dao.eliminar(id);
            response.sendRedirect("ServletCliente"); // Redirigir al servlet sin accion
        }
        // Si la accion es editar
        else if ("editar".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            Cliente cliente = dao.list(id); // Obtener el Cliente a editar
            request.setAttribute("cliente", cliente); // Establecer el Cliente en la solicitud

            List<Cliente> clientes = dao.listar();
            request.setAttribute("clientes", clientes);

            request.getRequestDispatcher("Clientes.jsp").forward(request, response);
        }
        // Si hay un término de búsqueda, buscar por DNI o nombre
        else if (buscar != null && !buscar.trim().isEmpty()) {
            List<Cliente> clientes = dao.buscarPorDniONombre(buscar); // Buscar por nombre o DNI
            request.setAttribute("clientes", clientes); // Pasar los resultados al JSP
            request.getRequestDispatcher("Clientes.jsp").forward(request, response);
        }
        // Por defecto, mostrar la lista de todos los Clientes
        else {
            List<Cliente> clientes = dao.listar();
            request.setAttribute("clientes", clientes);
            request.getRequestDispatcher("Clientes.jsp").forward(request, response);
        }
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Obtener los datos del formulario
        String idStr = request.getParameter("id");
        String nombre = request.getParameter("nombre");
        String apellido = request.getParameter("apellido");
        String telefono = request.getParameter("telefono");
        String dni = request.getParameter("dni");
        String contactoDni = request.getParameter("contacto_dni");
        String contactoNombre = request.getParameter("contacto_nombre");

        // Crear el objeto propietario
        Cliente cliente = new Cliente();
        cliente.setNombre(nombre);
        cliente.setApellido(apellido);
        cliente.setTelefono(telefono);
        cliente.setDni(dni);
        cliente.setContactoDni(contactoDni);
        cliente.setContactoNombre(contactoNombre);

        ClienteDAO dao = new ClienteDAO();

        // Si hay un ID, es una actualizacion
        if (idStr != null && !idStr.isEmpty()) {
            int id = Integer.parseInt(idStr);
            cliente.setId(id);
            dao.edit(cliente);
        } else {
            // Si no hay ID, es una insercion
            dao.add(cliente);
        }

        // Redirigir al servlet principal
        response.sendRedirect("ServletCliente");
	}

}
