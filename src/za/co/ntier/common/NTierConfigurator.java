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

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MSysConfig;
import org.compiere.model.Query;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Env;

/**
 * (c) 2015 nTier Software Services
 * @author Neil Gordon
 * Date: 24 Feb 2015
 * Description:	Manage configurator settings
 */
public class NTierConfigurator {

	/** Logger */
	private static CLogger log = CLogger.getCLogger(NTierConfigurator.class);
	/**
	 * 	Get specified MSysConfig record
	 *	@param ctx context 
	 *	@param ID
	 *	@return record
	 */
	public static MSysConfig getConfigurator(Properties ctx, String trxName, String name) {
		int AD_Client_ID = Env.getContextAsInt(ctx, "#AD_Client_ID");
		MSysConfig po = new Query(ctx, MSysConfig.Table_Name, "ad_client_id=? and name=?", trxName)
				.setParameters(AD_Client_ID, name).setOnlyActiveRecords(true).first();
		return po;
	}

	/**
	 * Get the configurator setting
	 */
	public static String getConfiguratorSettingValue(String name, String defaultValue) {
		String result = MSysConfig.getValue(name, defaultValue );
		return result;
	}

	/**
	 * Get the configurator setting - specified Client, or System Client. 
	 * 	Note, Client will override setting in System if both exist.
	 */
	public static String getConfiguratorSettingValue(String name, String defaultValue, int AD_Client_ID) {
		String result = MSysConfig.getValue(name, defaultValue, AD_Client_ID );
		return result;
	}
	
	/**
	 * Does the specified setting exist?
	 */
	public static boolean isSettingExists(Properties ctx, String trxName, String name)  {
		return isSettingExists(ctx, trxName, name, 0, 0);
	}
	
	/**
	 * Does the specified setting exist?
	 */
	public static boolean isSettingExists(Properties ctx, String trxName, String name, int AD_Client_ID, int AD_Org_ID)
	{
		//
		String sql = "SELECT Value FROM AD_SysConfig"
						+ " WHERE Name=? AND AD_Client_ID IN (0, ?) AND AD_Org_ID IN (0, ?) " //AND IsActive='Y'
						+ " ORDER BY AD_Client_ID DESC, AD_Org_ID DESC";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setString(1, name);
			pstmt.setInt(2, AD_Client_ID);
			pstmt.setInt(3, AD_Org_ID);
			rs = pstmt.executeQuery();
			if (rs.next())
				return true;
		}
		catch (SQLException e)
		{
			log.log(Level.SEVERE, "isSettingExists", e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		
		return false;
	}
	
	/**
	 * Create the given configurator setting
	 */
	public static void createConfiguratorSetting(Properties ctx, String trxName, boolean isSystem, String name, String value, String description) {
		
		String confLevel;
		Properties ctx1;
		ctx1 = (Properties)ctx.clone();
		if ( isSystem ) {
			if (isSettingExists(ctx, trxName, name)) 
				return;
			ctx1.setProperty(Env.AD_CLIENT_ID, "0");
			confLevel = MSysConfig.CONFIGURATIONLEVEL_System;
		} else {
			if (isSettingExists(ctx, trxName, name, Env.getAD_Client_ID(ctx), 0)) 
				return;
			confLevel = MSysConfig.CONFIGURATIONLEVEL_Client;
		}
		
		ctx1.setProperty(Env.AD_ORG_ID, "0");
		MSysConfig conf = new MSysConfig(ctx1, 0, null);
		conf.setConfigurationLevel(confLevel);
		conf.setName(name);
		conf.setDescription(description);
		conf.setValue(value);
		conf.saveEx();
		//CacheMgt.get().reset(MSysConfig.Table_Name);
	}
	
	public static MSysConfig newOrGet(Properties ctx, String trxName, boolean isSystem, String name, String defaultValue, String description) {
		NTierUtils.resetCacheForTable( MSysConfig.Table_Name );
		MSysConfig config;
		Properties ctx1;
		createConfiguratorSetting(ctx, trxName, isSystem, name, defaultValue, description);
		ctx1 = (Properties)ctx.clone();
		if (isSystem) {
			ctx1.setProperty(Env.AD_CLIENT_ID, "0");
		} else {
		}
		config = getConfigurator(ctx1, trxName, name);
		if ( config==null ) {
			throw new AdempiereException( String.format("Returned configurator setting is null (should've been created) - '%s'", name) );
		}
		return config;
	}
	
	public static String newOrGetString(Properties ctx, String trxName, boolean isSystem, String name, String defaultValue, String description) {
		MSysConfig config;
		config = newOrGet(ctx, trxName, isSystem, name, defaultValue, description);
		String r = config.get_ValueAsString("Value");
		return r;
	}
	
	public static boolean newOrGetBoolean(Properties ctx, String trxName, boolean isSystem, String name, String defaultValue, String description) {
		MSysConfig config;
		config = newOrGet(ctx, trxName, isSystem, name, defaultValue, description);
		boolean r = config.get_ValueAsBoolean("Value");
		return r;
	}
	
	public static int newOrGetInt(Properties ctx, String trxName, boolean isSystem, String name, String defaultValue, String description) {
		MSysConfig config;
		config = newOrGet(ctx, trxName, isSystem, name, defaultValue, description);
		int r = config.get_ValueAsInt("Value");
		return r;
	}

}
