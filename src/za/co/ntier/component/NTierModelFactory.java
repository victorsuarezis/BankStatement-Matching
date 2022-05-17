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
package za.co.ntier.component;

import java.sql.ResultSet;

import org.adempiere.base.IModelFactory;
import org.compiere.model.PO;
import org.compiere.util.CLogger;
import org.compiere.util.Env;

import za.co.ntier.model.MMatchSetup;

/**
 * @author ngordon
 * Date: 15 Sep 2014
 */
public class NTierModelFactory implements IModelFactory {

	/** Logger */
	private static CLogger log = CLogger.getCLogger(NTierModelFactory.class);

	@Override
	public Class<?> getClass(String tableName) {
	
		//#1
		if ( MMatchSetup.Table_Name.equals(tableName))
			return MMatchSetup.class;
		
		return null;
	}

	@Override
	public PO getPO(String tableName, int Record_ID, String trxName) {
		
		//#1
		if ( MMatchSetup.Table_Name.equals(tableName))
			return new MMatchSetup(Env.getCtx(), Record_ID, trxName);
		
		return null;
		
	}

	@Override
	public PO getPO(String tableName, ResultSet rs, String trxName) {
		
		//#1
		if ( MMatchSetup.Table_Name.equals(tableName))
			return new MMatchSetup(Env.getCtx(), rs, trxName);
		
		return null;
	}

}
