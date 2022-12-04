import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;

//Die Schlüssellänge (KEY_SIZE) kann 128, 192 oder 256 Bit sein
//Die Länge des authentication tags (T_LEN) kann 128, 120, 112, 104 oder 96 Bit sein, da nur diese Größen im GCM definiert sind

public class AES {
	SecretKey key;
	private int KEY_SIZE = 256;
	private int T_LEN = 128;
	private Cipher encryptionCipher;
	
	public void init() throws Exception{
		KeyGenerator gen = KeyGenerator.getInstance("AES");;
		gen.init(KEY_SIZE);
		key = gen.generateKey();
	}
	
	public String encrypt(String message) throws Exception{
		byte[] messageInBytes = message.getBytes();
		encryptionCipher = Cipher.getInstance("AES/GCM/NoPadding");
		encryptionCipher.init(Cipher.ENCRYPT_MODE, key);
		byte[] encryptedBytes = encryptionCipher.doFinal(messageInBytes);
		return encode(encryptedBytes);
	}

	public String decrypt(String encryptedMessage) throws Exception{
		byte[] messageInBytes = decode(encryptedMessage);
		Cipher decryptionCipher = Cipher.getInstance("AES/GCM/NoPadding");
		GCMParameterSpec spec = new GCMParameterSpec(T_LEN, encryptionCipher.getIV());
		decryptionCipher.init(Cipher.DECRYPT_MODE, key, spec);
		byte[] decryptedBytes = decryptionCipher.doFinal(messageInBytes);
		return new String(decryptedBytes);
	}
	
	private byte[] decode(String data) {
		return Base64.getDecoder().decode(data);
	}

	private String encode(byte[] data){
		return Base64.getEncoder().encodeToString(data);
	}
	
	public static void main(String[] args) {
	try {
		AES aes = new AES();
		aes.init();
		String encryptedMessage = aes.encrypt("Dies ist eine Testnachricht, um die Ver- und Entschluesselung zu testen."); //	<--- Hier die Testnachricht eingeben
		String decryptedMessage = aes.decrypt(encryptedMessage);
		
		System.out.println("Verschluesselte Nachricht = " + encryptedMessage);
		System.out.println("Entschluesselte Nachricht = " + decryptedMessage);
		
	} catch (Exception ignored) {}
	}
}