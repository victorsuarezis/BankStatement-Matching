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

/**
 * @author Neil Gordon 
 * 28 Jan 2014
 */
public class AResult {

	public static final int RESULT_OK = 0;
	public static final int RESULT_AN_EXCEPTION_OCCURRED = 1;
	public static final int RESULT_ERROR = 2;
	
	private int m_resultCode = 0;
	private String m_message = "";
	
	public AResult() {
	}
	
	public AResult(int m_resultCode, String m_message) {
		this.m_resultCode = m_resultCode;
		this.m_message = m_message;
	}

	public int getResultCode() {
		return m_resultCode;
	}

	public void setResultCode(int m_resultCode) {
		this.m_resultCode = m_resultCode;
	}

	public String getMessage() {
		return m_message;
	}
	
	/**
	 * Just set the message
	 */
	public void setMessage(String message) {
		this.m_message = message;
	}
	
}
