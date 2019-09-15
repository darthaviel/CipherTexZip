package compresor;

import alt_tda.lista.LISTA;
import alt_tda.arbol.ARBOL;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author l
 */
public class Comprimir {

    LISTA organizador = new LISTA();
    ARBOL huffmantree = new ARBOL();

    public void Comprimir(File archivo) {
        char[] text = new char[0];
        try {
            String name = archivo.getName();
            text = Files.readString(archivo.toPath()).toCharArray();
        } catch (IOException ex) {
            Logger.getLogger(Comprimir.class.getName()).log(Level.SEVERE, null, ex);
        }

        for (char caracter : text) {
            if (clasificarCaracter(caracter)) {
                organizador.INSERTA(new Ponderador(caracter, 1), organizador.FIN());
            }
        }

        construirHuffmanTree();

    }

    private boolean clasificarCaracter(char caracter) {
        for (int i = organizador.PRIMERO(); i < organizador.FIN(); i++) {
            if (((Ponderador) organizador.RECUPERA(i)).getDato().equals(caracter)) {
                Ponderador ponderador = (Ponderador) organizador.RECUPERA(i);
                organizador.SUPRIME(i);
                ponderador.setCantidad(ponderador.getCantidad() + 1);
                organizador.INSERTA(ponderador, i);
                return false;
            }
        }

        return true;
    }

    private void construirHuffmanTree() {
        for (int i = organizador.PRIMERO(); i < organizador.FIN(); i++) {
            Ponderador ponderador = (Ponderador) organizador.RECUPERA(i);
            ponderador.setDato(huffmantree.CREA(ponderador.getDato()));
            organizador.SUPRIME(i);
            organizador.INSERTA(ponderador, i);
        }

        while (organizador.FIN() != organizador.ANTERIOR(organizador.FIN())) {
            int menor = organizador.PRIMERO();
            int antemenor = organizador.SIGUIENTE(menor);
            if (((Ponderador) organizador.RECUPERA(menor)).getCantidad() > ((Ponderador) organizador.RECUPERA(antemenor)).getCantidad()) {
                int rmenor = antemenor;
                antemenor = menor;
                menor = rmenor;
            }
            for (int i = 3; i < organizador.FIN(); i++) {
                if (((Ponderador) organizador.RECUPERA(antemenor)).getCantidad() > ((Ponderador) organizador.RECUPERA(i)).getCantidad()) {
                    antemenor = i;
                } else {
                    if (((Ponderador) organizador.RECUPERA(menor)).getCantidad() > ((Ponderador) organizador.RECUPERA(i)).getCantidad()) {
                        antemenor = menor;
                        menor = i;
                    }
                }
            }
            Ponderador ponderador = (Ponderador) organizador.RECUPERA(menor);
            ponderador = new Ponderador(huffmantree.CREA(null, ((Integer) ((Ponderador) organizador.RECUPERA(menor)).getDato()), ((Integer) ((Ponderador) organizador.RECUPERA(antemenor)).getDato())), ((Ponderador) organizador.RECUPERA(menor)).getCantidad() + ((Ponderador) organizador.RECUPERA(antemenor)).getCantidad());
            organizador.SUPRIME(menor);
            organizador.INSERTA(ponderador, menor);
            organizador.SUPRIME(antemenor);
        }
    }

}
