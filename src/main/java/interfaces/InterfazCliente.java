package interfaces;

import java.util.List;

import modelo.Cliente;

public interface InterfazCliente {
	public List<Cliente> listar();
	public List<Cliente> buscarPorDniONombre(String buscar);
    public Cliente list(int id);
    public boolean add(Cliente cliente);
    public boolean edit(Cliente cliente);
    public boolean eliminar(int id);
    public Cliente buscar(String buscar);
}
