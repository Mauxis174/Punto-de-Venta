package interfaces;

import modelo.Empresa;

public interface InterfazEmpresa {

    public boolean add(Empresa empresa);
    public int obtenerIdMáximoEmpresa();
    public Empresa buscarPorIdMaximo();
}
