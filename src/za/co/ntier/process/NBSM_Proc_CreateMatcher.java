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

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.webui.apps.AEnv;
import org.adempiere.webui.event.DialogEvents;
import org.adempiere.webui.grid.WQuickEntry;
import org.adempiere.webui.session.SessionManager;
import org.compiere.model.MBankStatementLine;
import org.compiere.model.MWindow;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.Env;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;

import za.co.ntier.common.NTierStringUtils;
import za.co.ntier.model.MMatchSetup;
import za.co.ntier.nbsm.NBSM_Common;

/**
 */
public class NBSM_Proc_CreateMatcher extends SvrProcess
{
	
	private MBankStatementLine m_bsLine;
	
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
	 *  @throws Exception
	 */
	protected String doIt() throws java.lang.Exception
	{
		log.warning("NBSM_Proc_CreateMatcher - process");
		
		m_bsLine = new MBankStatementLine(getCtx(), getRecord_ID(), get_TrxName());
		if ( NTierStringUtils.isNullOrEmpty(m_bsLine.getDescription()) ) {
			throw new AdempiereException(String.format("Cannot create matcher for empty bank statement line description"));
		}
		
		// Work around for bug - Quick Entry not getting the correct default value
		Env.setContext(Env.getCtx(), "#C_BankAccount_ID", m_bsLine.getC_BankStatement().getC_BankAccount_ID());
		// Match text
		Env.setContext(Env.getCtx(), "#Description", m_bsLine.getDescription());
		if ( m_bsLine.getC_BPartner_ID() > 0 ) {
			Env.setContext(Env.getCtx(), "#C_BPartner_ID", m_bsLine.getC_BPartner_ID());
		} else {
			Env.setContext(Env.getCtx(), "#C_BPartner_ID", "");
		}
		if ( m_bsLine.getC_Charge_ID() > 0 ) {
			Env.setContext(Env.getCtx(), "#C_Charge_ID", m_bsLine.getC_Charge_ID());
		} else {
			Env.setContext(Env.getCtx(), "#C_Charge_ID", "");
		}
		if ( m_bsLine.get_ColumnIndex("IsTaxIncluded") >=0 ) {
			// Customized column
			Env.setContext(Env.getCtx(), "#IsTaxIncluded", m_bsLine.get_ValueAsBoolean("IsTaxIncluded"));
		}
		
		showQE();
		
		return "";

	}	//	doIt
	
	private void showQE() {
		
		// Get existing record by match text
		MMatchSetup matchSetup = MMatchSetup.getMatchSetup(
				getCtx(), get_TrxName(), 
				m_bsLine.getC_BankStatement().getC_BankAccount_ID(), 
				m_bsLine.getDescription());
		final int matchSetupID;
		if ( matchSetup == null ) {
			matchSetupID = 0;
		} else {
			matchSetupID = matchSetup.get_ID();
		}
		AEnv.executeAsyncDesktopTask(new Runnable() {
	    	@Override
			public void run() {
	    		String windowName = NBSM_Common.WINDOW_NAME_MATCHING_SETUP_QUICK_ENTRY;
	    		int windowID = MWindow.getWindow_ID(windowName);
	    		if ( windowID <= 0 ) {
	    			throw new AdempiereException(String.format("Could not find window '%s' required for Quick Entry - please apply 2pack", windowName));
	    		}
	    		final WQuickEntry vqe = new WQuickEntry (1, windowID);
	    		vqe.loadRecord ( matchSetupID );
	    		vqe.setVisible(true);
	    		vqe.addEventListener(DialogEvents.ON_WINDOW_CLOSE, new EventListener<Event>() {
	    			@Override
	    			public void onEvent(Event event) throws Exception {
	    				String strNull = null;
	    				Env.setContext(Env.getCtx(), "#C_BankAccount_ID", strNull);
	    				Env.setContext(Env.getCtx(), "#Description", strNull);
	    				Env.setContext(Env.getCtx(), "#C_BPartner_ID", strNull);
	    				Env.setContext(Env.getCtx(), "#C_Charge_ID", strNull);
	    				Env.setContext(Env.getCtx(), "#IsTaxIncluded", strNull);
	    			}
	    		});
	    	    SessionManager.getAppDesktop().showWindow(vqe);
	    	}
	    });
		
	}
}
