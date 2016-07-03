import java.util.ArrayList;
import java.util.List;

public class CipherAlgorithm {

    private List<Character> alphabet = new ArrayList<Character>();
    private final static char[] PUNCTUATION = {'.', ',', ';', ':', '!', '?', '-', ' '};
    private int m;
    private final int k=3;

    CipherAlgorithm() {
        for (char c = 'а'; c <= 'я'; c++) {
            alphabet.add(c);
        }
        for (char c = '0'; c <= '9'; c++) {
            alphabet.add(c);
        }
        for (char c : PUNCTUATION) {
            alphabet.add(c);
        }
    }

    String encrypt(String text1) {
        String text = text1.toLowerCase();

        int n = alphabet.size();
        //m = m % n;
        //k = k % n;
        //if (gcd(n, m) != 1) {//проверка простоты n относительно m

        //    return "null";
        //}
        StringBuilder cryptogram = new StringBuilder();
        //блок шифрования данных


        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (!alphabet.contains(c)){
                return "[ERROR:] only russian alphabet can be encoded";
            }
                int index = alphabet.indexOf(c);
                index = (index + k) % n;
                cryptogram.append(alphabet.get(index));

        }
        return cryptogram.toString();
    }

    private static int gcd(int a, int b) {
        while (a > 0 && b > 0) {
            if (a > b) {
                a %= b;
            } else {
                b %= a;
            }
        }
        return a + b;
    }
}
