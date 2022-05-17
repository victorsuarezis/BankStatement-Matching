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
package za.co.ntier.model;

import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import org.compiere.model.Query;

import za.co.ntier.common.NTierStringUtils;

/**
 * @author nTier Software Services - Neil Gordon - http://www.ntier.co.za
 * Date: 25 Jun 2015
 */
public class MMatchSetup extends X_ZZ_NBSM_MatchSetup implements
		I_ZZ_NBSM_MatchSetup {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1706239969832026858L;

	public MMatchSetup(Properties ctx, int ZZ_NBSM_MatchSetup_ID,
			String trxName) {
		super(ctx, ZZ_NBSM_MatchSetup_ID, trxName);
	}

	public MMatchSetup(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}
	
	/**
	 * Find first match setup record which would match with the supplied line text
	 */
	public static MMatchSetup getMatchSetup(Properties ctx, String trxName, int C_BankAccount_ID, 
			String statementLineText) {
		if ( statementLineText == null ) {
			statementLineText = "";
		}
		statementLineText = statementLineText.trim().toUpperCase();
		// Get all the Match setup records
		List<MMatchSetup> list = new Query(ctx, 
				MMatchSetup.Table_Name,
				"C_BankAccount_ID=?", trxName)
				.setOrderBy( " Line ")
				.setParameters( C_BankAccount_ID )
				.list();

		for (MMatchSetup rec: list ) {
			String matchText = rec.getZZ_NBSM_MatchText();
			if ( matchText == null ) {
				matchText = "";
			}
			matchText = matchText.trim().toUpperCase();
			if ( statementLineText.contains( matchText )) {
				return rec;
			}
		}
		return null;
	}

	@Override
	protected boolean beforeSave(boolean newRecord) {
		boolean bpRequired = false;
		boolean ptRequired = false;
		
		if ( getZZ_NBSM_MatchText() != null ) {
			// Trim spaces
			setZZ_NBSM_MatchText(getZZ_NBSM_MatchText().trim());
		}
		if ( NTierStringUtils.isNullOrEmpty(getZZ_NBSM_MatchAction()) || 
				MMatchSetup.ZZ_NBSM_MATCHACTION____SelectTheMatchAction___.equals( 
				getZZ_NBSM_MatchAction() ) ) {
			log.saveError("Error", "Please specify the Match Action");
			return false;
		}
		if ( MMatchSetup.ZZ_NBSM_MATCHACTION_CopyReferenceValues.equals( 
				getZZ_NBSM_MatchAction() ) ) {
			if ( getC_BPartner_ID() == 0  && getC_Charge_ID() ==0 ) {
				log.saveError("Error", "Partner and/or charge is required");
				return false;
			}
		}
		if ( MMatchSetup.ZZ_NBSM_MATCHACTION_CreatePayment.equals( 
				getZZ_NBSM_MatchAction() ) ) {
			setC_Charge_ID(0);
			bpRequired = true;
			ptRequired = true;
		}
		if ( MMatchSetup.ZZ_NBSM_MATCHACTION_MatchOpenPaymentByAmount.equals( 
				getZZ_NBSM_MatchAction() ) ) {
			setC_Charge_ID(0);
			bpRequired = true;
			ptRequired = true;
		}
//		if ( getC_BPartner_ID() != 0  && getC_Charge_ID() !=0 ) {
//			log.saveError("Error", "Cannot specify both Business Partner and Charge");
//			return false;
//		}
		if ( bpRequired && getC_BPartner_ID() == 0 ) {
			log.saveError("Error", "Business Partner is required");
			return false;
		}
		if ( ptRequired && NTierStringUtils.isNullOrEmpty( getZZ_NBSM_PaymentType() ) ) {
			log.saveError("Error", "Payment type is required");
			return false;
		}
		return true;
	}
	
	/**
	 * toString method
	 */
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		try {
			sb.append("Match Setup: Line=").append(getLine());
			if ( ! NTierStringUtils.isNullOrEmpty( getZZ_NBSM_MatchText() )) {
				sb.append(", Match Text='").append( getZZ_NBSM_MatchText() ).append("'");
			}
			if ( ! NTierStringUtils.isNullOrEmpty( getDescription() )) {
				sb.append(", Description='").append( getDescription() ).append("'");
			}
//					.append("', ID=").append(get_ID());
			sb.append("] ");
			return sb.toString();
		} catch (Exception ex) {
			sb.append("] ");
			return sb.toString();
		}
	}

	
	
	
}
