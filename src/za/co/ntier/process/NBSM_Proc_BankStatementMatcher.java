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
package za.co.ntier.process;


import java.util.logging.Level;

import org.compiere.model.MBankStatement;
import org.compiere.model.MBankStatementLine;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.Env;

import za.co.ntier.common.NTierChatUtils;
import za.co.ntier.common.NTierStringUtils;
import za.co.ntier.nbsm.NBSM_BankStatementMatchInfo;
import za.co.ntier.nbsm.NBSM_BankStatementMatcher;
import za.co.ntier.nbsm.NBSM_Common;

/**
 *	Bank Statement Matching
 *	
 *  @author Jorg Janke
 *  @version $Id: BankStatementMatcher.java,v 1.3 2006/09/25 00:59:41 jjanke Exp $
 *  
 *  @author Neil Gordon
 *  Modified for NBSM
 */
public class NBSM_Proc_BankStatementMatcher extends SvrProcess
{
	/**
	 *  Prepare - e.g., get Parameters.
	 */
	protected void prepare()
	{
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;
			else
				log.log(Level.SEVERE, "Unknown Parameter: " + name);
		}
	}	//	prepare

	/**
	 *  Perform process.
	 *  @return Message 
	 *  @throws Exception if not successful
	 */
	protected String doIt() throws Exception
	{
		int Table_ID = getTable_ID();
		int Record_ID = getRecord_ID();
//		
//		if (Table_ID == X_I_BankStatement.Table_ID)
//			return match (new X_I_BankStatement(getCtx(), Record_ID, get_TrxName()));
		if (Table_ID == MBankStatement.Table_ID)
			return match (new MBankStatement(getCtx(), Record_ID, get_TrxName()));
		else if (Table_ID == MBankStatementLine.Table_ID)
			return match (new MBankStatementLine(getCtx(), Record_ID, get_TrxName()));
		
		return "??";
	}	//	doIt

	private String match (MBankStatementLine bsl)
	{
		NBSM_BankStatementMatcher matcher = new NBSM_BankStatementMatcher();
		NBSM_BankStatementMatchInfo info = null;
		info = (NBSM_BankStatementMatchInfo) matcher.findMatch(bsl);
		if (info != null && info.isMatched())
		{
			if (info.getC_Payment_ID() > 0)
				bsl.setC_Payment_ID(info.getC_Payment_ID());
			if (info.getC_Invoice_ID() > 0)
				bsl.setC_Invoice_ID(info.getC_Invoice_ID());
			if (info.getC_BPartner_ID() > 0)
				bsl.setC_BPartner_ID(info.getC_BPartner_ID());
			if (info.getC_Charge_ID() > 0) {
				bsl.setC_Charge_ID(info.getC_Charge_ID());
				bsl.setTrxAmt( Env.ZERO );
				bsl.setChargeAmt( bsl.getStmtAmt() );
				// nTier: Customized column: only set if exists
				if ( bsl.get_ColumnIndex("IsTaxIncluded") >= 0 ) {
					bsl.set_ValueOfColumn("IsTaxIncluded", info.isTaxIncluded());
				}
				
			}
			bsl.saveEx();
			
			if ( ! NTierStringUtils.isNullOrEmpty(info.getChatText()) ) {
				NTierChatUtils.addChat (bsl.getCtx(), bsl.get_TrxName(), 
						bsl, 
						String.format(NBSM_Common.getChatPrefix() + info.getChatText()) );
			}
			return "OK";
		}
		
		return "";
	}	//	match 
	
	/**
	 * 	Perform Match
	 *	@param bs bank statement
	 *	@return Message
	 */
	private String match (MBankStatement bs)
	{
		int count = 0;
		MBankStatementLine[] lines = bs.getLines(false);
		for (int i = 0; i < lines.length; i++)
		{
			if (lines[i].getC_Payment_ID() == 0)
			{
				try {
					match(lines[i]);
				} catch (Exception e) {
					log.log(Level.SEVERE, "", e);
				}
				count++;
			}
		}
		return String.valueOf(count);
	}	//	match 

}	//	BankStatementMatcher
