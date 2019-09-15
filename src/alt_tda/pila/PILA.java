package alt_tda.pila;

import alt_tda.lista.LISTA;



public class PILA {

    LISTA lista_pila = new LISTA();

    public boolean METE(Object dato) {

        if (lista_pila.INSERTA(dato, lista_pila.FIN()) == -1) {
            return false;
        }
        return true;
    }

    public Object TOPE() {
        return lista_pila.RECUPERA(lista_pila.ANTERIOR(lista_pila.FIN()));
    }

    public boolean SACA() {
        if (lista_pila.SUPRIME(lista_pila.ANTERIOR(lista_pila.FIN())) == -1) {
            return false;
        }
        return true;
    }

    public boolean VACIA() {
        return lista_pila.VACIA();
    }

    public int ANULA() {
        lista_pila.ANULA();
        return -1;
    }

}
