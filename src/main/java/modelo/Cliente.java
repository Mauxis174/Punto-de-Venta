package modelo;

public class Cliente {
	private int id;
    private String nombre;
    private String apellido;
    private String telefono;
    private String dni;
    private String contactoDni;
    private String contactoNombre;
    
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getDni() {
		return dni;
	}
	public void setDni(String dni) {
		this.dni = dni;
	}
	public String getContactoDni() {
		return contactoDni;
	}
	public void setContactoDni(String contactoDni) {
		this.contactoDni = contactoDni;
	}
	public String getContactoNombre() {
		return contactoNombre;
	}
	public void setContactoNombre(String contactoNombre) {
		this.contactoNombre = contactoNombre;
	}
    
    
}
