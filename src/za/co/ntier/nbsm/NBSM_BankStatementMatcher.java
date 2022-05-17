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

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.impexp.BankStatementMatchInfo;
import org.compiere.impexp.BankStatementMatcherInterface;
import org.compiere.model.MBankStatementLine;
import org.compiere.model.MPayment;
import org.compiere.model.Query;
import org.compiere.model.X_I_BankStatement;
import org.compiere.process.DocAction;
import org.compiere.util.CLogger;
import org.compiere.util.Env;

import za.co.ntier.common.NTierChatUtils;
import za.co.ntier.common.NTierStringUtils;
import za.co.ntier.common.NTierUtils;
import za.co.ntier.model.MMatchSetup;

/**
 * @author Neil Gordon
 * 2015/06/25
 * TODO: NCG: Translations
 */
public class NBSM_BankStatementMatcher implements BankStatementMatcherInterface {

	/** Logger */
	private static CLogger log = CLogger
			.getCLogger(NBSM_BankStatementMatcher.class);
	
	private Properties m_ctx;
	private String m_trxName;
//	private Map<String, MMatchSetup> matchSetupMap = new HashMap<String, MMatchSetup>();
//	private int m_C_Invoice_ID;
	private int m_C_BPartner_ID;
	private int m_C_Payment_ID;
	private int m_C_Charge_ID;
	private NBSM_BankStatementMatchInfo info;
	private MMatchSetup m_matchSetup;
	private MPayment m_payment;
	private int m_AD_Org_ID;
	private Timestamp m_dateAcct;
	private BigDecimal m_stmtAmt;
	private int m_C_BankAccount_ID;
	private boolean m_isReceipt;
	private String m_bankStatementName = "";
	private int m_bankStatementLineNo = 0;
	
	public NBSM_BankStatementMatcher() {
	}
	
	/* (non-Javadoc)
	 * @see org.compiere.impexp.BankStatementMatcherInterface#findMatch(org.compiere.model.MBankStatementLine)
	 */
	@Override
	public BankStatementMatchInfo findMatch(MBankStatementLine bsl) {
		
		m_ctx = bsl.getCtx();
		m_trxName = bsl.get_TrxName();
		
		m_AD_Org_ID = bsl.getAD_Org_ID();
		m_dateAcct = bsl.getDateAcct();
		m_stmtAmt = bsl.getStmtAmt();
		m_C_BankAccount_ID = bsl.getC_BankStatement().getC_BankAccount_ID();
		m_bankStatementName = bsl.getC_BankStatement().getName();
		m_bankStatementLineNo = bsl.getLine();
		m_C_BPartner_ID = bsl.getC_BPartner_ID();
		m_C_Payment_ID = bsl.getC_Payment_ID();
		m_C_Charge_ID = bsl.getC_Charge_ID();
		m_payment = null;
		
		info = doMatch( bsl.getDescription() );
		
		// Return the match info
		return info;
	}

	/* (non-Javadoc)
	 * @see org.compiere.impexp.BankStatementMatcherInterface#findMatch(org.compiere.model.X_I_BankStatement)
	 */
	@Override
	public BankStatementMatchInfo findMatch(X_I_BankStatement ibs) {
//		m_ctx = ibs.getCtx();
//		m_trxName = ibs.get_TrxName();
//		
//		// Get the match info
//		BankStatementMatchInfo bsmInfo = new BankStatementMatchInfo();
//		bsmInfo = doMatch( ibs.getDescription(), ibs.getC_BPartner_ID() );
//		
		// Return the match info
		return null;
	}
	
	
	private NBSM_BankStatementMatchInfo doMatch(String textToMatch) {
		
		// Find the match record
		m_matchSetup = MMatchSetup.getMatchSetup(m_ctx, m_trxName, m_C_BankAccount_ID, textToMatch );
		if ( m_matchSetup == null ) {
			return null;
		}
		
		if (log.isLoggable(Level.INFO)) log.info("Found match for text: '" + textToMatch + "'");
		
		if ( m_C_BPartner_ID != 0 ) {
			if (log.isLoggable(Level.INFO)) log.info("nTierBankMatch: BP already set; will not do anything");
		}
		if ( m_C_Payment_ID != 0 ) {
			if (log.isLoggable(Level.INFO)) log.info("nTierBankMatch: payment already associated with b/s line; will not do anything");
		}
		if ( m_C_Charge_ID != 0 ) {
			if (log.isLoggable(Level.INFO)) log.info("nTierBankMatch: charge already associated with b/s line; will not do anything");
		}
		
		// Get the match info
		info = new NBSM_BankStatementMatchInfo();
		
		if ( MMatchSetup.ZZ_NBSM_PAYMENTTYPE_Payment.equals( 
				m_matchSetup.getZZ_NBSM_PaymentType() ) ) {
			m_isReceipt = false;
		} else {
			m_isReceipt = true;
		}
		
		if ( MMatchSetup.ZZ_NBSM_MATCHACTION_CopyReferenceValues.equals( 
				m_matchSetup.getZZ_NBSM_MatchAction() )) {
			info.addChatText("Copy values: ");
			// Copy - Partner
			if ( m_matchSetup.getC_BPartner_ID() != 0 ) {
				info.setC_BPartner_ID( m_matchSetup.getC_BPartner_ID() );
//				info.addChatText( "Business Partner" );
				info.addChatText( String.format("Business Partner - '%s'/'%s' ; ", 
						m_matchSetup.getC_BPartner().getValue(),
						m_matchSetup.getC_BPartner().getName()) );
			}
			// Copy - Charge
			if ( m_matchSetup.getC_Charge_ID() != 0 ) {
				info.setC_Charge_ID		( m_matchSetup.getC_Charge_ID() );
	//			info.setChargeAmt		( m_stmtAmt );
				info.setIsTaxIncluded	( m_matchSetup.isTaxIncluded() );
//				info.addChatText( "Charge" );
				info.addChatText( String.format("Charge - '%s' ; ", 
						m_matchSetup.getC_Charge().getName()) );
			}
		}
		if ( MMatchSetup.ZZ_NBSM_MATCHACTION_CreatePayment.equals( 
			m_matchSetup.getZZ_NBSM_MatchAction() )) {
//			info.addChatText("Create payment: ");
			createPayment( );
			if ( m_payment != null) {
				info.addChatText(
						String.format("Create payment: Payment created. Document no: '%s', Amount: %s", 
								m_payment.getDocumentNo(), 
								m_payment.getPayAmt() ));
			}
		}
		if ( MMatchSetup.ZZ_NBSM_MATCHACTION_MatchOpenPaymentByAmount.equals( 
				m_matchSetup.getZZ_NBSM_MatchAction() )) {
			
			int id = getMatchedPaymentID();
			if ( id>0 ) {
				info.addChatText("Match open payment by amount");
				info.setC_Payment_ID( id );
			}
		}
		
		info.addChatText( String.format(" %s", m_matchSetup.toString() ) );
		
		return info;
				
	}
	
	private void createPayment() {
		
		boolean isCreate = false;
		
		// Validate the amounts
		if ( m_stmtAmt.compareTo( Env.ZERO) < 0 ) {
			// Going out of the bank - -ve - so payment
			if ( MMatchSetup.ZZ_NBSM_PAYMENTTYPE_Payment.equals( 
					m_matchSetup.getZZ_NBSM_PaymentType()) ) {
				isCreate = true;
			} else {
				if (log.isLoggable(Level.INFO)) log.info( " Incorrect sign for AP payment ");
			}
		} else {
			// Coming in - +ve - so receipt
			if ( MMatchSetup.ZZ_NBSM_PAYMENTTYPE_Receipt.equals( 
					m_matchSetup.getZZ_NBSM_PaymentType()) ) {
				isCreate = true;
			} else {
				if (log.isLoggable(Level.INFO)) log.info( " Incorrect sign for AP payment ");
			}
		}
		
		if ( !isCreate ) {
			return;
		}
		
		// TODO: NCG: Review exception handling
		m_payment = new MPayment(m_ctx, 0,  m_trxName);
		
		m_payment.setAD_Org_ID( m_AD_Org_ID );
		/****************************************************************************
		 * Set fields
		 ****************************************************************************/
		m_payment.setC_BPartner_ID( m_matchSetup.getC_BPartner_ID() );
		if ( ! m_payment.getC_BPartner().isActive() ) {
			throw new AdempiereException( String.format("Business Partner is inactive" ) );
		}
		m_payment.setDateAcct					( m_dateAcct );
		m_payment.setDateTrx						( m_dateAcct );
		m_payment.setPayAmt						( m_stmtAmt.abs() );
		m_payment.setC_BankAccount_ID			( m_C_BankAccount_ID );
//			if ( ! m_po.getC_BankAccount().isActive() ) {
//				throw new AdempiereException( String.format("Bank Account is inactive" ) );
//			}
		
//			m_po.setDocumentNo( thedocumentno );
		
		m_payment.setIsReceipt( m_isReceipt );
		m_payment.setC_Currency_ID( NTierUtils.getDefaultCurrencyID() );
//			
		m_payment.saveEx();
		
		// Chat
		NTierChatUtils.addChat (m_payment.getCtx(), m_payment.get_TrxName(), 
				m_payment, 
				String.format(NBSM_Common.getChatPrefix() + "Payment created. ") + 
				String.format("Bank statement: '%s'/Line No: %s ", m_bankStatementName, m_bankStatementLineNo) +
				m_matchSetup.toString() );
		
		// Sanity check
		if ( m_payment.getAD_Org_ID() != m_AD_Org_ID ) {
			throw new AdempiereException( String.format(
					"The org has changed after saving the record. This is caused by the bank account " +
					" having a different org to the requested org. Please select a different bank account, " +
					" or correct the org on the selected bank account.") );
		}
		
		info.setC_Payment_ID( m_payment.get_ID() );
		
		/****************************************************************************
		 * Complete Document
		 ****************************************************************************/
		String docAction = DocAction.ACTION_Complete;
		m_payment.setDocAction( docAction );
		m_payment.processIt( docAction );
		m_payment.saveEx();
		
		if ( ! docAction.equals( m_payment.getDocStatus() )) {
			String msg = String.format("Error completing document: Invalid resulting status:- '%s' (expected '%s') Process Message:- '%s'", 
					m_payment.getDocStatus(), docAction, m_payment.getProcessMsg() );
			throw new AdempiereException( msg );
		}
		
		return;
		
	}
	
	/* 
	 * User has selected 'Match open payment by amount' as the Match Action 
	 * in a match setup record, and also selected a business partner. 
	 * A bank statement line has a reference which matches the match text 
	 * on the match setup record. 
	 * The system will look for an open payment to match the amount 
	 * and business partner, and associate with the current b/s line.
	 */
	private int getMatchedPaymentID() {
		if ( m_isReceipt ) {
			// AR Receipt - statement amt must be positive
			if ( m_stmtAmt.compareTo( Env.ZERO ) <= 0 ) {
				return -1;
			}
		} else {
			// AP Payment - statement amt must be negative
			if ( m_stmtAmt.compareTo( Env.ZERO ) >= 0 ) {
				return -1;
			}
		}
		String where = " c_bpartner_id = ? and isreceipt = ? and payamt = ? ";
		List<MPayment> list = new Query(
				m_ctx, 
				MPayment.Table_Name,
				where, 
				m_trxName)
			.setParameters( 
				m_matchSetup.getC_BPartner_ID(), 
				NTierStringUtils.toYN(m_isReceipt), 
				m_stmtAmt.abs() )
			.list();
		
		if ( list.size() <=0 ) {
			if (log.isLoggable(Level.INFO)) log.info("nTierBankMatch: no matching payments found");
			return -1;
		}
		
		// Find pmt
		int id = -1;
		for (MPayment pmt: list) {
			
			// Is this pmt already linked to a b/s line?
			if ( ! isPaymentAlreadyLinkedToBankStatementLine(pmt) ) {
				
				if ( id > -1 ) {
					// There is another payment with the same amount - therefore no match
					if (log.isLoggable(Level.INFO)) log.info("nTierBankMatch: There is more than 1 potential matching payment - therefore no match");
					return -1;
				}
				id = pmt.get_ID();
			} else {
				if (log.isLoggable(Level.INFO)) log.info(
						String.format("nTierBankMatch: Payment '%s' linked to another bank statement line - therefore no match",
								pmt));
			}
			
		}
		
		return id;
		
	}
	
	private boolean isPaymentAlreadyLinkedToBankStatementLine(MPayment pmt) {
		int AD_Client_ID = Env.getContextAsInt(m_ctx, "#AD_Client_ID");
		MBankStatementLine po = new Query(m_ctx, MBankStatementLine.Table_Name,
				"ad_client_id=? and isactive='Y' and c_payment_id=?", m_trxName).setParameters(
				AD_Client_ID, pmt.get_ID()).first();
		if ( po == null ) {
			return false;
		} else {
			return true;
		}
	}
	
}
