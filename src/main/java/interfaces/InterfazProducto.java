package interfaces;

import java.util.List;

import modelo.Producto;

public interface InterfazProducto {
	public List listar();
	public Producto list(int cod);
	public boolean add(Producto pro);
	public boolean edit(Producto pro);
	public boolean eliminar(int cod);
    public List<Producto> buscarPorCodigoNombre(String buscar);
}
