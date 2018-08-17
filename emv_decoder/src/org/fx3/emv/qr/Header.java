package org.fx3.emv.qr;

import java.io.Serializable;

public class Header implements Serializable, Comparable<Header> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8892806955692419095L;
	private String id;
	private boolean root;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean isRoot() {
		return root;
	}

	public void setRoot(boolean root) {
		this.root = root;
	}

	@Override
	public int compareTo(Header o) {
		return id.compareTo(o.getId());
	}

}
