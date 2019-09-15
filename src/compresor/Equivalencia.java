
package compresor;

/**
 *
 * @author l
 */
public class Equivalencia {
private char caracter;
private String codigoHuffman;

    public Equivalencia(char caracter, String codigoHuffman) {
        this.caracter = caracter;
        this.codigoHuffman = codigoHuffman;
    }

    public char getCaracter() {
        return caracter;
    }

    public String getCodigoHuffman() {
        return codigoHuffman;
    }

}
