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

import java.util.Properties;
import java.util.logging.Level;

import org.compiere.model.MChat;
import org.compiere.model.MChatEntry;
import org.compiere.model.PO;
import org.compiere.model.Query;
import org.compiere.util.CLogger;
import org.compiere.util.Env;

/**
 * @author nTier Software Services ngordon7000
 * Date: 29 Jun 2015
 * Description:	Chat support
 */
public class NTierChatUtils {
	
	/** Logger */
	private static CLogger log = CLogger.getCLogger(NTierChatUtils.class);
	
	/**
	 */
	public static MChat getChat(Properties ctx, String trxName, 
			PO po) {
		final String whereClause = "AD_Client_ID=? and AD_Table_ID=? AND Record_ID=? ";
		MChat retValue = new Query(ctx, MChat.Table_Name, whereClause,
				trxName).setParameters(Env.getAD_Client_ID(ctx), po.get_Table_ID(), po.get_ID())
				.firstOnly();
		return retValue;
	}
	
	/**
	 * Add chat
	 */
	public static void addChat(Properties ctx, String trxName, 
			PO po, String comments) {
		try {
			String descr = "";
			descr = comments;
			descr = NTierStringUtils.left(descr, 255);
			MChat chat = getChat(ctx, trxName, po);
			if (chat == null) {
				chat = new MChat (ctx, po.get_Table_ID(), po.get_ID(), descr, trxName);
				chat.saveEx(trxName);
			}
			log.info(String.format("Chat - %s - '%s'", po.get_TableName(), descr));
			MChatEntry chatEntry = new MChatEntry(chat, descr);
			chatEntry.saveEx(trxName);
		} catch (Exception e) {
			log.log(Level.SEVERE, "^^^", e);
			log.info(String.format("^^^ Failed to save chat record"));
		}
	}
}
