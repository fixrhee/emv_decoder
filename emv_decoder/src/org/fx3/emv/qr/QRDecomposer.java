package org.fx3.emv.qr;

import com.google.common.collect.SortedSetMultimap;
import com.google.common.collect.TreeMultimap;

public class QRDecomposer {

	private String qrdata;
	private SortedSetMultimap<String, String> map;

	public QRDecomposer() {
		setMap(TreeMultimap.create());
	}

	public QRDecomposer(String data) {
		setMap(TreeMultimap.create());
		setQrdata(data);
	}

	public SortedSetMultimap<String, String> doDecompose() {
		int start = 0;
		int end = qrdata.length();

		while (end - start != 0) {
			int idDigit = 2;
			int lengthDigit = 2;
			String id = qrdata.substring(start, start + idDigit);
			int dataLength = Integer.valueOf(qrdata.substring(start + idDigit, start + idDigit + lengthDigit));
			String value = qrdata.substring(start + idDigit + lengthDigit, start + idDigit + lengthDigit + dataLength);
			map.put(id, value);
			start = start + idDigit + lengthDigit + dataLength;
		}
		return map;
	}

	public String getQrdata() {
		return qrdata;
	}

	public void setQrdata(String qrdata) {
		this.qrdata = qrdata;
	}

	public SortedSetMultimap<String, String> getMap() {
		return map;
	}

	public void setMap(SortedSetMultimap<String, String> map) {
		this.map = map;
	}

}
