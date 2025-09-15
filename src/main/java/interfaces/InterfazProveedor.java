package interfaces;
import java.util.List;

import modelo.Proveedor;

public interface InterfazProveedor {
	public List listar();
	public Proveedor list(int cod);
	public boolean add(Proveedor prov);
	public boolean edit(Proveedor prov);
	public boolean eliminar(int cod);
    public List<Proveedor> buscarPorRucNombre(String buscar);
}
