
package descompresor;

import abstra.BitByteAbstraction;
import alt_tda.arbol.ARBOL;
import alt_tda.pila.PILA;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author l
 */
public class Descomprimir {

    private File in;
    private File out;
    private byte[] inbyte = new byte[0];
    private String bits = "";
    private BitByteAbstraction bitbyteconv = new BitByteAbstraction();
    private ARBOL huffmantree = new ARBOL();

    public void descomprimir(File in, File out) {
        this.in = in;
        this.out = out;

        try {
            inbyte = Files.readAllBytes(in.toPath());
        } catch (IOException ex) {
            Logger.getLogger(Descomprimir.class.getName()).log(Level.SEVERE, null, ex);
        }

        for (byte by : inbyte) {
            bits = bits + bitbyteconv.toBitStr(by);
        }

        inbyte = new byte[0];

        //removiendo bandera inicial (provicional)
        bits = bits.substring(1);

        reconstruirArbolHuffman();

        reconstruirTexto();

    }

    private void reconstruirArbolHuffman() {
        PILA prev = new PILA();
        int m;
        prev.METE(-1);
        bits = bits.substring(1);

        while (true) {
            if (bits.charAt(0) == 0) {
                m = -1;
                bits = bits.substring(1);
            } else {
                bits = bits.substring(1);
                m = huffmantree.CREA((char) bitbyteconv.toByte(bits.substring(0, 7)));
                bits = bits.substring(8);
            }

            if (m != -1 && ((Integer) prev.TOPE()) != -1) {
                m = huffmantree.CREA(null, (Integer) prev.TOPE(), m);
                prev.SACA();
                prev.SACA();
                if (prev.VACIA()) {
                    break;
                }
            }

            prev.METE(m);

        }
    }

    private void reconstruirTexto(){
        bits = bits.substring(0, bits.length() - (bitbyteconv.exactBitToInt(bits.substring(0, 2)) + 1));
        bits = bits.substring(3);
        int control = huffmantree.RAIZ();

        try {
            FileOutputStream write = new FileOutputStream(out.getPath());

            while (!bits.isEmpty()) {
                System.out.println(bits);
                if (bits.charAt(0) == 0) {
                    control = huffmantree.HIJO_MAS_IZQ(control);
                } else {
                    control = huffmantree.HIJO_MAS_IZQ(control);
                    control = huffmantree.HERMANO_DER(control);
                }

                if (huffmantree.HIJO_MAS_IZQ(control) == -1) {
                    write.write((byte) ((char) huffmantree.ETIQUETA(control)));
                    control = huffmantree.RAIZ();
                }
            }
            write.close();

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Descomprimir.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Descomprimir.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
