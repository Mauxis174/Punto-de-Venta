<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" session="true"%>
<%@ page import="java.util.List"%>
<%@ page import="modelo.Cliente"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link href="css/principal.css" rel="stylesheet">
</head>
<body class="bg-light">

	<div class="d-flex">
		<!-- Menu Lateral -->
		<nav
			class="sidebar bg-dark d-flex flex-column justify-content-between">
			<div class="menu-items d-flex flex-column justify-content-center align-items-center">
				<h4 class="text-white text-center py-3">Menú</h4>
				<ul class="nav flex-column w-100">
					<li class="nav-item"><a
						class="nav-link text-white text-center"
						href="ServletVenta?accion=listar">Nueva Venta</a></li>
	                <li class="nav-item">
	                    <a class="nav-link text-white text-center" href="ServletProveedor?accion=listar">Gestión de Proveedores</a>
	                </li>
					<li class="nav-item"><a
						class="nav-link text-white text-center"
						href="ServletCliente?accion=listar">Gestión de Clientes</a>
					</li>
					<li class="nav-item"><a
						class="nav-link text-white text-center" href="ServletProducto">Gestión
							de Productos</a>
					</li>
										<li class="nav-item"><a
						class="nav-link text-white text-center"
						href="ServletEmpresa?accion=listar">Configuración</a>
					</li>
				</ul>
        </div>
			<div class="text-center mb-4">
				<a class="btn btn-danger w-75 mx-auto d-block"
					href="CerrarSesionServlet">Cerrar Sesión</a>
			</div>
		</nav>

		<!-- Contenido Principal -->
		<div class="main-content">
			<h1 class="display-5 fw-bold text-center mb-5">Gestion de
				clientes</h1>

			<!-- Formulario de Busqueda -->
			<form action="ServletCliente" method="get">
				<div class="row mb-3">
					<div class="col-md-6">
						<label for="buscar" class="form-label">Buscar por Nombre o
							DNI</label> <input type="text" class="form-control" id="buscar"
							name="buscar" placeholder="Ingrese nombre o DNI" required>
					</div>
					<div class="col-md-6">
						<button type="submit" class="btn btn-secondary mt-4">Buscar</button>
					</div>
				</div>
			</form>

			<!-- Formulario para agregar cliente -->
			<form action="ServletCliente" method="post">
				<input type="hidden" name="id" value="${cliente.id}">
				<div class="row mb-3">
					<div class="col-md-6">
						<label for="nombre" class="form-label">Nombre</label> <input
							type="text" class="form-control" id="nombre" name="nombre"
							value="${cliente.nombre}" required>
					</div>
					<div class="col-md-6">
						<label for="apellido" class="form-label">Apellido</label> <input
							type="text" class="form-control" id="apellido" name="apellido"
							value="${cliente.apellido}" required>
					</div>
				</div>

				<div class="row mb-3">
					<div class="col-md-6">
						<label for="telefono" class="form-label">Telefono</label> <input
							type="tel" class="form-control" id="telefono" name="telefono"
							value="${cliente.telefono}" required>
					</div>
					<div class="col-md-6">
						<label for="dni" class="form-label">DNI</label> <input type="text"
							class="form-control" id="dni" name="dni"
							value="${cliente.dni}" required>
					</div>
				</div>

				<div class="row mb-3">
					<div class="col-md-6">
						<label for="contacto_dni" class="form-label">Contacto DNI</label>
						<input type="text" class="form-control" id="contacto_dni"
							name="contacto_dni" value="${cliente.contactoDni}">
					</div>
					<div class="col-md-6">
						<label for="contacto_nombre" class="form-label">Contacto
							Nombre</label> <input type="text" class="form-control"
							id="contacto_nombre" name="contacto_nombre"
							value="${cliente.contactoNombre}">
					</div>
				</div>

				<button type="submit" class="btn btn-primary">Guardar</button>
			</form>

			<div class="my-4"></div>

			<!-- Tabla de clientes -->
			<table class="table table-striped">
				<thead>
					<tr>
						<th>ID</th>
						<th>Nombre</th>
						<th>Apellido</th>
						<th>Telefono</th>
						<th>DNI</th>
						<th>Contacto DNI</th>
						<th>Contacto Nombre</th>
						<th>Acciones</th>
					</tr>
				</thead>
				<tbody>
					<%
					List<Cliente> clientes = (List<Cliente>) request.getAttribute("clientes");
					if (clientes != null && !clientes.isEmpty()) {
						for (Cliente cliente : clientes) {
					%>
					<tr>
						<td><%=cliente.getId()%></td>
						<td><%=cliente.getNombre()%></td>
						<td><%=cliente.getApellido()%></td>
						<td><%=cliente.getTelefono()%></td>
						<td><%=cliente.getDni()%></td>
						<td><%=cliente.getContactoDni() != null ? cliente.getContactoDni() : ""%></td>
						<td><%=cliente.getContactoNombre() != null ? cliente.getContactoNombre() : ""%></td>
						
						<td>
						<a href="ServletCliente?accion=editar&id=<%=cliente.getId()%>" class="btn btn-warning btn-sm">Editar</a>
						<a href="ServletCliente?accion=eliminar&id=<%=cliente.getId()%>" class="btn btn-danger btn-sm">Eliminar</a>
						</td>
						
					</tr>
					<%
					}
					} else {
					%>
					<tr>
						<td colspan="8" class="text-center">No hay clientes
							registrados</td>
					</tr>
					<%
					}
					%>
				</tbody>
			</table>
		</div>
	</div>

</body>
</html>