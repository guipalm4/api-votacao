package com.guiPalma.apivotacao.util;

public class CampoUtil {
	
	 private static final int[] pesoCPF = {11, 10, 9, 8, 7, 6, 5, 4, 3, 2};
	
	public  static String retiraMascaraCpf(String cpf) {
		return cpf.replaceAll("[.-]", "");
	}
	
	 public static boolean isCpf(String cpf) {
	      if ((cpf==null) || (cpf.length()!=11)) {
	    	  return false;
	      }
	      return true;
	 }

	

}
