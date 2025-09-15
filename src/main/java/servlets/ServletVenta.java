package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import modelo.Cliente;
import modelo.DetalleVenta;
import modelo.Empresa;
import modelo.Producto;
import modelo.Ventas;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import dao.ClienteDAO;
import dao.DetalleVentasDAO;
import dao.EmpresaDAO;
import dao.ProductoDAO;
import dao.VentasDAO;

//Importaciones para generar PDF
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.DocumentException;

/**
 * Servlet implementation class ServletVenta
 */
@WebServlet("/ServletVenta")
public class ServletVenta extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServletVenta() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("accion");
		String buscar = request.getParameter("buscar"); // Obtener el valor de busqueda (DNI o nombre)
		HttpSession session = request.getSession();
		ClienteDAO dao = new ClienteDAO();
		// Si la accion es listar
		if ("listar".equals(action)) {
			response.sendRedirect("NuevaVenta.jsp"); // Redirigir al servlet sin accion
		} else if (buscar != null && !buscar.trim().isEmpty()) {
			Cliente cliente = dao.buscar(buscar); // Buscar por nombre o DNI
			// Cuando encuentres el cliente
			session.setAttribute("clienteSeleccionado", cliente);
			// Pasar los resultados al JSP
			request.getRequestDispatcher("NuevaVenta.jsp").forward(request, response);
		} else {
			// Por defecto, redirige a la pÃ¡gina de venta
			request.getRequestDispatcher("NuevaVenta.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String accion = request.getParameter("accion");
		HttpSession session = request.getSession();

		// Obtener o crear el carrito
		List<DetalleVenta> carrito = (List<DetalleVenta>) session.getAttribute("carrito");
		if (carrito == null) {
			carrito = new ArrayList<>();
			session.setAttribute("carrito", carrito);
		}

		if ("agregarAlCarrito".equals(accion)) {
			// Agregar producto al carrito
			int id = Integer.parseInt(request.getParameter("id_producto"));
			int codigo = Integer.parseInt(request.getParameter("codigo"));
			String nombre = request.getParameter("nombre");
			double precio = Double.parseDouble(request.getParameter("precio"));
			int stock = Integer.parseInt(request.getParameter("stock"));
			int cantidad = Integer.parseInt(request.getParameter("cantidad"));

			// Validar que la cantidad no exceda el stock
			if (cantidad <= 0 || cantidad > stock) {
				request.setAttribute("mensajeVenta",
						"La cantidad debe ser mayor a 0 y no puede exceder el stock disponible");
				request.getRequestDispatcher("NuevaVenta.jsp").forward(request, response);
				return;
			}

			DetalleVenta itemdv = new DetalleVenta();
			itemdv.setId_producto(id);
			itemdv.setCodigo(codigo);
			itemdv.setNombre(nombre);
			itemdv.setPrecio(precio);
			itemdv.setCantidad(cantidad);

			// Calcular subtotal osea de cada producto el precio por la cantidad que quiera.
			double total = itemdv.subTotalPorItem(cantidad, precio);
			itemdv.setSubtotal(total);

			// Aqui añadimos el objeto itemdv a la lista carrito.
			carrito.add(itemdv);

			session.setAttribute("carrito", carrito);

			// Redirigir con mensaje
			request.setAttribute("mensajeVenta", "Producto agregado al carrito");

		} else if ("eliminarDelCarrito".equals(accion)) {
			// Eliminar un producto del carrito
			int indice = Integer.parseInt(request.getParameter("indice"));
			if (indice >= 0 && indice < carrito.size()) {
				carrito.remove(indice);
				session.setAttribute("carrito", carrito);
				request.setAttribute("mensajeVenta", "Producto eliminado del carrito");
			}

		} else if ("vaciarCarrito".equals(accion)) {
			// Vaciar completamente el carrito
			carrito.clear();
			session.setAttribute("carrito", carrito);
			request.setAttribute("mensajeVenta", "El carrito ha sido vaciado");
			session.removeAttribute("clienteSeleccionado");

		} else if ("completarVenta".equals(accion)) {
			// Aqui procesariamos la venta final de todos los items en el carrito
			// Por ejemplo, guardar en la base de datos, actualizar los productos, etc.

			if (carrito.isEmpty()) {
				request.setAttribute("mensajeVenta", "No hay productos en el carrito para completar la venta");
			} else {
				// Aqui guardamos en la base de datos.
				// Tambien actualizar el stock o cantidad de prodcutos en la tabla productos

				// En este ejemplo, simplemente mostramos un mensaje de éxito y vaciamos el
				// carrito
				double totalVenta = 0;
				ProductoDAO productoDAO = new ProductoDAO();
				Ventas ventas = new Ventas();
				VentasDAO ventasDAO = new VentasDAO();
				DetalleVentasDAO detDAO = new DetalleVentasDAO();

				// procesamos cada producto en el carrito
				// reducimos el stock, y guardamos en la tabla de ventas
				for (DetalleVenta item : carrito) {
					totalVenta += item.getSubtotal();

					// Actualizar stock del producto

					// Obtener el stock actual del producto

					Producto currentProduct = productoDAO.list(item.getId_producto());
					int currentStock = currentProduct.getStock();

					// Calcular el nuevo stock después de la venta
					int nuevoStock = currentStock - item.getCantidad();

					// Actualizar el stock en la base de datos
					productoDAO.editPorIdyStock(item.getId_producto(), nuevoStock);

					// Guardar detalle de venta en la base de datos, Esto me faltaaaa aun,tiene su
					// interfaz y modelo y dao solo falta rellenar datos

				}

				// Guardar la venta principal
				ventasDAO.add(totalVenta);

				// Con esto recorro cada item del carrito
				// luego obtengo el id mÃ¡s reciente de venta
				// y la añado al item, osea el item tendria
				// id_venta, id del proeductio, precio etc etc
				// tendria todos los atributos de la clase DetalleVenta
				for (DetalleVenta item : carrito) {
					int id_venta = detDAO.obtenerIdVenta();
					item.setId_venta(id_venta);
					detDAO.add(item);
				}
		
				// Generar PDF
				response.setContentType("application/pdf");
				response.setHeader("Content-Disposition", "attachment; filename=factura.pdf");
				try {
					Document document = new Document();
					PdfWriter.getInstance(document, response.getOutputStream());
					document.open();

					Cliente cliente = (Cliente) session.getAttribute("clienteSeleccionado");
					EmpresaDAO empresaDAO = new EmpresaDAO();
				    Empresa empresa = empresaDAO.buscarPorIdMaximo();
				    session.setAttribute("empresa", empresa);
					
					document.add(new Paragraph(" "));
					
					document.add(new Paragraph("Razón Social: " + empresa.getRazon()));
					document.add(new Paragraph("RUC: " + empresa.getRuc()));
					document.add(new Paragraph("Dirección: " + empresa.getDireccion()));
					document.add(new Paragraph("Teléfono: " + empresa.getTelefono()));
					document.add(new Paragraph("Gerente: " + empresa.getGerente()));
					
					document.add(new Paragraph("Factura de Venta"));
					document.add(new Paragraph("Cliente: " + cliente.getNombre() + " " + cliente.getApellido()));
					document.add(new Paragraph(" "));

					PdfPTable table = new PdfPTable(4);
					table.addCell("Producto");
					table.addCell("Cantidad");
					table.addCell("Precio");
					table.addCell("Subtotal");

					for (DetalleVenta item : carrito) {
						table.addCell(item.getNombre());
						table.addCell(String.valueOf(item.getCantidad()));
						table.addCell(String.valueOf(item.getPrecio()));
						table.addCell(String.valueOf(item.getSubtotal()));
					}
					document.add(table);

					document.add(new Paragraph(" "));

					// Añadir fecha de la factura
					String fecha = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date());
					document.add(new Paragraph("Fecha de emisión: " + fecha));
					document.add(new Paragraph(" "));

					// Añadir espacio para la firma
					document.add(new Paragraph("Firma: ____________________________"));
					document.add(new Paragraph(" "));

					document.add(new Paragraph("Total a pagar: $" + totalVenta));
													
					document.close();
					carrito.clear();
					session.setAttribute("carrito", carrito);

					return; // Importante para no hacer el forward después
				} catch (DocumentException e) {
					throw new ServletException("Error al generar el PDF", e);
				}

			}

		} else {
			// Procesar la selecciÃ³n de producto del comboboxz de la lista desplegable
			String idStr = request.getParameter("cbomarca");
			if (idStr != null && !idStr.isEmpty()) {
				try {
					int id = Integer.parseInt(idStr);
					ProductoDAO dao = new ProductoDAO();
					Producto productoSeleccionado = dao.list(id);

					// Verificar si el producto ya esta enel carrityoi y dscontar la cantidfad del
					// stock para que se visusalize
					int cantidadEnCarrito = 0;
					for (DetalleVenta item : carrito) {
						if (item.getId_producto() == id) {
							cantidadEnCarrito += item.getCantidad();
						}
					}

					// restamos del stock original del prodcuto seleccionado para mostarloe en la
					// vista
					int stockDisponible = productoSeleccionado.getStock() - cantidadEnCarrito;
					if (stockDisponible < 0)
						stockDisponible = 0; // y con esto se evita que pongas -2 -3 -4 osea los negativos

					productoSeleccionado.setStock(stockDisponible); // con esto se actualiza el stock en la pantalla
																	// osea en esa tabla del jps nuevaventa

					request.setAttribute("productoSeleccionado", productoSeleccionado);

				} catch (NumberFormatException e) {
					e.printStackTrace();
				}
			}
		}

		request.getRequestDispatcher("NuevaVenta.jsp").forward(request, response);
	}
}