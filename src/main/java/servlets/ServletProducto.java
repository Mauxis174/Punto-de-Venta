package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import modelo.Producto;

import java.io.IOException;
import java.util.List;

import dao.ProductoDAO;

/**
 * Servlet implementation class ServletProducto
 */
@WebServlet("/ServletProducto")
public class ServletProducto extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public ServletProducto() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String action = request.getParameter("accion");
        String buscar = request.getParameter("buscar");
        ProductoDAO dao = new ProductoDAO();

        // Si la accion es eliminar
        if ("eliminar".equals(action)) {
            try {
                int id = Integer.parseInt(request.getParameter("id"));
                boolean eliminado = dao.eliminar(id);
                
                if (eliminado) {
                    // Agregar mensaje de éxito (opcional)
                    request.getSession().setAttribute("mensaje", "Producto eliminado correctamente");
                } else {
                    // Agregar mensaje de error (opcional)
                    request.getSession().setAttribute("error", "No se pudo eliminar el producto");
                }
                
                response.sendRedirect("ServletProducto");
                return;
                
            } catch (NumberFormatException e) {
                // ID no válido
                request.getSession().setAttribute("error", "ID de producto no válido");
                response.sendRedirect("ServletProducto");
                return;
            } catch (Exception e) {
                e.printStackTrace();
                request.getSession().setAttribute("error", "Error al eliminar el producto");
                response.sendRedirect("ServletProducto");
                return;
            }
        }
        // Si la accion es editar
        else if ("editar".equals(action)) {
            try {
                int id = Integer.parseInt(request.getParameter("id"));
                Producto producto = dao.list(id);
                
                if (producto != null) {
                    request.setAttribute("producto", producto);
                } else {
                    request.getSession().setAttribute("error", "Producto no encontrado");
                }

                List<Producto> productos = dao.listar();
                for (Producto p : productos) {
                    p.setTieneVentas(dao.tieneVentas(p.getId_producto()));
                    p.setNumeroVentas(dao.obtenerNumeroVentas(p.getId_producto()));
                }
                request.setAttribute("productos", productos);


                request.getRequestDispatcher("Productos.jsp").forward(request, response);
                return;
                
            } catch (NumberFormatException e) {
                request.getSession().setAttribute("error", "ID de producto no válido");
                response.sendRedirect("ServletProducto");
                return;
            }
        }
        // Si hay un término de búsqueda
        else if (buscar != null && !buscar.trim().isEmpty()) {
            List<Producto> productos = dao.buscarPorCodigoNombre(buscar.trim());  // ✅ Buscar por nombre o código
            for (Producto p : productos) {
                p.setTieneVentas(dao.tieneVentas(p.getId_producto()));
                p.setNumeroVentas(dao.obtenerNumeroVentas(p.getId_producto()));
            }
            request.setAttribute("productos", productos);

            request.setAttribute("terminoBusqueda", buscar.trim());
            
            request.getRequestDispatcher("Productos.jsp").forward(request, response);
            return;
        }

        // Por defecto, mostrar la lista de todos los Productos
        else {
        	List<Producto> productos = dao.listar();
        	for (Producto p : productos) {
        	    p.setTieneVentas(dao.tieneVentas(p.getId_producto()));
        	    p.setNumeroVentas(dao.obtenerNumeroVentas(p.getId_producto()));
        	}
        	request.setAttribute("productos", productos);

            request.getRequestDispatcher("Productos.jsp").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            // Obtener los datos del formulario
            String idStr = request.getParameter("id");
            int codigo = Integer.parseInt(request.getParameter("codigo"));
            String nombre = request.getParameter("nombre");
            Double precio = Double.parseDouble(request.getParameter("precio"));
            int stock = Integer.parseInt(request.getParameter("stock"));

            // Validaciones básicas
            if (nombre == null || nombre.trim().isEmpty()) {
                request.getSession().setAttribute("error", "El nombre del producto es obligatorio");
                response.sendRedirect("ServletProducto");
                return;
            }
            
            if (precio <= 0) {
                request.getSession().setAttribute("error", "El precio debe ser mayor a 0");
                response.sendRedirect("ServletProducto");
                return;
            }
            
            if (stock < 0) {
                request.getSession().setAttribute("error", "El stock no puede ser negativo");
                response.sendRedirect("ServletProducto");
                return;
            }

            // Crear el objeto producto
            Producto producto = new Producto();
            producto.setCodigo(codigo);
            producto.setNombre(nombre.trim());
            producto.setPrecio(precio);
            producto.setStock(stock);

            ProductoDAO dao = new ProductoDAO();
            boolean operacionExitosa = false;

            // Si hay un ID, es una actualización
            if (idStr != null && !idStr.isEmpty()) {
                int id = Integer.parseInt(idStr);
                producto.setId_producto(id);
                operacionExitosa = dao.edit(producto);
                
                if (operacionExitosa) {
                    request.getSession().setAttribute("mensaje", "Producto actualizado correctamente");
                } else {
                    request.getSession().setAttribute("error", "No se pudo actualizar el producto");
                }
            } else {
                // Si no hay ID, es una inserción
                operacionExitosa = dao.add(producto);
                
                if (operacionExitosa) {
                    request.getSession().setAttribute("mensaje", "Producto agregado correctamente");
                } else {
                    request.getSession().setAttribute("error", "No se pudo agregar el producto");
                }
            }

            // Redirigir al servlet principal
            response.sendRedirect("ServletProducto");
            
        } catch (NumberFormatException e) {
            request.getSession().setAttribute("error", "Error en los datos numéricos ingresados");
            response.sendRedirect("ServletProducto");
        } catch (Exception e) {
            e.printStackTrace();
            request.getSession().setAttribute("error", "Error interno del servidor");
            response.sendRedirect("ServletProducto");
        }
    }
}