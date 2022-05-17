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

/**
 * @author Neil Gordon
 * 14 Feb 2014
 * Context and transaction support, and a result object
 */
public abstract class BaseSupport {

	protected String m_trxName;
	protected Properties m_ctx;
	protected AResult m_result = new AResult();
	
	public BaseSupport() {
		
	}

	public BaseSupport(Properties m_ctx, String m_trxName) {
		this.m_trxName = m_trxName;
		this.m_ctx = m_ctx;
	}

	public String get_TrxName() {
		return m_trxName;
	}

	public void set_TrxName(String m_trxName) {
		this.m_trxName = m_trxName;
	}

	public Properties getCtx() {
		return m_ctx;
	}

	public void setCtx(Properties m_ctx) {
		this.m_ctx = m_ctx;
	}
	
	public AResult process() throws Exception {
		return m_result;
	}
	
	
	public AResult getResult() {
		return m_result;
	}
}
