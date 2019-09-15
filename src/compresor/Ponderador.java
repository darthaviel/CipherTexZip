
package compresor;

/**
 *
 * @author l
 */
public class Ponderador {
    private Object dato;
    private long cantidad;

    Ponderador(Object dato, long cantidad) {
        this.dato = dato;
        this.cantidad = cantidad;
    }

    public Object getDato() {
        return dato;
    }
    
    public void setDato(Object dato){
        this.dato = dato;
    }

    public long getCantidad() {
        return cantidad;
    }

    public void setCantidad(long cantidad) {
        this.cantidad = cantidad;
    }
    

}
