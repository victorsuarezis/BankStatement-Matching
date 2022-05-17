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

import java.io.ByteArrayOutputStream;
import java.security.MessageDigest;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.logging.Level;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Callback;
import org.adempiere.webui.apps.AEnv;
import org.adempiere.webui.component.Messagebox;
import org.adempiere.webui.window.FDialog;
import org.compiere.model.I_AD_Client;
import org.compiere.model.I_C_TaxCategory;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Product_Category;
import org.compiere.model.MBPGroup;
import org.compiere.model.MBPartnerLocation;
import org.compiere.model.MClient;
import org.compiere.model.MCountry;
import org.compiere.model.MDocType;
import org.compiere.model.MInvoice;
import org.compiere.model.MOrg;
import org.compiere.model.MPriceList;
import org.compiere.model.MProduct;
import org.compiere.model.MProductCategory;
import org.compiere.model.MProject;
import org.compiere.model.MRefList;
import org.compiere.model.MTable;
import org.compiere.model.MTaxCategory;
import org.compiere.model.MUser;
import org.compiere.model.Query;
import org.compiere.model.X_AD_Reference;
import org.compiere.model.X_C_Country;
import org.compiere.util.CLogger;
import org.compiere.util.CacheMgt;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.w3c.dom.Node;
import org.zkoss.zk.ui.Component;

/**
 * @author NCG
 * Date: 18 Mar 2014
 * Description:	General utility classes
 * TODO: NCG: Tidy code; review methods to see if should be retained in this class or relocated to another.
 */
public class NTierUtils {

	/** Logger */
	private static CLogger log = CLogger.getCLogger(NTierUtils.class);
	
	/**
	 * Get a PO for the record ID and the specified table id
	 */
//	public static PO getPO(Properties ctx, String trxName, int tableID, int recordID) {
//		String tableName = MTable.getTableName(ctx, tableID);
//		if (tableName == null) {
//			throw new AdempiereException(String.format("No table found for ID - '%s'", tableID));
//		}
//		return getPO(ctx, trxName, tableName, recordID);
//	}
	
	/**
	 * Get a PO for the record ID and the specified table name
	 */
//	public static PO getPO(Properties ctx, String trxName, String tableName, int recordID) {
//		IModelFactory factory = new NTierModelFactory();
//		PO po = factory.getPO(tableName, recordID, trxName);
//		if (po==null) {
//			factory = new DefaultModelFactory();
//			po = factory.getPO(tableName, recordID, trxName);
//		}
//		return po;
//	}
	
	/**
	 */
	public static String getValueForID(Properties ctx, String trxName, String tableName, int Record_ID) {
    	StringBuilder sql = new StringBuilder();
		sql.append("select Value from " + tableName + 
				" where " +
				" AD_Client_ID = " + Env.getAD_Client_ID(ctx)+ " and " + 
				tableName + "_ID=").append(Record_ID);
		
		return DB.getSQLValueStringEx(null, sql.toString());
    }
	
	/**
	 * Return -1 if not found
	 */
	public static int getIDForValue(Properties ctx, String trxName, String tableName, String value) {
    	StringBuilder sql = new StringBuilder();
		sql.append(
				" select " + tableName + "_ID " +
				" from " + tableName + 
				" where " +
				" AD_Client_ID = " + Env.getAD_Client_ID(ctx)+ " and " + 
				" value = ? ");
		
		return DB.getSQLValueEx(trxName, sql.toString(), value);
    }
	
	/**
	 * Return -1 if not found
	 */
	public static int getIDForColumn(Properties ctx, String trxName, String tableName, String columnName, String columnValue) {
    	StringBuilder sql = new StringBuilder();
		sql.append(
				" select " + tableName + "_ID " +
				" from " + tableName + 
				" where " +
				" AD_Client_ID = " + Env.getAD_Client_ID(ctx)+ " and " + 
				" " +  columnName + " = ? ");
		//Only returns the first value
		return DB.getSQLValueEx(trxName, sql.toString(), columnValue);
    }
	
//	public static PO getPOForValue(Properties ctx, String trxName, String tableName, String value) {
//		int id = getIDForValue(ctx, trxName, tableName, value);
//		if (id<0) {
//			return null;
//		}
//		PO po = getPO(ctx, trxName, tableName, id);
//		return po;
//	}
	
//	public static PO getPOForColumn(Properties ctx, String trxName, String tableName, String columnName, String columnValue) {
//		int id = getIDForColumn(ctx, trxName, tableName, columnName, columnValue);
//		if (id<0) {
//			return null;
//		}
//		PO po = getPO(ctx, trxName, tableName, id);
//		return po;
//	}
	
	/**
	 * Download the attachment for the given table name and record id, with the specified name 
	 * Adapted From ReportStarter
	 */
//	public static File getAttachment(Properties ctx, String trxName, String tableName, int recordID, String nameOfAttachment) {
//		String msg = "";
//		MAttachment attachment;
//		File reportFile = null;
//		
//		PO po = getPO(ctx, trxName, tableName, recordID);
//		if (po==null) {
//			msg = String.format("Record not found for Table '%s', Record ID %s", tableName, recordID);
//			throw new AdempiereException(msg);
//		}
//		CacheMgt.get().reset(MAttachment.Table_Name);
//		CacheMgt.get().reset(po.get_TableName());
//		
//		attachment = po.getAttachment();
//		if (attachment == null) {
//			return null;
//		}
//		
//		MAttachmentEntry[] entries = attachment.getEntries();
//		for (MAttachmentEntry entry: entries) {
//			if ( entry != null ) {
//				if (nameOfAttachment.equals(entry.getName())) {
//					reportFile = getAttachmentEntryFile(entry);
//					return reportFile;
//				}
//			}
//			
//		}
//		return null;
//	}
//	
//	/**
//	 * Download the attachment for the given process and record id, with the specified name 
//	 */
//	public static File getAttachmentForProcess(Properties ctx, String trxName, int processID, 
//			String nameOfAttachment) {
//		return getAttachment(ctx, trxName, MProcess.Table_Name, processID, nameOfAttachment);
//	}
//	
//	/**
//	 * Download the attachment for the given server process and record id, with the specified name 
//	 */
//	public static File getAttachmentForProcess(Properties ctx, String trxName, SvrProcess process, 
//			String nameOfAttachment) {
//		String msg = "";
//		if (process.getProcessInfo()==null) {
//			msg = "No process info";
//			throw new AdempiereException(msg);
//		}
//		int processID = process.getProcessInfo().getAD_Process_ID();
//		return getAttachment(ctx, trxName, MProcess.Table_Name, processID, nameOfAttachment);
//	}
//	
//	/**
//	 * Copy the attachment entry to a temporary file
//	 */
//	public static File getAttachmentEntryFile(MAttachmentEntry entry) {
//		File reportFile = NTierFileUtils.getTempFileWithDateStamp(entry.getName(), ".pdf");
//		entry.getFile(reportFile);
//		return reportFile;
//	}
//	
	/**
	 * The document type id for the document name
	 * Return -1 = not found
	 */
	public static int getDocTypeIDForName(Properties ctx, String trxName, String name) {
		int id = getIDForColumn(ctx, trxName, MDocType.Table_Name, MDocType.COLUMNNAME_Name, name);
		return id;
	}
	
	/**
	 * Get specified reference by name
	 */
	public static X_AD_Reference getReference(String referenceName) {
		return new Query(
				Env.getCtx(), 
				X_AD_Reference.Table_Name, "name = ?", null)
			.setParameters(referenceName)
			.first();
	}
	
	/**
	 * Given the reference name, and the value, return the name from the list
	 */
	public static String getReferenceListItemName(String referenceName, String value) {
		X_AD_Reference ref = getReference(referenceName);
		if (ref == null) {
			return null;
		}
		MRefList item = new Query(
				Env.getCtx(), 
				MRefList.Table_Name, "ad_reference_id = ? and value = ? ", null)
			.setParameters(ref.get_ID(), value)
			.first();
		if (item == null) {
			return null;
		}
		return item.getName();
	}
	
	/**
	 * Given the reference name, and the list item name, return the value from the list
	 */
	public static String getReferenceListItemValue(String referenceName, String listItemName) {
		X_AD_Reference ref = getReference(referenceName);
		if (ref == null) {
			return null;
		}
		MRefList item = new Query(
				Env.getCtx(), 
				MRefList.Table_Name, "ad_reference_id = ? and name = ? ", null)
			.setParameters(ref.get_ID(), listItemName)
			.first();
		if (item == null) {
			return null;
		}
		return item.getValue();
	}
	
	/**
	 * @author ngordon
	 * Coalesce function
	 */
	public static <T> T coalesce(T ...items) {
	    for(T i : items) if(i != null) return i;
	    return null;
	}
	
	/**
	 * @author ngordon
	 * Show an info dialog, with callback
	 * Copied from FDialog
	 * Reference: Error that occurred after upgrading from Release 1.0 to Release 2.0, 
	 * 	"Components can be accessed only in event listeners"
	 */
	public static void showDialogInfo(int windowNo, Component comp, String message, Callback<Integer> callback)
    {
    	Properties ctx = Env.getCtx();
		String s = message.toString().replace("\n", "<br>");
		Messagebox.showDialog(s, AEnv.getDialogHeader(ctx, windowNo), Messagebox.OK, Messagebox.INFORMATION, callback);
		
		return;
    }
	
	/**
	 * @author ngordon
	 * Show dialog using the UI thread
	 * Reference: Error that occurred after upgrading from Release 1.0 to Release 2.0, 
	 * 	"Components can be accessed only in event listeners"
	 */
	public static void showDialogInfo(final String msg, final Callback<Integer> callback) {
		
			Runnable runnable = new Runnable() {
			
			@Override
			public void run() {
				showDialogInfo(0, null, msg, callback);
			}
		};
		AEnv.executeAsyncDesktopTask(runnable);
		
	}
	
	/**
	 * @author ngordon
	 * Reference: Error that occurred after upgrading from Release 1.0 to Release 2.0, 
	 * 	"Components can be accessed only in event listeners"
	 * @param msg
	 */
	public static void showDialogError(final String msg, final Callback<Integer> callback) {
		
			Runnable runnable = new Runnable() {
			
			@Override
			public void run() {
				FDialog.error(0, null, "Error", msg, callback);
			}
		};
		AEnv.executeAsyncDesktopTask(runnable);
		
	}
	
	/**
	 * @author ngordon
	 * Reference: Error that occurred after upgrading from Release 1.0 to Release 2.0, 
	 * 	"Components can be accessed only in event listeners"
	 */
	public static void showDialogAsk(final String msg, final Callback<Boolean> callback) {
		
			Runnable runnable = new Runnable() {
			
			@Override
			public void run() {
				FDialog.ask(0, null, msg, callback);
			}
		};
		AEnv.executeAsyncDesktopTask(runnable);
		
	}
	
	/**
	 * @author ngordon
	 * Reference: Error that occurred after upgrading from Release 1.0 to Release 2.0, 
	 * 	"Components can be accessed only in event listeners"
	 */
	public static boolean showDialogAsk1(final String msg) {
		final StringBuffer dialogResult = new StringBuffer();
		if ( dialogResult.length() > 0)
			dialogResult.delete(0, dialogResult.length());
		
		NTierUtils.showDialogAsk("You have entered the Date the Pink Copy Returned toDCAC.  This Permit will now be locked and no further changes can be made to it.\nDo you want to continue?", 
				new  Callback<Boolean>(){
			public void onCallback(Boolean result) {
				dialogResult.append(result.toString());
			}				
		});
		
		while (dialogResult.length() == 0) {
 			try {
 				Thread.sleep(200);
 			} catch (InterruptedException e) {}
 			
		}
		if (dialogResult.toString().equals("true")) {
			return true;
		}
		return false;
	}
	
	
	public static String getGUID() {
		return java.util.UUID.randomUUID().toString();
	}
	/**
	 * Get md5 hash
	 */
	public static String getMD5(String stringToMD5) {
		try {
			byte[] bytesOfMessage = stringToMD5.getBytes("UTF-8");
	
			MessageDigest md = MessageDigest.getInstance("MD5");
			//TODO: communicate with Sword and get the correct formula here
			byte[] theDigest = md.digest(bytesOfMessage);
			//String result = new String(theDigest, "UTF-8");
			String result;
			result = bytesToHexString(theDigest);
			return result;
			
		} catch (Exception e) {
			throw new AdempiereException(e);
		}
		
	}
	
	/**
	 * Convert byte array to hex string
	 * 	http://stackoverflow.com/questions/9655181/convert-from-byte-array-to-hex-string-in-java
	 */
	final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();
	public static String bytesToHexString(byte[] bytes) {
	    char[] hexChars = new char[bytes.length * 2];
	    for ( int j = 0; j < bytes.length; j++ ) {
	        int v = bytes[j] & 0xFF;
	        hexChars[j * 2] = hexArray[v >>> 4];
	        hexChars[j * 2 + 1] = hexArray[v & 0x0F];
	    }
	    return new String(hexChars);
	}
	
	/**
	 * @author NCG
	 */
	public static MUser getLoggedInUser() {
		int AD_User_ID = Env.getAD_User_ID(Env.getCtx());
		MUser retValue = MUser.get(Env.getCtx(), AD_User_ID);
		return retValue;
	}
	
	/**
	 * @author NCG
	 * Reset cache for given table
	 */
	public static void resetCacheForTable(String tableName) {
		CacheMgt.get().reset(tableName);
	}
	
	/**
	 * Lookup the country
	 * See also: getDefaultCountry..
	 */
	public static MCountry getCountry(Properties ctx, String trxName, String countryCode) {
		int AD_Client_ID = Env.getContextAsInt(ctx, "#AD_Client_ID");
		return new Query(ctx, X_C_Country.Table_Name, "countrycode=?", trxName)
			.setParameters(countryCode)
			.first();
	}
	
	/**
	 * Lookup the product by key
	 */
	public static MProduct getProduct(Properties ctx, String trxName, String value) {
		int AD_Client_ID = Env.getContextAsInt(ctx, "#AD_Client_ID");
		return new Query(ctx, I_M_Product.Table_Name, "ad_client_id=? and value=?", trxName)
			.setParameters(AD_Client_ID, value)
			.first();
	}
	
	/**
	 * Lookup the product category by key
	 */
	public static MProductCategory getProductCategory(Properties ctx, String trxName, String value) {
		if (value==null) {
			return null;
		}
		int AD_Client_ID = Env.getContextAsInt(ctx, "#AD_Client_ID");
		return new Query(ctx, I_M_Product_Category.Table_Name, "ad_client_id=? and value=?", trxName)
			.setParameters(AD_Client_ID, value)
			.first();
	}
	
	/**
	 * Lookup the default product category
	 */
	public static MProductCategory getDefaultProductCategory(Properties ctx, String trxName) {
		int AD_Client_ID = Env.getContextAsInt(ctx, "#AD_Client_ID");
		MProductCategory r = new Query(ctx, I_M_Product_Category.Table_Name, "ad_client_id=? and isdefault='Y'", trxName)
			.setParameters(AD_Client_ID)
			.setOnlyActiveRecords( true )
			.first();
		if ( r == null) {
			throw new AdempiereException("Default product category not found for client: " + AD_Client_ID);
		}
		return r;
	}
	
	
	/**
	 * Lookup the project
	 */
	public static MProject getProject(Properties ctx, String trxName, String value) {
		int AD_Client_ID = Env.getContextAsInt(ctx, "#AD_Client_ID");
		return new Query(ctx, MProject.Table_Name, "ad_client_id = ? and value=?", trxName)
			.setParameters(AD_Client_ID, value)
			.first();
	}
	
	/**
	 * Lookup the project by name
	 */
	public static int getProjectIDByName(Properties ctx, String trxName, String name) {
		if (name==null) {
			return 0;
		}
		int AD_Client_ID = Env.getContextAsInt(ctx, "#AD_Client_ID");
		MProject project =  new Query(ctx, MProject.Table_Name, "ad_client_id = ? and name=?", trxName)
			.setParameters(AD_Client_ID, name)
			.first();
		if (project==null) {
			return 0;
		}
		return project.get_ID();
	}

	/**
	 * Lookup the org by name
	 * NCG
	 */
	public static MOrg getOrgByName(Properties ctx, String trxName, String orgName) {
		int AD_Client_ID = Env.getContextAsInt(ctx, "#AD_Client_ID");
		return new Query(ctx, MOrg.Table_Name, "ad_client_id = ? and name=?", trxName)
			.setParameters(AD_Client_ID, orgName)
			.first();
	}

	/**
	 * Get the base currency
	 * NCG
	 */
	public static int getDefaultCurrencyID() {
		// The base currency
		return MClient.get(Env.getCtx()).getC_Currency_ID();
	}
	
	/**
	 * Get the default BP group
	 * NCG
	 */
	public static int getDefaultBPGroupID() {
		int ID = MBPGroup.getDefault( Env.getCtx() ).get_ID();
		return ID;
	}

	/**
	 * Lookup the country
	 * if no country sent, default to that of locale
	 * See also: getCountry
	 *  Adapted from: Megafreight code
	 */
	public static MCountry getDefaultCountryForLocale(Properties ctx, String trxName) {
		Locale locale = Locale.getDefault();
		
		MTable table = MTable.get(Env.getCtx(), X_C_Country.Table_ID);
		Query query = table.createQuery("IsActive='Y' AND CountryCode=?", null);
		query.setParameters(locale.getCountry());
		List<X_C_Country> entityTypes = query.list();
		
		return (MCountry)entityTypes.get(0);
	}
	
	/**
	 * Lookup the BPartner Location
	 */
	public static MBPartnerLocation getBPartnerLocation(Properties ctx, String trxName, int id) {
		int AD_Client_ID = Env.getContextAsInt(ctx, "#AD_Client_ID");
		return new Query(ctx, MBPartnerLocation.Table_Name, "ad_client_id = ? and C_BPartner_Location_ID=?", trxName)
			.setParameters(AD_Client_ID, id)
			.first();
	}
	
	/**
	 * Get the default price list
	 *  Null if not found
	 * NCG
	 */
	public static MPriceList getDefaultPriceList(Properties ctx, String trxName) {
		int AD_Client_ID = Env.getContextAsInt(ctx, "#AD_Client_ID");
		MPriceList po = new Query(ctx, MPriceList.Table_Name,
				"ad_client_id=? and IsDefault='Y'", trxName).setParameters(
				AD_Client_ID).first();
	    return po;
	}
	
	
	/**
	 * Print XML document to console
	 * NCG
	 */
	public static void XMLNodePrint(Node document) {
		System.out.println(XMLNodeAsString(document));
	}
	
	/**
	 * Print XML document to console
	 * NCG
	 */
	public static String XMLNodeAsString(Node document) {
		try {
			Source source = new DOMSource(document);
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			Result result = new StreamResult(bos);
			Transformer xformer = TransformerFactory.newInstance().newTransformer();
			xformer.setOutputProperty(OutputKeys.INDENT, "yes");
			xformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
			xformer.transform(source, result);
			return bos.toString();
		} catch (Exception e) {
			throw new AdempiereException(e);
		}
	}
	
	/**
	 * Lookup the default tax category 
	 */
	public static MTaxCategory getDefaultTaxCategory(Properties ctx, String trxName) {
		int AD_Client_ID = Env.getContextAsInt(ctx, "#AD_Client_ID");
		MTaxCategory r = new Query(ctx, I_C_TaxCategory.Table_Name, "ad_client_id=? and isdefault='Y'", trxName)
			.setParameters(AD_Client_ID)
			.setOnlyActiveRecords( true )
			.first();
		if ( r == null) {
			throw new AdempiereException("Default tax category not found for client: " + AD_Client_ID);
		}
		return r;
	}
	
	public static MClient getAD_ClientByValue(Properties ctx, String trxName, String value) 
		{
			final String whereClause = I_AD_Client.COLUMNNAME_Value + "= ? ";
			MClient result = new Query(ctx,I_AD_Client.Table_Name,whereClause,trxName)
									.setParameters(value)
									.firstOnly();
			
			if (log.isLoggable(Level.FINE)) log.fine("Client_Value =[" + value + "]");
			if(result != null)
			{
				if (log.isLoggable(Level.FINE)) log.fine("AD_Client_ID = " + result.getAD_Client_ID());
			}
			
			return result;
		}
	
	/**
	 * Does invoice exist? (unique keys are on specified fields)
	 * NCG: #1000394: Duplicate key error on F8 Dazzle Supplier Invoice
	 */
	public static boolean isInvoiceExists(int bpID, String documentNo) {
		//Gets the doc type id for SO or for PO
		MInvoice inv = new MInvoice(Env.getCtx(), 0, null);
		inv.setIsSOTrx(false);
		inv.setC_DocTypeTarget_ID();
		int docTypeID = inv.getC_DocTypeTarget_ID();
		
		List<MInvoice> list = new Query(Env.getCtx(), MInvoice.Table_Name, "c_bpartner_id = ? and documentno=? and (c_doctype_id=? or c_doctypetarget_id=?)", null)
			.setParameters( new Object[] { bpID , documentNo, docTypeID, docTypeID } )
			.setOnlyActiveRecords(false)
			.list();
		if (list.size()>0) return true;
		return false;
	}
	
	public static void main(String[] args) {
		// test for coalesce
		System.out.println(String.format("Coalesce test: %s", NTierUtils.coalesce( null, null, null, null, "a", "b")));
	}
	
}
