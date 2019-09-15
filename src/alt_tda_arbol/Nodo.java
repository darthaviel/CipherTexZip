package alt_tda_arbol;

/**
 *
 * @author l
 */
public class Nodo {

    private int hijo_mas_izquierdo;
    private int hermano_derecho;
    private Object dato;

    public int getHijo_mas_izquierdo() {
        return hijo_mas_izquierdo;
    }

    public void setHijo_mas_izquierdo(int hijo_mas_izquierdo) {
        this.hijo_mas_izquierdo = hijo_mas_izquierdo;
    }

    public int getHermano_derecho() {
        return hermano_derecho;
    }

    public void setHermano_derecho(int hermano_derecho) {
        this.hermano_derecho = hermano_derecho;
    }

    public Object getDato() {
        return dato;
    }

    public void setDato(Object dato) {
        this.dato = dato;
    }

    public Nodo(int hijo_mas_izquierdo, int hermano_derecho, Object dato) {
        this.hijo_mas_izquierdo = hijo_mas_izquierdo;
        this.hermano_derecho = hermano_derecho;
        this.dato = dato;
    }

    public Nodo() {
        this.dato = null;
        this.hermano_derecho = -1;
        this.hijo_mas_izquierdo = -1;
    }

}
