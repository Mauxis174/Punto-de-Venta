<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page
	import="java.util.*, modelo.Producto, dao.ProductoDAO, modelo.DetalleVenta, modelo.Cliente"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Nueva Venta</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link href="css/principal.css" rel="stylesheet" type="text/css">
<script type="text/javascript">
	function pasar() {
		// aqui pa comprobar si esta seleccionada una opción valida (no la opción vacÃ­a predeterminada esa de -- Seleccione un producto --)
		var selectBox = document.getElementById("cbomarca");
		if (selectBox.value != "") {
			document.form1.submit();
		}
	}
</script>
</head>
<body class="bg-light">
	<div class="d-flex">

		<nav
			class="sidebar bg-dark d-flex flex-column justify-content-between">

			<!-- Contenedor superior del menú -->
			<div
				class="menu-items d-flex flex-column justify-content-center align-items-center">
				<h4 class="text-white text-center py-3">Menú</h4>
				<ul class="nav flex-column w-100">
					<li class="nav-item"><a
						class="nav-link text-white text-center"
						href="ServletVenta?accion=listar">Nueva Venta</a></li>
					<li class="nav-item"><a
						class="nav-link text-white text-center"
						href="ServletProveedor?accion=listar">Gestión de Proveedores</a></li>
					<li class="nav-item"><a
						class="nav-link text-white text-center"
						href="ServletCliente?accion=listar">Gestión de Clientes</a></li>
					<li class="nav-item"><a
						class="nav-link text-white text-center" href="ServletProducto">Gestión
							de Productos</a></li>
					<li class="nav-item"><a
						class="nav-link text-white text-center"
						href="ServletEmpresa?accion=listar">Configuración</a></li>
				</ul>
			</div>

			<!-- Botón de cerrar sesión abajo -->
			<div class="text-center mb-4">
				<a class="btn btn-danger" href="CerrarSesionServlet">Cerrar
					Sesión</a>
			</div>

		</nav>

		<!-- CONTENIDO PRINCIPAL -->
		<div class="main-content">
			<h1 class="display-5 fw-bold text-center mb-5">Bienvenido al
				Sistema - Realizar una Nueva Venta</h1>

			<form action="ServletVenta" method="get">
				<div class="row mb-3">
					<div class="col-md-6">
						<label for="buscar" class="form-label">Buscar por Nombre o
							DNI</label> <input type="text" class="form-control" id="buscar"
							name="buscar" placeholder="Ingrese nombre o DNI" required>
						<div class="col-md-6">
							<button type="submit" class="btn btn-secondary mt-4">Buscar</button>
						</div>

						<%
						Cliente cliente = (Cliente) session.getAttribute("clienteSeleccionado");
						if (cliente != null) {
						%>
						<label for="cliente" class="form-label mt-2">Cliente
							Seleccionado:</label> <input type="text" class="form-control"
							value="<%=cliente.getNombre() + " " + cliente.getApellido()%>"
							readonly>
						<%
						}
						%>
					</div>
				</div>
			</form>

			<!-- formulario para seleccionar el producto y agregarlo al carrito -->
			<div class="d-flex">
				<form name="form1" action="ServletVenta" method="post"
					onchange="pasar()">
					Seleccione un producto:
					<%
				String idSeleccionado = request.getParameter("cbomarca");
				%>
					<select name="cbomarca" id="cbomarca" onchange="pasar()">
						<option value="">-- Seleccione un producto --</option>
						<%
						ProductoDAO dao = new ProductoDAO();
						List<Producto> list = dao.listar();
						for (Producto pro : list) {
							String selected = (idSeleccionado != null && idSeleccionado.equals(String.valueOf(pro.getId_producto())))
							? "selected"
							: "";
						%>
						<option value="<%=pro.getId_producto()%>" <%=selected%>><%=pro.getNombre()%></option>
						<%
						}
						%>
					</select>

				</form>


				<table class="table table-striped ms-5">
					<thead>
						<tr>
							<th>ID</th>
							<th>Código</th>
							<th>Nombre</th>
							<th>Precio</th>
							<th>Stock</th>
						</tr>
					</thead>

					<tbody>

						<%
						Producto seleccionado = (Producto) request.getAttribute("productoSeleccionado");
						if (seleccionado != null) {
						%>

						<tr>
							<td><%=seleccionado.getId_producto()%></td>
							<td><%=seleccionado.getCodigo()%></td>
							<td><%=seleccionado.getNombre()%></td>
							<td><%=seleccionado.getPrecio()%></td>
							<td><%=seleccionado.getStock()%></td>
						</tr>
						<%
						}
						%>
					</tbody>

				</table>
				<form action="ServletVenta" method="post">
					<%
					if (seleccionado != null) {
					%>
					<input type="hidden" name="id_producto"
						value="<%=seleccionado.getId_producto()%>"> <input
						type="hidden" name="codigo" value="<%=seleccionado.getCodigo()%>">
					<input type="hidden" name="nombre"
						value="<%=seleccionado.getNombre()%>"> <input
						type="hidden" name="precio" value="<%=seleccionado.getPrecio()%>">
					<input type="hidden" name="stock"
						value="<%=seleccionado.getStock()%>">
					<%
					}
					%>

					<div class="col-md-8 ms-5">
						<label for="cantidad" class="form-label">Cantidad</label> <input
							type="text" class="form-control px-3" id="cantidad"
							name="cantidad" required>
					</div>


					<div class="col-md-1 ms-5 mt-2">
						<button type="submit" name="accion" value="agregarAlCarrito"
							class="btn btn-primary">Agregar al Carrito</button>
					</div>
				</form>
			</div>


			<!-- aqui esta creando la tabla del carrito donde se va aÃ±adiendo los productos a vender-->
			<%
			List<DetalleVenta> carrito = (List<DetalleVenta>) session.getAttribute("carrito");
			if (carrito != null && !carrito.isEmpty()) {
			%>
			<h3 class="mt-5">Carrito de Compras</h3>
			<table class="table table-bordered mt-3">
				<thead>
					<tr>
						<th>Código del producto</th>
						<th>Nombre</th>
						<th>Precio</th>
						<th>Cantidad</th>
						<th>Total</th>
						<th>Acciones</th>
					</tr>
				</thead>
				<tbody>
					<%
					double totalVenta = 0;
					for (int i = 0; i < carrito.size(); i++) {
						DetalleVenta itemdv = carrito.get(i);
						totalVenta += itemdv.getSubtotal();
					%>
					<tr>
						<td><%=itemdv.getCodigo()%></td>
						<td><%=itemdv.getNombre()%></td>
						<td><%=itemdv.getPrecio()%></td>
						<td><%=itemdv.getCantidad()%></td>
						<td><%=itemdv.getSubtotal()%></td>
						<td>
							<form action="ServletVenta" method="post">
								<input type="hidden" name="indice" value="<%=i%>">
								<button type="submit" name="accion" value="eliminarDelCarrito"
									class="btn btn-danger btn-sm">Eliminar</button>
							</form>
						</td>
					</tr>
					<%
					}
					%>
					<tr>
						<td colspan="4" class="text-end fw-bold">Total de la Venta:</td>
						<td class="fw-bold"><%=totalVenta%></td>
						<td></td>
					</tr>
				</tbody>
			</table>


			<div class="d-flex justify-content-end mt-3">
				<!-- botones para vaciar el carrito o completar la venta -->
				<form action="ServletVenta" method="post">
					<button type="submit" name="accion" value="vaciarCarrito"
						class="btn btn-warning me-2">Vaciar Carrito</button>
					<button type="submit" name="accion" value="completarVenta"
						class="btn btn-success">Completar Venta</button>
				</form>
			</div>
			<%
			}
			%>

			<!-- mensajes de confirmacion o errores -->
			<%
			String mensajeVenta = (String) request.getAttribute("mensajeVenta");
			if (mensajeVenta != null) {
			%>
			<div class="alert alert-success mt-3"><%=mensajeVenta%></div>
			<%
			}
			%>

		</div>

	</div>
</body>
</html>