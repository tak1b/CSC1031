import java.util.Base64;

// Step 1: Define the EncryptionStrategy interface.
interface EncryptionStrategy {
    String encrypt(String text);
}

// Step 2: Implement Strategy Classes

// CaesarCipherEncryption: shifts letters using a configurable shift value.
class CaesarCipherEncryption implements EncryptionStrategy {
    private final int shift;
    
    public CaesarCipherEncryption(int shift) {
        this.shift = shift;
    }
    
    @Override
    public String encrypt(String text) {
        StringBuilder result = new StringBuilder();
        for (char ch : text.toCharArray()) {
            if (Character.isLetter(ch)) {
                char base = Character.isLowerCase(ch) ? 'a' : 'A';
                ch = (char) ((ch - base + shift) % 26 + base);
            }
            result.append(ch);
        }
        return result.toString();
    }
}

// Base64Encryption: encodes text using Base64.
class Base64Encryption implements EncryptionStrategy {
    @Override
    public String encrypt(String text) {
        return Base64.getEncoder().encodeToString(text.getBytes());
    }
}

// XOREncryption: encrypts by XOR-ing each character using a configurable key.
class XOREncryption implements EncryptionStrategy {
    private final char key;
    
    public XOREncryption(char key) {
        this.key = key;
    }
    
    @Override
    public String encrypt(String text) {
        StringBuilder result = new StringBuilder();
        for (char ch : text.toCharArray()) {
            result.append((char) (ch ^ key));
        }
        return result.toString();
    }
}

// ReverseStringEncryption: reverses the entire string.
class ReverseStringEncryption implements EncryptionStrategy {
    @Override
    public String encrypt(String text) {
        return new StringBuilder(text).reverse().toString();
    }
}

// DuplicateCharacterEncryption: duplicates each character in the string.
class DuplicateCharacterEncryption implements EncryptionStrategy {
    @Override
    public String encrypt(String text) {
        StringBuilder result = new StringBuilder();
        for (char ch : text.toCharArray()) {
            result.append(ch).append(ch);
        }
        return result.toString();
    }
}

// Step 4: Context Class - EncryptionService.
// This class holds a reference to an EncryptionStrategy and uses it to encrypt text.
class EncryptionService {
    private EncryptionStrategy strategy;
    
    public void setEncryptionStrategy(EncryptionStrategy strategy) {
        this.strategy = strategy;
    }
    
    public String encrypt(String text) {
        if (strategy == null) {
            throw new IllegalStateException("Encryption strategy not set.");
        }
        return strategy.encrypt(text);
    }
}
