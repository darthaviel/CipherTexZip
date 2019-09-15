
package alt_tda.lista;



public class LISTA{

    Cursor cursor_lista = new Cursor();

    public int PRIMERO() {
        return 1;
    }

    public int FIN() {
        return cursor_lista.cuenta_elementos() + 1;
    }

    public int INSERTA(Object dato, int pos) {
        int lugar = cursor_lista.asignarMemoria();
        if (lugar == -1) {
            cursor_lista.amplearMemoria();
            lugar = cursor_lista.asignarMemoria();
        }
        if (pos == 1) {
            cursor_lista.setCubetas_cursor(lugar, dato, cursor_lista.getCabeza());
            cursor_lista.setCabeza(lugar);
        } else {
            int ant = cursor_lista.cubeta_anterior(pos);
            cursor_lista.setCubetas_cursor(lugar, dato, cursor_lista.getCubetas_cursorEncadenamiento(ant));
            cursor_lista.setCubetas_cursorEncadenamiento(ant, lugar);
        }
        return lugar;
    }

    public int SUPRIME(int pos) {
        int elementos = cursor_lista.cuenta_elementos();
        if (pos > elementos  && pos < 1 ) {
            return -1;
        }
        int cubeta_eliminar = cursor_lista.posicion_cubeta(pos);
        if (pos == 1 && elementos == 1) {
            cursor_lista.setCabeza(-1);
            cursor_lista.liberarMemoria(cubeta_eliminar);
        } else {
            if (pos == 1) {
                cursor_lista.setCabeza(cursor_lista.getCubetas_cursorEncadenamiento(cubeta_eliminar));
                cursor_lista.liberarMemoria(cubeta_eliminar);

            } else {
                int cubeta_anterior = cursor_lista.cubeta_anterior(pos);
                cursor_lista.setCubetas_cursorEncadenamiento(cubeta_anterior,cursor_lista.getCubetas_cursorEncadenamiento(cubeta_eliminar));
                cursor_lista.liberarMemoria(cubeta_eliminar);
            }
        }
      return cubeta_eliminar;
    }

    public Object RECUPERA(int pos) {
        int elementos = cursor_lista.cuenta_elementos();
        if (pos > elementos  && pos < 1 ) {
            return null;
        }
        return cursor_lista.getCubetas_cursorDato(cursor_lista.posicion_cubeta(pos));
    }

    public int LOCALIZA(Object dato) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public int ANTERIOR(int pos) {
        if (pos <= 1 || pos > cursor_lista.cuenta_elementos() + 1) {
            return -1;
        }
        return --pos;
    }

    public int SIGUIENTE(int pos) {
        if (pos < 1 && pos > cursor_lista.cuenta_elementos() + 1) {
            return -1;
        }
        return ++pos;
    }

    public void ANULA() {
        cursor_lista.setCabeza(-1);
        cursor_lista.limpiaMemoria();
    }

    public boolean VACIA() {
        boolean vacia = false;
        if (cursor_lista.cuenta_elementos()==0)
            vacia = true;
        return vacia;
    }

}
