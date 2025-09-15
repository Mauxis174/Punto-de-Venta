<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" session="true"%>
<%@ page import="java.util.List"%>
<%@ page import="modelo.Proveedor"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Gestion de Proveedores</title>
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
						<div 				class="menu-items d-flex flex-column justify-content-center align-items-center">
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
				Proveedores</h1>

			<!-- Formulario de Busqueda -->
			<form action="ServletProveedor" method="get">
				<div class="row mb-3">
					<div class="col-md-6">
						<label for="buscar" class="form-label">Buscar por Nombre o
							RUC</label>
							<input type="text" class="form-control" id="buscar"
							name="buscar" placeholder="Ingrese nombre o ruc" required>
					</div>
					<div class="col-md-6">
						<button type="submit" class="btn btn-secondary mt-4">Buscar</button>
					</div>
				</div>
			</form>

			<!-- Formulario para agregar Proveedor -->
			<form action="ServletProveedor" method="post">
				<input type="hidden" name="id" value="${proveedor.id_prov}">
				<div class="row mb-3">
					<div class="col-md-6">
						<label for="ruc" class="form-label">Ruc</label>
						<input
							type="text" class="form-control" id="ruc" name="ruc"
							value="${proveedor.ruc}" required>
					</div>
					<div class="col-md-6">
						<label for="nombre" class="form-label">Nombre</label>
						<input
							type="text" class="form-control" id="nombre" name="nombre"
							value="${proveedor.nombre}" required>
					</div>
				</div>

				<div class="row mb-3">
					<div class="col-md-6">
						<label for="telefono" class="form-label">Telefono</label>
						<input
							type="text" class="form-control" id="telefono" name="telefono"
							value="${proveedor.telefono}" required>
					</div>
					<div class="col-md-6">
						<label for="direccion" class="form-label">Direccion</label>
						<input type="text"
							class="form-control" id="direccion" name="direccion"
							value="${proveedor.direccion}" required>
					</div>
				</div>

				<div class="row mb-3">
					<div class="col-md-6">
						<label for="razon" class="form-label">Razon</label>
						<input type="text" class="form-control" id="razon"
							name="razon" value="${proveedor.razon}">
					</div>
				</div>

				<button type="submit" class="btn btn-primary">Guardar</button>
			</form>

			<div class="my-4"></div>

			<!-- Tabla de Proveedores -->
			<table class="table table-striped">
				<thead>
					<tr>
						<th>ID</th>
						<th>Ruc</th>
						<th>Nombre</th>
						<th>Telefono</th>
						<th>Direccion</th>
						<th>Razon</th>
						<th>Acciones</th>
					</tr>
				</thead>
				<tbody>
					<%
					List<Proveedor> proveedores = (List<Proveedor>) request.getAttribute("proveedores");
					if (proveedores != null && !proveedores.isEmpty()) {
						for (Proveedor proveedor : proveedores) {
					%>
					<tr>
						<td><%=proveedor.getId_prov()%></td>
						<td><%=proveedor.getRuc()%></td>
						<td><%=proveedor.getNombre()%></td>
						<td><%=proveedor.getTelefono()%></td>
						<td><%=proveedor.getDireccion()%></td>
						<td><%=proveedor.getRazon()%></td>

						<td>
							<a
								href="ServletProveedor?accion=editar&id=<%=proveedor.getId_prov()%>"
								class="btn btn-warning btn-sm">Editar
							</a>
							<a
								href="ServletProveedor?accion=eliminar&id=<%=proveedor.getId_prov()%>"
								class="btn btn-danger btn-sm">Eliminar
							</a>
						
						</td>

					</tr>
					<%
					}
					} else {
					%>
					<tr>
						<td colspan="8" class="text-center">No hay proveedores
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