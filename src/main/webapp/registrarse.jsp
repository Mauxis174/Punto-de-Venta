<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>Registro</title>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4" crossorigin="anonymous"></script>
<link href="css/estilosregistro.css" rel="stylesheet" type="text/css">
</head>
<body class="bg-light">

	<!-- Caja del login -->
	<div class="container">
		<form action="Registrarse" method="get">
			<h2>Registro Punto de Venta</h2>
			<div>

					<div class="input-field">
						<input type="text" id="txtNombre" name="txtNombre"
							value="${u.nombre}" required>
						<label for="txtNombre">Nombre</label>
					</div>

					<div class="input-field">
						<input type="text"
							 id="txtApellido" name="txtApellido"
							value="${u.apellido}" required>
						<label for="txtApellido">Apellido</label> 
					</div>

					<div class="input-field">
						<input type="text"
							 id="txtUsuario" name="txtUsuario" required>
						<label for="txtUsuario">Usuario</label> 
					</div>

					<div class="input-field">
						 <input
							type="password" id="txtContrasenia"
							name="txtContrasenia" value="${u.clave}" required>
						<label for="txtContrasenia">Contraseña</label>
					</div>



					<select class="form-select" aria-label="Default select example"
						id="tipoUsuario" name="tipoUsuario" required>
						<option value="">Seleccione una opción</option>
						<option value="1">Admin</option>
						<option value="2">Vendedor</option>
					</select>



			</div>

			<div class="Create-account">
				<p>¿Ya tienes una cuenta? <a href="login.jsp">Login</a></p>
			</div>

			<div class="containerButton">
				<input type="submit" class="BotonAceptar" id="btnAceptar"
					value="Registrarse">
			</div>
			<div class="mensajeAlerta">${msg}</div>
		</form>
	</div>
	<!--</div>-->

</body>
</html>