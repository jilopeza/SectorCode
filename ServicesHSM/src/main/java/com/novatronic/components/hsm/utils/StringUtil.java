/*
 * Novatronic S.A.C. Todos los derechos reservados
 * www.novatronic.com
 */
package com.novatronic.components.hsm.utils;

import java.util.Arrays;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * 
 * @author Josue Lopez
 * 
 */
public class StringUtil
{

    /**
     * Determina si una cadena es un numero
     * 
     * @param n
     * @return
     */
    public static boolean isNumber(String n)
    {
        boolean isnumber = true;
        int i = 0;

        for (i = 0; i < n.length(); i++)
        {
            if (!Character.isDigit(n.charAt(i)))
            {
                isnumber = false;
                break;
            }
        }

        return isnumber;
    }

    /**
     * centra una cadena en una longitud dada
     * 
     * @param s
     * @param l
     * @return
     */
    public static String padc(String s, int l)
    {
        String p;

		if (s.length() > l) {
			return s;
		}

        int i = l / 2;
        int d = l % 2;
        p = padl(s, i + d);
        p = padr(p, l);

        return p;
    }

    /**
     * Adiciona l espacios al lado izquierdo en una cadena de caracteres
     * 
     * @param s
     * @param l
     * @return
     */
    public static String padl(String s, int l)
    {
        String p = new String();

		if (s.length() > l) {
			p = s.substring(0, l);
		} else if (s.length() < l) {
			int i = l - s.length();
			int j = 0;

			for (j = 0; j < i; j++)
				p = " " + p;

			p = p + s;
		} else {
			p = s;
		}

        return p;
    }

    /**
     * Adiciona l espacios al lado derecho en una cadena de caracteres
     * 
     * @param s
     * @param l
     * @return
     */
    public static String padr(String s, int l)
    {
        String p = new String();

        if (s.length() > l)
            p = s.substring(0, l);
        else if (s.length() < l)
        {
            int i = l - s.length();
            int j = 0;

            for (j = 0; j < i; j++)
                p = p + " ";

            p = s + p;
        }
        else
            p = s;

        return p;
    }

    /**
     * Repite un numero de caracteres c en una longitud l
     * 
     * @param c
     * @param l
     * @return
     */
    public static String replicate(char c, int l)
    {
        char[] chrs = new char[l];
        Arrays.fill(chrs, c);
        String s = new String(chrs);
        return s;
    }
    
    
    public static String padLString(String value, char character, int length )
    {
        int i = length - value.length();
        String result= replicate(character, i) + value.trim();
        
        return result;
    }

    
    public static String padRString(String value, char character, int length )
    {
        int i = length - value.length();
        String result= value.trim() +  replicate(character, i);
        
        return result;
    }

    /**
     * Repite una cadena de caracteres string en una longitud l
     * 
     * @param string
     * @param l
     * @return
     */
    public static String replicate(String string, int l)
    {
        String strings = "";
        for (int i = 0; i < l; i++)
        {
            String s = new String(string);
            strings += s;
        }
        return strings;
    }

    /**
     * Sustrae un numero de caracteres del lado izquierdo
     * 
     * @param s
     * @param l
     * @return
     */
    public static String left(String s, int l)
    {
		if (s == null || s.isEmpty()) {
			return "";
		}

		if (l > s.length() - 1) {
			l = s.length();
		}
        
        return s.substring(0, l);
    }

    /**
     * Sustrae un numero de caracteres del lado derecho
     * 
     * @param s
     * @param l
     * @return
     */
    public static String right(String s, int l)
    {
        if ( s == null || s.isEmpty() ) {
            return "";
        }
        
        return s.substring(s.length() - l, s.length());
    }

    /**
     * Convierte el primer caracter entre palabras de una cadena en Mayuscula
     * 
     * @param s
     * @return
     * @throws java.io.IOException
     */
    public static String proper(String s)
    {
        java.io.StringReader in = new java.io.StringReader(s.toLowerCase());
        boolean preSpace = true;
        StringBuffer properCase = new StringBuffer();

        try
        {
            while (true)
            {
                int i = in.read();
                if (i == -1) {
                    break;
                }
                
                char c = (char) i;
                if (c == ' ' || c == '"' || c == '(' || c == '.' || c == '/' || c == '\\' || c == ',')
                {
                    properCase.append(c);
                    preSpace = true;
                }
                else
                {
                    if (preSpace) {                    	
                        properCase.append(Character.toUpperCase(c));
                    }
                    else {
                        properCase.append(c);
                    }
                    
                    preSpace = false;
                }
            }

            return properCase.toString();
        }
        catch (Exception e)
        {
            return null;
        }
    }

    /**
     * Convierte un map a string con valores igualados
     * 
     * @param map
     * @return
     */
    public static String mapToString(Map<String, Object> map)
    {
        String values = "";

        if (map != null && !map.isEmpty())
        {
            Object[] keys = (Object[]) map.keySet().toArray();
            for (int i = 0; i < keys.length; i++)
            {
                String item = (String) map.get(keys[i]);
                values = (i > 0 ? (values + "," + keys[i] + "=" + item) : (keys[i] + "=" + item));
            }
        }

        return values;
    }

    /**
     * Convierte un array de string a delimitado por comas
     * 
     * @param array
     * @return
     */
    public static String arrayToString(String array[])
    {
        String values = "";
        if (array != null && array.length > 0)
        {
            for (int i = 0; i < array.length; i++)
            {
                values += "," + array[i];
            }
        }
        return (values.length() > 0 ? values.substring(1) : values);
    }

    public static String replacePattern(String pattern, String value, String replace)
    {
        Pattern patternClean = Pattern.compile(pattern);
        return patternClean.matcher(value).replaceAll(replace);
    }

    public static String cleanPattern(String pattern, String value)
    {
        return replacePattern(pattern, value, "");
    }

    public static String trimString(String value)
    {
        return (value != null && value.trim().length() > 0 ? value.trim() : "");
    }
    
    public static boolean isEmpty(String str)
    {
      return str == null || str.length() == 0;
    }
    
    public static String substringWithDot (String value, int index )
    {
        if ( isEmpty(value) ) return "...";
        
        if ( value.length()-3 < index ) return value;
        
        return value.substring(0,index)+"...";
    }
}
