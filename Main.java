import java.util.Map;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        EncoderDecoder helper = new EncoderDecoder();
        // Hello World Example
        String encodedStr = helper.encode("HELLO WORLD");
        System.out.println(encodedStr);
        System.out.println(helper.decode(encodedStr));
    }
}

class EncoderDecoder {
    private final String REFERENCE_TABLE;
    private final int TABLE_SIZE;
    private final Map<Character, Integer> INDEX_MAP;

    public EncoderDecoder() {
        this.REFERENCE_TABLE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789()*+,-./";
        this.TABLE_SIZE = REFERENCE_TABLE.length();
        this.INDEX_MAP = new HashMap<>();
        for (int i = 0; i < TABLE_SIZE; i++) {
            INDEX_MAP.put(REFERENCE_TABLE.charAt(i), i);
        }
    }

    public String encode(String plainText) {
        if (plainText.isEmpty()) return plainText;
        int offset = (int)(Math.random() * REFERENCE_TABLE.length()); // randomly chose an index to select any char to use as offset
        char offsetChar = REFERENCE_TABLE.charAt(offset);

        StringBuilder encodedSB = new StringBuilder(plainText.length() + 1);
        encodedSB.append(offsetChar); // First char of encoded message is offset char

        for (int i = 0; i < plainText.length(); i++) {
            char c = plainText.charAt(i);
            int charIdx = INDEX_MAP.getOrDefault(c, -1);
            if (charIdx != -1) {
                charIdx = (charIdx - offset + TABLE_SIZE) % TABLE_SIZE;
                encodedSB.append(REFERENCE_TABLE.charAt(charIdx));
            } else {
                encodedSB.append(c); // Char that is not in table remains same eg. white space ' '
            }
        }

        return encodedSB.toString();
    }

    public String decode(String encodedText) {
        if (encodedText.isEmpty()) return encodedText;

        char offsetChar = encodedText.charAt(0); // First char of decoded message is offset char
        int offset = INDEX_MAP.getOrDefault(offsetChar, -1);
        if (offset == -1) throw new IllegalArgumentException("Error: Offset character not in the reference table.");

        StringBuilder decodedSB = new StringBuilder(encodedText.length() - 1);

        for (int i = 1; i < encodedText.length(); i++) {
            char c = encodedText.charAt(i);
            int charIdx = INDEX_MAP.getOrDefault(c, -1);
            if (charIdx != -1) {
                charIdx = (charIdx + offset) % TABLE_SIZE;
                decodedSB.append(REFERENCE_TABLE.charAt(charIdx));
            } else {
                decodedSB.append(c); // Char that is not in table remains same eg. white space ' '
            }
        }

        return decodedSB.toString();
    }
}