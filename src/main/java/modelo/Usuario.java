package modelo;

public class Usuario {

	// Atributos:

	private int codigo, tipo;

	private String nombre, apellido, usuario, clave;

	
	
	public int getCodigo() {

		return codigo;

	}

	public void setCodigo(int codigo) {

		this.codigo = codigo;

	}

	public int getTipo() {

		return tipo;

	}

	public void setTipo(int tipo) {

		this.tipo = tipo;

	}

	public String getNombre() {

		return nombre;

	}

	public void setNombre(String nombre) {

		this.nombre = nombre;

	}

	public String getApellido() {

		return apellido;

	}

	public void setApellido(String apellido) {

		this.apellido = apellido;

	}

	public String getUsuario() {

		return usuario;

	}

	public void setUsuario(String usuario) {

		this.usuario = usuario;

	}

	public String getClave() {

		return clave;

	}

	public void setClave(String clave) {

		this.clave = clave;

	}
}