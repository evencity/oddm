package com.apical.oddm.web.pageModel.base;

import java.util.ArrayList;
import java.util.List;

public class Grid<T> implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long total = 0l;
	private List<T> rows = new ArrayList<T>();

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}

}
