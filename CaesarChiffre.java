
public class CaesarChiffre {

	public static void main(String[] args) {
		int offset = 0; //Schluesselgroesse
		String encryptedMessage = "abcdefghijklmnopqrstuvwxyz ABCDEFGHIJKLMNOPQRSTUVWXYZ 0123456789";
		String decryptedMessage = encryptString(encryptedMessage, offset);
		
		System.out.println(encryptString(encryptedMessage, offset));
		System.out.println(decrypt(decryptedMessage, offset));
		
		//System.out.println((int)'z');
	}
	
	public static String encryptString(String data, int offset) {
		String returnString = "";
		for(int i = 0; i < data.length(); i++) {
			if(data.charAt(i) == 32) {
				returnString += encrypt(data.charAt(i), 0);
			} else {
				returnString += encrypt(data.charAt(i), offset);
			}
		}
		return returnString;
	}
	
	public static char encrypt(char c, int offset) {
		
		if((int)c+offset > 122) {
			return (char)((int)c+offset-26);
		} else {
			return (char)((int)c+offset);
		}
	}
	
	public static String decrypt(String s, int offset) {
		String returnString = "";
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			
			if ((int) c + offset < 94 && (int) c != 32) {
				returnString += (char) ((int) c - offset);
			} else if ((int) c == 32) {
				returnString += (char) ((int) c);	
			} else if ((int) c + offset == 99 || (int) c + offset == 100) {
				returnString += (char)((int) c - offset + 26);
			} else {
				returnString += (char) ((int) c - offset);
				}
			}
		return returnString;
	}
}
