<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="java.util.List" %>
    <%@ page import="modelo.Producto, dao.ProductoDAO" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Lista de Productos</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="css/principal.css" rel="stylesheet">
</head>
<body class="bg-light">
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
    <div class="main-content">
        <h1 class="display-5 fw-bold text-center mb-5">
            Bienvenido al Sistema - Gestionar Productos
        </h1>
<!-- Formulario de Busqueda -->
        <form action="ServletProducto" method="get">
            <div class="row mb-3">
                <div class="col-md-6">
                    <label for="buscar" class="form-label">Buscar por Nombre o Código</label>
					<input type="text" class="form-control" id="buscar" name="buscar" placeholder="Ingrese Nombre o Codigo"
					       value="<%= request.getAttribute("terminoBusqueda") != null ? request.getAttribute("terminoBusqueda") : "" %>" required>
                </div>
                <div class="col-md-6">
                    <button type="submit" class="btn btn-secondary mt-4">Buscar</button>
                </div>
            </div>
        </form>

        <!-- Formulario para agregar propietario -->
        <form action="ServletProducto" method="post">
            <input type="hidden" name="id" value="${producto.id_producto}">
            <div class="row mb-3">
                <div class="col-md-6">
                    <label for="codigo" class="form-label">Código</label>
                    <input type="text" class="form-control" id="codigo" name="codigo" value="${producto.codigo}" required>
                </div>
                <div class="col-md-6">
                    <label for="nombre" class="form-label">Nombre</label>
                    <input type="text" class="form-control" id="nombre" name="nombre" value="${producto.nombre}" required>
                </div>
            </div>

            <div class="row mb-3">
                <div class="col-md-6">
                    <label for="precio" class="form-label">Precio</label>
                    <input type="tel" class="form-control" id="precio" name="precio" value="${producto.precio}" required>
                </div>
                <div class="col-md-6">
                    <label for="stock" class="form-label">Stock</label>
                    <input type="text" class="form-control" id="stock" name="stock" value="${producto.stock}" required>
                </div>
            </div>
            <button type="submit" class="btn btn-primary">Guardar</button>
        </form>

        <div class="my-4"></div>

        <!-- Tabla de Productos -->
        <table class="table table-striped">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Código</th>
                    <th>Nombre</th>
                    <th>Precio</th>
                    <th>Stock</th>
                    <th>Acciones</th>
                </tr>
            </thead>
            <tbody>
                <% 
                    List<Producto> productos = (List<Producto>) request.getAttribute("productos");
                    if (productos != null && !productos.isEmpty()) {
                        for (Producto producto : productos) {
                %>
                <tr>
                    <td><%= producto.getId_producto() %></td>
                    <td><%= producto.getCodigo() %></td>
                    <td><%= producto.getNombre() %></td>
                    <td><%= producto.getPrecio() %></td>
                    <td><%= producto.getStock() %></td>
					<!-- En la sección de la tabla, modificar la columna de acciones -->
					<td>
					    <a href="ServletProducto?accion=editar&id=<%=producto.getId_producto()%>" class="btn btn-warning btn-sm">Editar</a>

					    
					<% if (producto.isTieneVentas()) { %>
					    <a href="ServletProducto?accion=eliminar&id=<%=producto.getId_producto()%>" 
					       class="btn btn-secondary btn-sm" 
					       title="Desactivar producto (tiene <%=producto.getNumeroVentas()%> ventas registradas)"
					       onclick="return confirm('Este producto tiene <%=producto.getNumeroVentas()%> ventas registradas. ¿Desea eliminarlo?')">
					       Eliminar
					    </a>
					<% } else { %>
					    <a href="ServletProducto?accion=eliminar&id=<%=producto.getId_producto()%>" 
					       class="btn btn-danger btn-sm" 
					       title="Eliminar producto permanentemente"
					       onclick="return confirm('Este producto no tiene ventas registradas. ¿Desea eliminarlo permanentemente?')">
					       Eliminar
					    </a>
					<% } %>

					</td>
                </tr>
                <% 
                        }
                    } else {
                %>
                <tr>
                    <td colspan="8" class="text-center">No hay productos registrados</td>
                </tr>
                <% } %>
            </tbody>
        </table>
    </div>

</div>
</body>
</html>