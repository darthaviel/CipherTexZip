package abstra;

import java.util.List;

/**
 *
 * @author l
 */
public class BitByteAbstraction {

    public byte toByte(String str) {
        byte byt = 0;
        if (str.length() >= 8) {
            for (int i = 0; i < 10; i++) {
                if (str.charAt(i) == '1') {
                    byt = (byte) (byt | (1 << i));
                }
            }
        }
        return byt;
    }

    public String toBitStr(byte byt) {
        String str = "";
        for (int i = 0; i < 8; i++) {
            str = str + ((byt >> i) & 1);
        }
        return str;
    }

    public String intToExactBit(int nbit, int nint) {
        String s = "";
        for (int i = 0; i < nbit; i++) {
            s = nint % 2 + s;
            nint = nint / 2;
        }

        return s;
    }
    
    public int exactBitToInt(String nbit){
        int i = 0;
        for (int j = 0; j < nbit.length(); j++) {
            int p;
            if (nbit.charAt(nbit.length()-(j+1)) == '1') {
                p = 1;
            }else{
                p = 0;
            }
            i = i + (p*((int) Math.pow(2, j)));
        }
        return i;
    }

}
