<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>Login</title>

<link href="css/estiloslogin.css" rel="stylesheet" type="text/css">

</head>
<body class="bg-light">

<!-- Caja del login -->
	<div class="container">
		<form action="LogueoServlet" method="get">
			<h2>Punto de Venta</h2>
			<div class="input-field">
				<input type="text" id="txtCorreo"
					name="txtCorreo" required> <label for="txtCorreo">Ingresar
					Email</label>
			</div>

			<div class="input-field">
				<input type="password" id="txtClave"
					name="txtClave" required> <label for="txtClave">Contraseña</label>
			</div>

			<div class="Create-account">
				<p>
					¿Aún no tienes una cuenta? <a href="registrarse.jsp">Regístrate</a>
				</p>
			</div>

			<div class="containerButton">
				<input type="submit" class="BotonAceptar" id="btnAceptar"
					value="Aceptar">
			</div>
		</form>

		<div class="mensajeAlerta">${msg}</div>
	</div>

</body>
</html>
