<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" session="true" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Principal</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="css/principal.css" rel="stylesheet">
</head>
<body class="bg-light">

<!-- MENU LATERAL -->
<div class="d-flex">

    <nav class="sidebar bg-dark d-flex flex-column justify-content-between">
        
        <!-- Contenedor superior del menú -->
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

        <!-- Botón de cerrar sesión abajo -->
        <div class="text-center mb-4">
            <a class="btn btn-danger" href="CerrarSesionServlet">Cerrar Sesión</a>
        </div>

    </nav>

    <!-- CONTENIDO PRINCIPAL -->
    <div class="main-content flex-grow-1 p-5">
        <h1 class="display-5 fw-bold text-center mb-5">
            SISTEMA DE VENTAS
        </h1>

    </div>

</div>

</body>
</html>
