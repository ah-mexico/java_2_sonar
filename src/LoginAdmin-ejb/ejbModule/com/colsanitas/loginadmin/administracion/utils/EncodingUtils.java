package com.colsanitas.loginadmin.administracion.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;



/**
 * <p>Clase: EncodingUtils</p>
 * <p>Descripci&oacute;n: Clase para la codificaci&oacute;n/decodificaci&oacute;n de cadenas y
 * objetos, a trav&eacute;s del uso de varios algoritmos.<p/>
 * <p>Copyright: Copyright (c) 2007 </p>
 * @author avargasr
 * @version 1.0
 */
public final class EncodingUtils {

	private static Logger logger = Logger.getLogger(EncodingUtils.class);

	/** Tipos de algoritmos soportados por slappasswd */
	public enum HashEncrypt {
		CRYPT, MD5, SMD5, SHA, SSHA;
	}

	/**
	 * Obtiene el MD5 de la cadena indicada y lo codifica
	 * en Base64.
	 * @param textBytes byte[], cadena en texto claro
	 * @return String cadena codificada
	 */
    public static String encodeMD5(byte[] textBytes) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] hashBytes = digest.digest(textBytes);
            byte[] encodedBytes  = Base64.encodeBase64(hashBytes);
            String encodedString = new String(encodedBytes);
            return encodedString;

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Verifica si el MD5 del texto indicado corresponde con la cadena
     * MD5 a comparar.
     * @param textBytes texto claro
     * @param textHash representación MD5
     * @return boolean true si corresponde, false de lo contrario
     */
    public static boolean verifyHashMD5(byte[] textBytes, String textHash) {
        return encodeMD5(textBytes).equals(textHash);
    }

    /**
     * Codifica un texto en Base64.
     * @param text texto a codificar
     * @return String texto codificado
     */
    public static String encodeBase64(String text) {
    	byte[] textBytes = text.getBytes();
    	byte[] encodedBytes  = Base64.encodeBase64(textBytes);
        String encodedText = new String(encodedBytes);
        return encodedText.replaceAll("=", "oSi");
    }

    /**
     * Decodifica un texto en Base64.
     * @param encodedText texto codificado
     * @return String texto claro
     */
    public static String decodeBase64(String encodedText) {
    	encodedText = encodedText.replaceAll("oSi", "=");
    	String decodedText = new String(Base64.decodeBase64(encodedText.getBytes()));
    	return decodedText;
    }
   
    /**
     * Verifica si la contrase&ntilde;a indicada corresponde con la contrase&ntilde;a
     * LDAP codificada en MD5.
     * @param ldapPassword contrase&ntilde;a LDAP MD5 con el prefijo {MD5}
     * @param password contrase&ntilde;a a verificar en texto claro
     * @return boolean, true si son iguales, false de lo contrario.
     */
    public static boolean verifyLDAPPassword(String ldapPassword, String password) {
    	int index = ldapPassword.indexOf("}");
    	if (index > -1) {
    		ldapPassword = ldapPassword.substring(index+1);
    	}
    	String hashPassword = EncodingUtils.encodeMD5(password.getBytes());
    	if (ldapPassword.equals(hashPassword)) {
    		return true;
    	} else {
    		return false;
    	}
    }

	/**
	 * Metodo de utilidad que codifica una cadena de texto en MD5 en base 16, si no se
	 * puede encontrar este algoritmo entonces devuelve una cadena null
	 * 
	 * @param unencodedText
	 * @return la cadena cifrada en Md5 o null de no ser posible
	 */
	public static String encodeMD5_B16(String unencodedText) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] md5hash = new byte[40];
			md.update(unencodedText.getBytes("iso-8859-1"), 0, unencodedText
					.length());
			md5hash = md.digest();
			return convertirAHexagimal(md5hash);
		} catch (NoSuchAlgorithmException e) {
			System.out.println("No Such Algorithm Exception");
		} catch (UnsupportedEncodingException e) {
			System.out.println(
					"Unsupported Encoding Exception");
		}
		return null;
	}
	
	/**
	 * Convierte un arreglo de caracteres en hexagesimal
	 * 
	 * @param data
	 *            arreglo de bytes
	 * @return cadena de caracteres en hexagesimal
	 */
	private static String convertirAHexagimal(byte[] data) {
		StringBuilder buf = new StringBuilder();
		for (int i = 0; i < data.length; i++) {
			int halfbyte = (data[i] >>> 4) & 0x0F;
			int two_halfs = 0;
			do {
				if ((0 <= halfbyte) && (halfbyte <= 9))
					buf.append((char) ('0' + halfbyte));
				else
					buf.append((char) ('a' + (halfbyte - 10)));
				halfbyte = data[i] & 0x0F;
			} while (two_halfs++ < 1);
		}
		return buf.toString();
	}
	
	/**
	 * Reemplaza tildes y caracteres especiales.
	 * @param text String, texto a reemplazar.
	 * @return String texto reemplazado.
	 */
	public static String reemplazarCaracteres(String text) {
		text = text.replaceAll("Á", "A");
		text = text.replaceAll("É", "E");
		text = text.replaceAll("Í", "I");
		text = text.replaceAll("Ó", "O");
		text = text.replaceAll("Ú", "U");
		text = text.replaceAll("Ñ", "N");
		text = text.replaceAll("á", "a");
		text = text.replaceAll("é", "e");
		text = text.replaceAll("í", "i");
		text = text.replaceAll("ó", "o");
		text = text.replaceAll("ú", "u");
		text = text.replaceAll("ñ", "n");
		text = text.replaceAll("\\.", "");
		return text;
	}
	
	
	
	
}
