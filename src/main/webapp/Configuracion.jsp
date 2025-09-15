<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" session="true"%>
<%@ page import="java.util.List"%>
<%@ page import="modelo.Empresa"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Configuracion</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link href="css/principal.css" rel="stylesheet">
</head>
<body class="d-flex">
	
	<nav class="sidebar bg-dark d-flex flex-column justify-content-between">
        
        <!-- Contenedor superior del menú -->
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

        <!-- Botón de cerrar sesión abajo -->
        <div class="text-center mb-4">
            <a class="btn btn-danger" href="CerrarSesionServlet">Cerrar Sesión</a>
        </div>

    </nav>
    
    		<div class="main-content">
			<h1 class="display-5 fw-bold text-center mb-5">Datos de la Empresa</h1>

	
	<%
					Empresa empresa = (Empresa) request.getAttribute("empresa");

	%>

	<form action="ServletEmpresa" method="post">
		<div class="row mb-3">
			<div class="col-md-6">
				<label for="ruc" class="form-label">RUC</label>
				<input
					type="text" class="form-control" id="ruc" name="ruc"
					value="${empresa.ruc}" required>
			</div>
			<div class="col-md-6">
				<label for="gerente" class="form-label">Gerente</label>
				<input
					type="text" class="form-control" id="gerente" name="gerente"
					value="${empresa.gerente}" required>
			</div>
		</div>

		<div class="row mb-3">
			<div class="col-md-6">
				<label for="direccion" class="form-label">Direccion</label>
				<input
					type="text" class="form-control" id="direccion" name="direccion"
					value="${empresa.direccion}" required>
			</div>
			<div class="col-md-6">
				<label for="telefono" class="form-label">Telefono</label>
				<input type="text"
					class="form-control" id="telefono" name="telefono" value="${empresa.telefono}"
					required>
			</div>
		</div>
				<div class="row mb-3">
			<div class="col-md-6">
				<label for="razon" class="form-label">Razón Social</label> <input
					type="text" class="form-control" id="razon"
					name="razon" value="${empresa.razon}">
			</div>
			
		</div>

		<button type="submit" class="btn btn-primary">Guardar</button>
	</form>
	</div>
</body>
</html>