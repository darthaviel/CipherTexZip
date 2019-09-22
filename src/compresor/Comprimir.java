package compresor;

import abstra.BitByteAbstraction;
import alt_tda.lista.LISTA;
import alt_tda.arbol.ARBOL;
import alt_tda.pila.PILA;
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
    char[] text = new char[0];
    String hash = "";
    String huffmantextcode = "";
    String arbol = "";
    BitByteAbstraction bitbyteconv = new BitByteAbstraction();

    public void Comprimir(File archivo) {
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

        //mostrar equivalencias
        for (int i = 1; i < organizador.FIN(); i++) {
            System.out.println((Ponderador) organizador.RECUPERA(i));
        }

        construirHuffmanTree();

        generarCodigoHuffman();

        //ver codigo huffman
        for (int i = 1; i < organizador.FIN(); i++) {
            System.out.println((Equivalencia) organizador.RECUPERA(i));
        }

        cambiarTextoAHuffman();

        System.out.println(arbol);
        System.out.println(huffmantextcode);

        prepararArbol();

        prepararBytes();

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

        while (organizador.ANTERIOR(organizador.FIN()) != organizador.PRIMERO()) {
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
                }
                if (((Ponderador) organizador.RECUPERA(menor)).getCantidad() > ((Ponderador) organizador.RECUPERA(antemenor)).getCantidad()) {
                    int rmenor = antemenor;
                    antemenor = menor;
                    menor = rmenor;
                }

            }
            if (((Ponderador) organizador.RECUPERA(menor)).getCantidad() == ((Ponderador) organizador.RECUPERA(antemenor)).getCantidad()) {
                int rmenor = antemenor;
                antemenor = menor;
                menor = rmenor;
            }
            Ponderador ponderador = (Ponderador) organizador.RECUPERA(menor);
            ponderador = new Ponderador(huffmantree.CREA(null, ((Integer) ((Ponderador) organizador.RECUPERA(menor)).getDato()), ((Integer) ((Ponderador) organizador.RECUPERA(antemenor)).getDato())), ((Ponderador) organizador.RECUPERA(menor)).getCantidad() + ((Ponderador) organizador.RECUPERA(antemenor)).getCantidad());
            organizador.SUPRIME(antemenor);
            organizador.INSERTA(ponderador, antemenor);
            organizador.SUPRIME(menor);
        }
    }

    private void generarCodigoHuffman() {
        PILA pila = new PILA();
        LISTA huffmancode = new LISTA();
        boolean regresa = false;
        pila.METE(huffmantree.RAIZ());
        organizador.ANULA();

        while (!pila.VACIA()) {
            if ((huffmantree.HIJO_MAS_IZQ((Integer) pila.TOPE()) == -1) || regresa) {
                if (huffmantree.HIJO_MAS_IZQ((Integer) pila.TOPE()) == -1) {
                    String huffmanc = "";
                    for (int i = huffmancode.PRIMERO(); i < huffmancode.FIN(); i++) {
                        huffmanc = huffmancode.RECUPERA(i) + huffmanc;
                    }
                    arbol = arbol.substring(0, arbol.length() - 1) + "1" + ((Character) huffmantree.ETIQUETA((Integer) pila.TOPE()));
                    organizador.INSERTA(new Equivalencia(((Character) huffmantree.ETIQUETA((Integer) pila.TOPE())), huffmanc), organizador.FIN());
                }

                if (huffmantree.HERMANO_DER((Integer) pila.TOPE()) != -1) {
                    int a = huffmantree.HERMANO_DER((Integer) pila.TOPE());
                    pila.SACA();
                    huffmancode.SUPRIME(huffmancode.PRIMERO());
                    pila.METE(a);
                    huffmancode.INSERTA('1', huffmancode.PRIMERO());
                    arbol = arbol + "0";
                    regresa = false;
                } else {
                    pila.SACA();
                    if (!huffmancode.VACIA()) {
                        huffmancode.SUPRIME(huffmancode.PRIMERO());
                    } else {
                        System.out.println("vacia");
                        System.out.println(pila.VACIA());
                    }
                    regresa = true;
                }
            } else {
                pila.METE(huffmantree.HIJO_MAS_IZQ((Integer) pila.TOPE()));
                huffmancode.INSERTA('0', huffmancode.PRIMERO());
                regresa = false;
                arbol = arbol + "0";

            }
        }
    }

    private void cambiarTextoAHuffman() {
        for (char caracter : text) {
            for (int i = organizador.PRIMERO(); i < organizador.FIN(); i++) {
                if (((Equivalencia) organizador.RECUPERA(i)).getCaracter() == caracter) {
                    huffmantextcode = huffmantextcode + ((Equivalencia) organizador.RECUPERA(i)).getCodigoHuffman();
                    break;
                }
            }
        }
    }

    private void prepararArbol() {
        String bittree = "";
        for (int i = 0; i < arbol.length(); i++) {
            bittree = bittree + arbol.charAt(i);
            if (arbol.charAt(i) == '1') {
                i++;
                bittree = bitbyteconv.toBitStr((byte) arbol.charAt(i));
            }
        }
        arbol = bittree;
    }

    private void prepararBytes() {
        int nbit = (4 + arbol.length() + huffmantextcode.length() + hash.length())%8;
        arbol = arbol + bitbyteconv.intToExactBit(3, nbit);
    }

}
