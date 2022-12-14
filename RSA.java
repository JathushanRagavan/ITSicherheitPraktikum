import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

import javax.crypto.Cipher;

public class RSA {
	
	private PrivateKey privateKey;
	private PublicKey publicKey;
	private int keySize = 2048; //Kann 1024 oder 2048 sein
	private String charset = "UTF8";
	
	public RSA() {
		try {
		KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
		generator.initialize(keySize);
		KeyPair pair = generator.generateKeyPair();
		privateKey = pair.getPrivate();
		publicKey = pair.getPublic();
		} catch (Exception ignored) {
		}
	}

	public String encrypt(String message) throws Exception{
		byte[] messageToBytes = message.getBytes();
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		byte[] encryptedBytes = cipher.doFinal(messageToBytes);
		return encode(encryptedBytes);
	}

	private String encode(byte[] data) {
		return Base64.getEncoder().encodeToString(data);
	}
	
	public String decrypt(String encryptedMessage) throws Exception{
		byte[] encryptedBytes = decode(encryptedMessage);
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		byte[] decryptedMessage = cipher.doFinal(encryptedBytes);
		return new String(decryptedMessage, charset);
	}
	
	private byte[] decode(String data) {
		return Base64.getDecoder().decode(data);
	}
	
	public static void main(String[] args) {
		RSA rsa = new RSA();
		try {
			String encryptedMessage = rsa.encrypt("Dies ist eine Testnachricht, um die Ver- und Entschluesselung zu testen."); //	<--- Hier die Testnachricht eingeben
			String decryptedMessage = rsa.decrypt(encryptedMessage);
			
			System.out.println("Verschluesselte Nachricht = " + encryptedMessage);
			System.out.println("Entschluesselte Nachricht = " + decryptedMessage);
		} catch (Exception ignored) {}
	}
}