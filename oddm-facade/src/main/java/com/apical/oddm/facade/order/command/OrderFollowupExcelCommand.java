package com.apical.oddm.facade.order.command;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

/** 
 * @description 类的描述：
 * @author  作者 : zhuzh
 * @date 创建时间：2016年10月31日 上午11:38:50 
 * @version 1.0 
 */

public class OrderFollowupExcelCommand implements Serializable {

	private static final long serialVersionUID = 1L;

	private String clientName; //所属业务
	
	private String seller; //所属业务
	
	private String merchandiser;//所属跟单
	
	private Set<Integer> state;//状态

	@Override
	public String toString() {
		final int maxLen = 10;
		return "OrderFollowupExcelCommand [clientName=" + clientName + ", seller=" + seller + ", merchandiser=" + merchandiser + ", state="
				+ (state != null ? toString(state, maxLen) : null) + "]";
	}

	private String toString(Collection<?> collection, int maxLen) {
		StringBuilder builder = new StringBuilder();
		builder.append("[");
		int i = 0;
		for (Iterator<?> iterator = collection.iterator(); iterator.hasNext() && i < maxLen; i++) {
			if (i > 0)
				builder.append(", ");
			builder.append(iterator.next());
		}
		builder.append("]");
		return builder.toString();
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getSeller() {
		return seller;
	}

	public void setSeller(String seller) {
		this.seller = seller;
	}

	public String getMerchandiser() {
		return merchandiser;
	}

	public void setMerchandiser(String merchandiser) {
		this.merchandiser = merchandiser;
	}

	public Set<Integer> getState() {
		return state;
	}

	public void setState(Set<Integer> state) {
		this.state = state;
	}

}
