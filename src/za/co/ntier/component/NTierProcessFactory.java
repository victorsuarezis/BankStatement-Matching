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

import org.adempiere.base.IProcessFactory;
import org.compiere.process.ProcessCall;

import za.co.ntier.process.NBSM_Proc_BankStatementMatcher;
import za.co.ntier.process.NBSM_Proc_CreateMatcher;

/**
 * (c) 2015 nTier Software Services
 * @author Neil Gordon
 * Date: 13 Feb 2015
 * Description:	
 */
public class NTierProcessFactory implements IProcessFactory {

	@Override
	public ProcessCall newProcessInstance(String className) {
		if ("org.compiere.process.BankStatementMatcher".equals(className))
			return new NBSM_Proc_BankStatementMatcher();
		if ("za.co.ntier.process.NBSM_Proc_CreateMatcher".equals(className))
			return new NBSM_Proc_CreateMatcher();
		return null;
	}
}
