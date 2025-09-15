package interfaces;

import java.util.List;

import modelo.Ventas;

public interface InterfazVentas {
	public boolean add(double totalVenta);
	public List<Ventas> buscarPorTotalFinal(String buscar);
}
