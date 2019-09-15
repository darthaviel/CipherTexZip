
package alt_tda.lista;

import java.util.Arrays;



class Cursor {

    private int cabeza;
    private boolean[] memoria = new boolean[10000];
    private Nodo[] cubetas_cursor = new Nodo[10000];

    public Cursor() {
        this.cabeza = -1;
        for (int i = 0; i < cubetas_cursor.length; i++) {
            cubetas_cursor[i] =  new Nodo();            
        }
    }

    // Metodos del cursor
    // Metodos para administrar memoria del Cursor
    public int asignarMemoria() {
        for (int i = 0; i < memoria.length; i++) {
            if (memoria[i] == false) {
                memoria[i] = true;
                return i;
            }

        }
        return -1;
    }

    public void liberarMemoria(int cubeta) {
        memoria[cubeta] = false;

    }
    
    public void limpiaMemoria()
    {       
        for (int i = 0; i < memoria.length; i++) {
            memoria[i]=false;
            reestablecerTamaño();
        }
    }

    // Metodos apoyo Lista
    public int cuenta_elementos() {
        int contador = 0;
        for (int i = 0; i < memoria.length; i++) {
            if (memoria[i] == true) {
                contador++;
            }
        }

        return contador;
    }
    //problema encontrado 
    public int cubeta_anterior(int pos) {
        //int cuenta = 0;
        int cuenta = 1;
        int next = cabeza;
        while (pos-1 > cuenta){
            next = cubetas_cursor[next].getEncadenamiento();
            cuenta++;
        }
        /*while (pos < cuenta) {
            next = cubetas_cursor[next].getEncadenamiento();
            cuenta++;
        }*/
        return next;
    }

    public int posicion_cubeta(int pos) {
        int cuenta = 1;
        int next = cabeza;
        while (pos != cuenta) {
            next = cubetas_cursor[next].getEncadenamiento();
            cuenta++;
        }
        return next;
    }
    // Metodos de apoyo al cursor

    public void setCubetas_cursor(int index, Object dato, int encadenamiento) {
        cubetas_cursor[index].setEncadenamiento(encadenamiento);
        cubetas_cursor[index].setDato(dato);
    }

    public void setCabeza(int cabeza) {
        this.cabeza = cabeza;
    }

    public int getCabeza() {
        return cabeza;
    }

    public int getCubetas_cursorEncadenamiento(int index) {
        return cubetas_cursor[index].getEncadenamiento();
    }

    public void setCubetas_cursorEncadenamiento(int index, int encadenamiento) {
        cubetas_cursor[index].setEncadenamiento(encadenamiento);
    }

    public Object getCubetas_cursorDato(int index) {
        return cubetas_cursor[index].getDato();
    }

    void amplearMemoria() {
        memoria = Arrays.copyOf(memoria, memoria.length + 100);
        cubetas_cursor = Arrays.copyOf(cubetas_cursor, cubetas_cursor.length + 100);
    }

    private void reestablecerTamaño() {
        memoria = Arrays.copyOf(memoria, 10000);
        cubetas_cursor = Arrays.copyOf(cubetas_cursor, 10000);
    }
}
