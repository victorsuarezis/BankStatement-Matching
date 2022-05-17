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
package za.co.ntier.nbsm;

import org.compiere.impexp.BankStatementMatchInfo;

/**
 *	Bank Statement Match Information.
 *	Returned by Bank Statement Matcher	
 *	
 *  @author Neil Gordon
 */
public class NBSM_BankStatementMatchInfo extends BankStatementMatchInfo
{
	/**
	 * 	Standard Constructor
	 */
	public NBSM_BankStatementMatchInfo()
	{
		super();
	}	//	NBSM_BankStatementMatchInfo


	private int m_C_Charge_ID = 0;
	private boolean m_isTaxIncluded = false;
	private String m_chatText = "";
	
	/* (non-Javadoc)
	 * @see org.compiere.impexp.BankStatementMatchInfo#isMatched()
	 */
	public boolean isMatched()
	{
		return getC_BPartner_ID() > 0 || getC_Payment_ID() > 0 || getC_Invoice_ID() > 0 || getC_Charge_ID() > 0;
	}	//	isValid


	public int getC_Charge_ID() {
		return m_C_Charge_ID;
	}


	public void setC_Charge_ID(int c_Charge_ID) {
		m_C_Charge_ID = c_Charge_ID;
	}


	public boolean isTaxIncluded() {
		return m_isTaxIncluded;
	}


	public void setIsTaxIncluded(boolean isTaxIncluded) {
		this.m_isTaxIncluded = isTaxIncluded;
	}


	public String getChatText() {
		return m_chatText;
	}


	public void setChatText(String chatText) {
		this.m_chatText = chatText;
	}

	public void addChatText(String textToAdd) {
		m_chatText = m_chatText.concat(textToAdd);
	}

}	//	NBSM_BankStatementMatchInfo
