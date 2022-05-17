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
package za.co.ntier.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.compiere.model.MTable;
import org.compiere.util.KeyNamePair;

/** Generated Interface for ZZ_NBSM_MatchSetup
 *  @author iDempiere (generated) 
 *  @version Release 2.0
 */
public interface I_ZZ_NBSM_MatchSetup 
{

    /** TableName=ZZ_NBSM_MatchSetup */
    public static final String Table_Name = "ZZ_NBSM_MatchSetup";

    /** AD_Table_ID=1000008 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(3);

    /** Load Meta Data */

    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/** Get Client.
	  * Client/Tenant for this installation.
	  */
	public int getAD_Client_ID();

    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/** Set Organization.
	  * Organizational entity within client
	  */
	public void setAD_Org_ID (int AD_Org_ID);

	/** Get Organization.
	  * Organizational entity within client
	  */
	public int getAD_Org_ID();

    /** Column name C_BankAccount_ID */
    public static final String COLUMNNAME_C_BankAccount_ID = "C_BankAccount_ID";

	/** Set Bank Account.
	  * Account at the Bank
	  */
	public void setC_BankAccount_ID (int C_BankAccount_ID);

	/** Get Bank Account.
	  * Account at the Bank
	  */
	public int getC_BankAccount_ID();

	public org.compiere.model.I_C_BankAccount getC_BankAccount() throws RuntimeException;

    /** Column name C_BPartner_ID */
    public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/** Set Business Partner .
	  * Identifies a Business Partner
	  */
	public void setC_BPartner_ID (int C_BPartner_ID);

	/** Get Business Partner .
	  * Identifies a Business Partner
	  */
	public int getC_BPartner_ID();

	public org.compiere.model.I_C_BPartner getC_BPartner() throws RuntimeException;

    /** Column name C_Charge_ID */
    public static final String COLUMNNAME_C_Charge_ID = "C_Charge_ID";

	/** Set Charge.
	  * Additional document charges
	  */
	public void setC_Charge_ID (int C_Charge_ID);

	/** Get Charge.
	  * Additional document charges
	  */
	public int getC_Charge_ID();

	public org.compiere.model.I_C_Charge getC_Charge() throws RuntimeException;

    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/** Get Created.
	  * Date this record was created
	  */
	public Timestamp getCreated();

    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/** Get Created By.
	  * User who created this records
	  */
	public int getCreatedBy();

    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/** Set Description.
	  * Optional short description of the record
	  */
	public void setDescription (String Description);

	/** Get Description.
	  * Optional short description of the record
	  */
	public String getDescription();

    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/** Set Active.
	  * The record is active in the system
	  */
	public void setIsActive (boolean IsActive);

	/** Get Active.
	  * The record is active in the system
	  */
	public boolean isActive();

    /** Column name IsTaxIncluded */
    public static final String COLUMNNAME_IsTaxIncluded = "IsTaxIncluded";

	/** Set Price includes Tax.
	  * Tax is included in the price 
	  */
	public void setIsTaxIncluded (boolean IsTaxIncluded);

	/** Get Price includes Tax.
	  * Tax is included in the price 
	  */
	public boolean isTaxIncluded();

    /** Column name Line */
    public static final String COLUMNNAME_Line = "Line";

	/** Set Line No.
	  * Unique line for this document
	  */
	public void setLine (int Line);

	/** Get Line No.
	  * Unique line for this document
	  */
	public int getLine();

    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/** Get Updated.
	  * Date this record was updated
	  */
	public Timestamp getUpdated();

    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/** Get Updated By.
	  * User who updated this records
	  */
	public int getUpdatedBy();

    /** Column name ZZ_NBSM_MatchAction */
    public static final String COLUMNNAME_ZZ_NBSM_MatchAction = "ZZ_NBSM_MatchAction";

	/** Set Match Action	  */
	public void setZZ_NBSM_MatchAction (String ZZ_NBSM_MatchAction);

	/** Get Match Action	  */
	public String getZZ_NBSM_MatchAction();

    /** Column name ZZ_NBSM_MatchSetup_ID */
    public static final String COLUMNNAME_ZZ_NBSM_MatchSetup_ID = "ZZ_NBSM_MatchSetup_ID";

	/** Set nTier Bank Statement Matching Setup	  */
	public void setZZ_NBSM_MatchSetup_ID (int ZZ_NBSM_MatchSetup_ID);

	/** Get nTier Bank Statement Matching Setup	  */
	public int getZZ_NBSM_MatchSetup_ID();

    /** Column name ZZ_NBSM_MatchSetup_UU */
    public static final String COLUMNNAME_ZZ_NBSM_MatchSetup_UU = "ZZ_NBSM_MatchSetup_UU";

	/** Set ZZ_NBSM_MatchSetup_UU	  */
	public void setZZ_NBSM_MatchSetup_UU (String ZZ_NBSM_MatchSetup_UU);

	/** Get ZZ_NBSM_MatchSetup_UU	  */
	public String getZZ_NBSM_MatchSetup_UU();

    /** Column name ZZ_NBSM_MatchText */
    public static final String COLUMNNAME_ZZ_NBSM_MatchText = "ZZ_NBSM_MatchText";

	/** Set Text to match	  */
	public void setZZ_NBSM_MatchText (String ZZ_NBSM_MatchText);

	/** Get Text to match	  */
	public String getZZ_NBSM_MatchText();

    /** Column name ZZ_NBSM_PaymentType */
    public static final String COLUMNNAME_ZZ_NBSM_PaymentType = "ZZ_NBSM_PaymentType";

	/** Set Payment Type	  */
	public void setZZ_NBSM_PaymentType (String ZZ_NBSM_PaymentType);

	/** Get Payment Type	  */
	public String getZZ_NBSM_PaymentType();
}
