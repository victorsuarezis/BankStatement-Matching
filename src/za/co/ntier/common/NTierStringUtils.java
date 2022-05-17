/******************************************************************************                                                                                                                                                                                                
 * Copyright (C) 2015 nTier Software Services http://www.ntier.co.za          *                                                                                                                                                                                                
 * This program is free software; you can redistribute it and/or modify it    *                                                                                                                                                                                                
 * under the terms version 2 of the GNU General Public License as published   *                                                                                                                                                                                                
 * by the Free Software Foundation. This program is distributed in the hope   *                                                                                                                                                                                                
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *                                                                                                                                                                                                
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *                                                                                                                                                                                                
 * See the GNU General Public License for more details.                       *                                                                                                                                                                                                
 * You should have received a copy of the GNU General Public License along    *                                                                                                                                                                                                
 * with this program; if not, write to the Free Software Foundation, Inc.,    *                                                                                                                                                                                                
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *                                                                                                                                                                                                
 *****************************************************************************/  
package za.co.ntier.common;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.NumberFormat;
import java.text.ParsePosition;

import org.adempiere.exceptions.AdempiereException;

/**
 * @author Neil Gordon 
 * 25 Feb 2014
 */
public class NTierStringUtils {

	public static boolean isNullOrEmpty(String s) {
		if ( (s==null) || "".equals(s.trim()) ) return true;
		return false;
	}

	public static String left(String s, int length) {
		if (s!=null) {
			if (s.length()<=length) {
				return s;
			}
			return s.substring(0, length);
		}
		return s;
	}
	
    public static String right(String input, int len) {
        return input.substring(input.length() - len);
    }

    public static String mid(String input, int index, int len) {
        return input.substring(index - 1, index + len - 1);
    }

    public static String mid(String input, int index) {
        return input.substring(index - 1);
    }
	
	/**
	 * NCG
	 * Return "" if s is null otherwise s
	 */
	public static String NVL(String s) {
		if ( (s==null)) return "";
		return s;
	}
	
	/**
	 * NCG
	 * Return ifNull if s is null otherwise s
	 */
	public static String NVL(String s, String ifNull) {
		if ( (s==null)) return ifNull;
		return s;
	}
	
	/**
	 * Stack trace as string
	 */
	public static String getStackTraceAsString(Exception e) {
		StringWriter sw = new StringWriter(); 
		e.printStackTrace(new PrintWriter(sw)); 
		String stacktrace = sw.toString();
		return stacktrace;
	}
	
	/**
	 * Check if a string contains numerics
	 * @param str
	 * @return true is it contains numerics otherwise false
	 * See: http://stackoverflow.com/questions/1102891/how-to-check-if-a-string-is-a-numeric-type-in-java
	 */
	public static boolean isNumeric(String str)
	{
	  if (isNullOrEmpty(str)) return false; //NCG added
	  NumberFormat formatter = NumberFormat.getInstance();
	  ParsePosition pos = new ParsePosition(0);
	  formatter.parse(str, pos);
	  return str.length() == pos.getIndex();
	}
	
	/**
	 * Convert from Y or N to a boolean
	 */
	public static boolean fromYesNo(String value) throws Exception {
		if ( ! ("N".equals(value.toUpperCase()) || "Y".equals(value.toUpperCase())) ) {
			throw new Exception(String.format("Must be Y or N, instead is - '%s'", value));
		}
		return "N".equals(value.toUpperCase()) ? false : true;
	}
	
	/**
	 * Convert from true or false to a boolean
	 */
	public static boolean fromTrueFalse(String value) {
		if ( ! ("FALSE".equals(value.toUpperCase()) || "TRUE".equals(value.toUpperCase())) ) {
			throw new AdempiereException(String.format("Must be true or false, instead is - '%s'", value));
		}
		return "FALSE".equals(value.toUpperCase()) ? false : true;
	}
	
	/**
	 * Convert from 1 or 0 to a boolean
	 */
	public static boolean fromOneZero(String value) throws Exception {
		if ( ! ("0".equals(value.toUpperCase()) || "1".equals(value.toUpperCase())) ) {
			throw new Exception(String.format("Must be 1 or 0, instead is - '%s'", value));
		}
		return "0".equals(value.toUpperCase()) ? false : true;
	}
	
	public static String toYN(boolean value) {
		return value ? "Y" : "N";
	}
	
	public static void main(String[] args) {
		System.out.println(String.format("%s", NTierStringUtils.left("abcdefghij", 3)));	//abc
	}
}
