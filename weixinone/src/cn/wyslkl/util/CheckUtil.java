package cn.wyslkl.util;
import java.security.MessageDigest;
import java.util.Arrays;

/**
 * @author wys
 *
 */
public class CheckUtil {
	private static final String token="wyslkl123456";
	public static boolean checkSignature(String signature,String timestamp,String nonce){
		String[] arr=new String[]{token,timestamp,nonce};
		//ÅÅÐò
		Arrays.sort(arr);
		
		//Éú³É×Ö·û´®
		StringBuffer content=new StringBuffer();
		for(int i=0;i<arr.length;i++){
			content.append(arr[i]);
		}
		//sha1¼ÓÃÜ
		String temp=SHA1(content.toString());
		
		return temp.equalsIgnoreCase(signature);
	}
	private static String SHA1(String str) {

		if (str == null || str.length() == 0) {

		return null;

		}

		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

		MessageDigest mdTemp;

		try {

		mdTemp = MessageDigest.getInstance("SHA1");

		mdTemp.update(str.getBytes("UTF-8"));

		byte[] md = mdTemp.digest();

		int j = md.length;

		char buf[] = new char[j * 2];

		int k = 0;

		for (int i = 0; i < j; i++) {

		byte b0 = md[i];

		buf[k++] = hexDigits[b0 >>> 4 & 0xf];

		buf[k++] = hexDigits[b0 & 0xf];

		}

		return new String(buf);

		} catch (Exception e) {

		return null;

		}

		}



}
