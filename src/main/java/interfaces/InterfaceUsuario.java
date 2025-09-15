package interfaces;

import modelo.Usuario;

public interface InterfaceUsuario {

	public Usuario validarUsuario(String usuario, String clave);

	public boolean existeUsuario(Usuario u);

	public boolean registrarUsuario(Usuario u);
	
    public Usuario list(String usuario);

}
