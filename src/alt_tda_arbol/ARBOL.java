package alt_tda_arbol;

import alt_tda.pila.PILA;

/**
 *
 * @author l
 */
public class ARBOL {

    Cursor cursor_arbol = new Cursor();

    public int PADRE(int n) {
        PILA pila = new PILA();
        boolean regresa = false;
        if (cursor_arbol.exist(n)) {
            pila.METE(RAIZ());
            while (!pila.VACIA()) {
                if (((Integer) pila.TOPE()) == n) {
                    pila.SACA();
                    if (pila.VACIA()) {
                        return -1;
                    } else {
                        return ((Integer) pila.TOPE());
                    }
                } else if ((HIJO_MAS_IZQ((Integer) pila.TOPE()) == -1) || regresa) {
                    if (HERMANO_DER((Integer) pila.TOPE()) != -1) {
                        int a = HERMANO_DER((Integer) pila.TOPE());
                        pila.SACA();
                        pila.METE(a);
                        regresa = false;
                    } else {
                        pila.SACA();
                        regresa = true;
                    }
                } else {
                    pila.METE(HIJO_MAS_IZQ((Integer) pila.TOPE()));
                    regresa = false;

                }
            }
        }
        return -2;

    }

    public int HIJO_MAS_IZQ(int n) {
        if (cursor_arbol.exist(n)) {
            return cursor_arbol.getHijoMasIzq(n);
        } else {
            return -2;
        }

    }

    public int HERMANO_DER(int n) {
        if (cursor_arbol.exist(n)) {
            return cursor_arbol.getHermanoDer(n);
        } else {
            return -2;
        }

    }

    public Object ETIQUETA(int n) {
        if (cursor_arbol.exist(n)) {
            return cursor_arbol.getEtiqueta(n);
        } else {
            return -1;
        }

    }

    public int CREA(Object etiqueta, int... hijos) {
        for (int hijo : hijos) {
            if (!cursor_arbol.exist(hijo)) {
                return -1;
            }
        }

        int nodo = cursor_arbol.campoLibre();
        cursor_arbol.setEtiqueta(nodo, etiqueta);
        cursor_arbol.setHijoMasIzq(nodo, hijos[0]);
        for (int i = 1; i < hijos.length; i++) {
            cursor_arbol.setHermanoDer(hijos[i - 1], hijos[i]);
        }
        
        cursor_arbol.setRaiz(nodo);

        return nodo;
    }

    public int CREA(Object etiqueta) {
        int nodo = cursor_arbol.campoLibre();
        cursor_arbol.setEtiqueta(nodo, etiqueta);
        
        cursor_arbol.setRaiz(nodo);
        
        return nodo;
    }

    public int RAIZ() {
        return cursor_arbol.getRaiz();
    }

    public void ANULA() {
        cursor_arbol.setRaiz(-1);
        cursor_arbol.anularMemoria();
    }

}
