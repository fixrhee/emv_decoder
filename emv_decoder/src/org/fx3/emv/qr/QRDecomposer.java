package org.fx3.emv.qr;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class QRDecomposer extends QRComposer {

	private String qrdata;
	private CRCCalculator crc;
	private Map<String, String> map;

	public QRDecomposer() {
		map = new TreeMap<String, String>();
	}

	public QRDecomposer(String data) {
		map = new TreeMap<String, String>();
		setQrdata(data);
	}

	public Map<String, String> doDecompose() {
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

	public boolean isValidCRC(String crc) {
		try {
			if (crc != null) {
				String recompile = this.crc.computeCRC(qrdata.substring(0, qrdata.length() - 4));
				if (recompile.equalsIgnoreCase(crc)) {
					return true;
				}
			}
			return false;
		} catch (NullPointerException ex) {
			return false;
		}
	}

	public String getTagValue(String idValue, String tagID) throws InvalidTagException {
		int start = 0;
		int end = idValue.length();
		Map<String, String> tagMap = new HashMap<String, String>();

		try {
			while (end - start != 0) {
				int idDigit = 2;
				int lengthDigit = 2;
				String id = idValue.substring(start, start + idDigit);
				int dataLength = Integer.valueOf(idValue.substring(start + idDigit, start + idDigit + lengthDigit));
				String value = idValue.substring(start + idDigit + lengthDigit,
						start + idDigit + lengthDigit + dataLength);
				tagMap.put(id, value);
				start = start + idDigit + lengthDigit + dataLength;
			}
			return tagMap.get(tagID);
		} catch (StringIndexOutOfBoundsException ex) {
			throw new InvalidTagException("Invalid Tag");
		}
	}

	public String getQrdata() {
		return qrdata;
	}

	public void setQrdata(String qrdata) {
		this.qrdata = qrdata;
	}

	public Map<String, String> getMap() {
		return map;
	}

	public void setMap(Map<String, String> map) {
		this.map = map;
	}

	public CRCCalculator getCrc() {
		return crc;
	}

	public void setCrc(CRCCalculator crc) {
		this.crc = crc;
	}

}
