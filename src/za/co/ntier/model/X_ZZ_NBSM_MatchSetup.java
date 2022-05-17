/******************************************************************************
 * Product: iDempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2012 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software, you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY, without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program, if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
/** Generated Model - DO NOT CHANGE */
package za.co.ntier.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.model.I_Persistent;
import org.compiere.model.MTable;
import org.compiere.model.PO;
import org.compiere.model.POInfo;


/** Generated Model for ZZ_NBSM_MatchSetup
 *  @author iDempiere (generated) 
 *  @version Release 2.0 - $Id$ */
public class X_ZZ_NBSM_MatchSetup extends PO implements I_ZZ_NBSM_MatchSetup, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20150701L;

    /** Standard Constructor */
    public X_ZZ_NBSM_MatchSetup (Properties ctx, int ZZ_NBSM_MatchSetup_ID, String trxName)
    {
      super (ctx, ZZ_NBSM_MatchSetup_ID, trxName);
      /** if (ZZ_NBSM_MatchSetup_ID == 0)
        {
			setC_BankAccount_ID (0);
// @C_BankAccount_ID@
			setLine (0);
// @SQL=SELECT COALESCE(MAX(Line),0)+10 AS DefaultValue FROM ZZ_NBSM_MatchSetup WHERE C_BankAccount_ID=@C_BankAccount_ID@
			setZZ_NBSM_MatchSetup_ID (0);
			setZZ_NBSM_MatchText (null);
// @Description@
        } */
    }

    /** Load Constructor */
    public X_ZZ_NBSM_MatchSetup (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 3 - Client - Org 
      */
    protected int get_AccessLevel()
    {
      return accessLevel.intValue();
    }

    /** Load Meta Data */
    protected POInfo initPO (Properties ctx)
    {
      POInfo poi = POInfo.getPOInfo (ctx, Table_ID, get_TrxName());
      return poi;
    }

    public String toString()
    {
      StringBuffer sb = new StringBuffer ("X_ZZ_NBSM_MatchSetup[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public org.compiere.model.I_C_BankAccount getC_BankAccount() throws RuntimeException
    {
		return (org.compiere.model.I_C_BankAccount)MTable.get(getCtx(), org.compiere.model.I_C_BankAccount.Table_Name)
			.getPO(getC_BankAccount_ID(), get_TrxName());	}

	/** Set Bank Account.
		@param C_BankAccount_ID 
		Account at the Bank
	  */
	public void setC_BankAccount_ID (int C_BankAccount_ID)
	{
		if (C_BankAccount_ID < 1) 
			set_Value (COLUMNNAME_C_BankAccount_ID, null);
		else 
			set_Value (COLUMNNAME_C_BankAccount_ID, Integer.valueOf(C_BankAccount_ID));
	}

	/** Get Bank Account.
		@return Account at the Bank
	  */
	public int getC_BankAccount_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BankAccount_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_BPartner getC_BPartner() throws RuntimeException
    {
		return (org.compiere.model.I_C_BPartner)MTable.get(getCtx(), org.compiere.model.I_C_BPartner.Table_Name)
			.getPO(getC_BPartner_ID(), get_TrxName());	}

	/** Set Business Partner .
		@param C_BPartner_ID 
		Identifies a Business Partner
	  */
	public void setC_BPartner_ID (int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
	}

	/** Get Business Partner .
		@return Identifies a Business Partner
	  */
	public int getC_BPartner_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_Charge getC_Charge() throws RuntimeException
    {
		return (org.compiere.model.I_C_Charge)MTable.get(getCtx(), org.compiere.model.I_C_Charge.Table_Name)
			.getPO(getC_Charge_ID(), get_TrxName());	}

	/** Set Charge.
		@param C_Charge_ID 
		Additional document charges
	  */
	public void setC_Charge_ID (int C_Charge_ID)
	{
		if (C_Charge_ID < 1) 
			set_Value (COLUMNNAME_C_Charge_ID, null);
		else 
			set_Value (COLUMNNAME_C_Charge_ID, Integer.valueOf(C_Charge_ID));
	}

	/** Get Charge.
		@return Additional document charges
	  */
	public int getC_Charge_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Charge_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Description.
		@param Description 
		Optional short description of the record
	  */
	public void setDescription (String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	/** Get Description.
		@return Optional short description of the record
	  */
	public String getDescription () 
	{
		return (String)get_Value(COLUMNNAME_Description);
	}

	/** Set Price includes Tax.
		@param IsTaxIncluded 
		Tax is included in the price 
	  */
	public void setIsTaxIncluded (boolean IsTaxIncluded)
	{
		set_Value (COLUMNNAME_IsTaxIncluded, Boolean.valueOf(IsTaxIncluded));
	}

	/** Get Price includes Tax.
		@return Tax is included in the price 
	  */
	public boolean isTaxIncluded () 
	{
		Object oo = get_Value(COLUMNNAME_IsTaxIncluded);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Line No.
		@param Line 
		Unique line for this document
	  */
	public void setLine (int Line)
	{
		set_Value (COLUMNNAME_Line, Integer.valueOf(Line));
	}

	/** Get Line No.
		@return Unique line for this document
	  */
	public int getLine () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Line);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Create payment = PA */
	public static final String ZZ_NBSM_MATCHACTION_CreatePayment = "PA";
	/** Match open payment by amount = MO */
	public static final String ZZ_NBSM_MATCHACTION_MatchOpenPaymentByAmount = "MO";
	/** Copy reference values = CO */
	public static final String ZZ_NBSM_MATCHACTION_CopyReferenceValues = "CO";
	/** --- Select the Match Action --- = -- */
	public static final String ZZ_NBSM_MATCHACTION____SelectTheMatchAction___ = "--";
	/** Set Match Action.
		@param ZZ_NBSM_MatchAction Match Action	  */
	public void setZZ_NBSM_MatchAction (String ZZ_NBSM_MatchAction)
	{

		set_Value (COLUMNNAME_ZZ_NBSM_MatchAction, ZZ_NBSM_MatchAction);
	}

	/** Get Match Action.
		@return Match Action	  */
	public String getZZ_NBSM_MatchAction () 
	{
		return (String)get_Value(COLUMNNAME_ZZ_NBSM_MatchAction);
	}

	/** Set nTier Bank Statement Matching Setup.
		@param ZZ_NBSM_MatchSetup_ID nTier Bank Statement Matching Setup	  */
	public void setZZ_NBSM_MatchSetup_ID (int ZZ_NBSM_MatchSetup_ID)
	{
		if (ZZ_NBSM_MatchSetup_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_ZZ_NBSM_MatchSetup_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_ZZ_NBSM_MatchSetup_ID, Integer.valueOf(ZZ_NBSM_MatchSetup_ID));
	}

	/** Get nTier Bank Statement Matching Setup.
		@return nTier Bank Statement Matching Setup	  */
	public int getZZ_NBSM_MatchSetup_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ZZ_NBSM_MatchSetup_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set ZZ_NBSM_MatchSetup_UU.
		@param ZZ_NBSM_MatchSetup_UU ZZ_NBSM_MatchSetup_UU	  */
	public void setZZ_NBSM_MatchSetup_UU (String ZZ_NBSM_MatchSetup_UU)
	{
		set_ValueNoCheck (COLUMNNAME_ZZ_NBSM_MatchSetup_UU, ZZ_NBSM_MatchSetup_UU);
	}

	/** Get ZZ_NBSM_MatchSetup_UU.
		@return ZZ_NBSM_MatchSetup_UU	  */
	public String getZZ_NBSM_MatchSetup_UU () 
	{
		return (String)get_Value(COLUMNNAME_ZZ_NBSM_MatchSetup_UU);
	}

	/** Set Text to match.
		@param ZZ_NBSM_MatchText Text to match	  */
	public void setZZ_NBSM_MatchText (String ZZ_NBSM_MatchText)
	{
		set_Value (COLUMNNAME_ZZ_NBSM_MatchText, ZZ_NBSM_MatchText);
	}

	/** Get Text to match.
		@return Text to match	  */
	public String getZZ_NBSM_MatchText () 
	{
		return (String)get_Value(COLUMNNAME_ZZ_NBSM_MatchText);
	}

	/** Payment = PA */
	public static final String ZZ_NBSM_PAYMENTTYPE_Payment = "PA";
	/** Receipt = RE */
	public static final String ZZ_NBSM_PAYMENTTYPE_Receipt = "RE";
	/** Set Payment Type.
		@param ZZ_NBSM_PaymentType Payment Type	  */
	public void setZZ_NBSM_PaymentType (String ZZ_NBSM_PaymentType)
	{

		set_Value (COLUMNNAME_ZZ_NBSM_PaymentType, ZZ_NBSM_PaymentType);
	}

	/** Get Payment Type.
		@return Payment Type	  */
	public String getZZ_NBSM_PaymentType () 
	{
		return (String)get_Value(COLUMNNAME_ZZ_NBSM_PaymentType);
	}
}