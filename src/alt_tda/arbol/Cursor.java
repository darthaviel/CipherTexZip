package alt_tda.arbol;

import java.util.Arrays;

/**
 *
 * @author l
 */
class Cursor {

    private int raiz;

    private Nodo[] nodos_arbol = new Nodo[1000];

    private boolean[] memoria = new boolean[1000];

    public Cursor() {
        this.raiz = -1;
        for (int i = 0; i < nodos_arbol.length; i++) {
            nodos_arbol[i] = new Nodo();
        }
    }

    public int campoLibre() {
        int a = -1;
        while (true) {
            for (int i = 0; i < memoria.length; i++) {
                if (!memoria[i]) {
                    a = i;
                    memoria[i] = true;
                    break;
                }
            }
            if (a == -1) {
                ampliarMemoria();
            } else {
                break;
            }
        }

        return a;
    }

    private void ampliarMemoria() {
        nodos_arbol = Arrays.copyOf(nodos_arbol, nodos_arbol.length + 100);
        memoria = Arrays.copyOf(memoria, memoria.length + 100);
    }

    public int getRaiz() {
        return raiz;
    }

    public void setRaiz(int raiz) {
        this.raiz = raiz;
    }

    public void setEtiqueta(int nodo, Object etiqueta) {
        nodos_arbol[nodo].setDato(etiqueta);
    }
    
    public void setHijoMasIzq(int nodo, int hijomasizq){
        nodos_arbol[nodo].setHijo_mas_izquierdo(hijomasizq);
    }
    
    public void setHermanoDer(int nodo, int hermanoder){
        nodos_arbol[nodo].setHermano_derecho(hermanoder);
    }

    void anularMemoria() {
        if (memoria.length > 1000) {
            memoria = Arrays.copyOf(memoria, 1000);
            nodos_arbol = Arrays.copyOf(nodos_arbol, 1000);
        }
        for (int i = 0; i < memoria.length; i++) {
            memoria[i] = false;
        }
    }

    public boolean exist(int n) {
        if (n < 0) {
            return false;
        }
        if (n < memoria.length) {
            if (memoria[n]) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    Object getEtiqueta(int n) {
        return nodos_arbol[n].getDato();
    }

    int getHermanoDer(int n) {
        return nodos_arbol[n].getHermano_derecho();
    }

    int getHijoMasIzq(int n) {
        return nodos_arbol[n].getHijo_mas_izquierdo();
    }

}
