package interfaces;

import modelo.DetalleVenta;

public interface InterfazDetalleVentas {
	
	//Añadir venta de cada producto con su
	//total por cada producto
	public boolean add(DetalleVenta deve);
	
	public int obtenerIdVenta();
}
