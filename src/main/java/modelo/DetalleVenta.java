package modelo;

public class DetalleVenta {
	private int id_detalleventa;
	private int cantidad;
	private double precio;
	private double subtotal;
	private int id_venta;
	private int id_producto;
	private int codigo;
    private String nombre;

	public DetalleVenta() {
		super();
		// TODO Auto-generated constructor stub
	}
	
    public double subTotalPorItem(int cantidad, double precio) {
        return cantidad * precio;
    }
	
	public int getId_detalleventa() {
		return id_detalleventa;
	}
	public void setId_detalleventa(int id_detalleventa) {
		this.id_detalleventa = id_detalleventa;
	}
	public int getId_producto() {
		return id_producto;
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
	public double getPrecio() {
		return precio;
	}
	public void setPrecio(double precio) {
		this.precio = precio;
	}
	public int getCantidad() {
		return cantidad;
	}
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	public double getSubtotal() {
		return subtotal;
	}
	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
	}
	public int getId_venta() {
		return id_venta;
	}
	public void setId_venta(int id_venta) {
		this.id_venta = id_venta;
	}
	
	
	
}
