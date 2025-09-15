package modelo;

public class Producto {
	private int id_producto;
	private int codigo;
	private String nombre;
	private Double precio;
	private int stock;
	private boolean tieneVentas;
	private int numeroVentas;

	public int getId_producto() {
		return id_producto;
	}
	public boolean isTieneVentas() {
		return tieneVentas;
	}
	public void setTieneVentas(boolean tieneVentas) {
		this.tieneVentas = tieneVentas;
	}
	public int getNumeroVentas() {
		return numeroVentas;
	}
	public void setNumeroVentas(int numeroVentas) {
		this.numeroVentas = numeroVentas;
	}
	public void setId_producto(int id_producto) {
		this.id_producto = id_producto;
	}
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Double getPrecio() {
		return precio;
	}
	public void setPrecio(Double precio) {
		this.precio = precio;
	}
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}
}
