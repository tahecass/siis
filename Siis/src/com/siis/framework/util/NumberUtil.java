/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.casewaresa.framework.util;

import java.text.NumberFormat;
import java.util.Locale;

/**
 *
 * @author Admin
 */

public class NumberUtil {

    private static NumberFormat nfPorcentaje;
    private static NumberFormat nfUS;
    private static NumberFormat nfGerman;
    private static NumberFormat nf;
    private static NumberFormat nf2;

    static {
        nfPorcentaje = NumberFormat.getPercentInstance();
        nfUS = NumberFormat.getInstance(Locale.US);

        nfGerman = NumberFormat.getInstance(Locale.GERMAN);
        nf = NumberFormat.getInstance(Locale.getDefault());
        nf2 = NumberFormat.getInstance(Locale.getDefault());

        nfUS.setMaximumFractionDigits(2);
        nfGerman.setMaximumFractionDigits(2);
        nf.setMaximumFractionDigits(2);
        nf2.setMaximumFractionDigits(2);
        nf2.setMinimumFractionDigits(2);
    }

    public static String porcentajeFormat(double d) {
        return nfPorcentaje.format(d);
    }

    public static String formatComaPunto(Number n) {
        return nfUS.format(n);
    }

    public static String formatPuntoComa(Number n) {
        return nfGerman.format(n);
    }

    public static String format(Number n) {
        return nf.format(n);
    }

    public static String format$(Number n) {
        return "$" + nf.format(n);
    }

    public static String format2$(Number n) {
        return "$" + nf2.format(n);
    }

    @SuppressWarnings("unused")
	public static String delCerosIzq(String s) {
        if (s == null) {
            return s;
        }

        String sAux = "";
        int index = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) != '0') {
                index = i;
                break;
            }
        }

        return s.substring(index);
    }

    public static Integer delCerosIzqInt(String s) {
        int i = 0;

        try {
            i = Integer.parseInt(s);
        } catch (NumberFormatException e) {
            i = 0;
        }

        return i;
    }

//    public static String toFormatReferencia(String ref) {
//        StringBuilder sb = new StringBuilder();
//
//        char[] crs = ref.toCharArray();
//        for (int i = 0; i < crs.length; i++) {
//            if (i == 2 || i == 4 || i == 8 || i == 12) {
//                sb.append("-");
//            }
//
//            sb.append(crs[i]);
//        }
//
//        return sb.toString();
//    }

//    public static String getNumeroFactura(Integer id) {
//        String aux = "00000" + id.toString();
//        String n = aux.substring(aux.length() - 6, aux.length());
//        return n;
//    }

    public static String getNumeroSinFormato(String numero) {
        StringBuilder newNumero = new StringBuilder();
        int indexFinal = numero.trim().indexOf(",");

        if (indexFinal > -1) {
            numero = numero.substring(0, indexFinal);
        }

        char[] caracteres = numero.trim().toCharArray();
        for (char c : caracteres) {
            if (Character.isDigit(c)) {
                newNumero.append(c);
            }
        }
        return newNumero.toString();
    }
}
