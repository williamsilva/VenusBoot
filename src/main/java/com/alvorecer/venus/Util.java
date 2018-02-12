package com.alvorecer.venus;

import java.util.Calendar;
import java.util.Random;

import com.ibm.icu.text.SimpleDateFormat;

public class Util {
	
	public static String hora (){
		SimpleDateFormat format = new SimpleDateFormat("HH:mm");
		return format.format(Calendar.getInstance().getTime());
	}
	
	public static String protocol (){
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		String protocol = format.format(Calendar.getInstance().getTime());
		
		return protocol + setNumberAleatorio(4);
	}
	
	public static String Voucher() {
		return setCaracterAleatorio(3) + setNumberAleatorio(4);
	}

	private static String setCaracterAleatorio(int numeroCaracteres) {
		Random rand = new Random();
		char[] letras = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < numeroCaracteres; i++) {
			int ch = rand.nextInt(letras.length);
			sb.append(letras[ch]);
		}
		return sb.toString();
	}

	private static String setNumberAleatorio(int numeroCaracteres) {
		Random rand = new Random();
		char[] letras = "0123456789".toCharArray();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < numeroCaracteres; i++) {
			int ch = rand.nextInt(letras.length);
			sb.append(letras[ch]);
		}
		return sb.toString();
	}
}
