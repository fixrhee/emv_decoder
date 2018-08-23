package org.fx3.emv.qr;

import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.google.common.collect.SortedSetMultimap;
import com.google.common.collect.TreeMultimap;

public class QRComposer {

	private Logger logger = Logger.getLogger(QRComposer.class);
	private SortedSetMultimap<Header, QRData> map;
	private CRCCalculator crc;

	public QRComposer() {
		map = TreeMultimap.create();
	}

	public void set(String id, String data) {
		Header header = new Header();
		header.setId(id);
		header.setRoot(true);
		QRData qrdata = new QRData(id, data);
		map.put(header, qrdata);
	}

	public void set(String id, String tag, String data) {
		Header header = new Header();
		header.setId(id);
		header.setRoot(false);
		QRData qrdata = new QRData(tag, data);
		map.put(header, qrdata);
	}

	public String doCompose() {
		StringBuffer sb = new StringBuffer();

		for (Header key : map.keySet()) {
			StringBuffer st = new StringBuffer();
			Collection<QRData> values = map.get(key);
			Iterator<QRData> it = values.iterator();

			sb.append(key.getId());
			if (key.isRoot()) {
				while (it.hasNext()) {
					QRData qrdata = it.next();
					sb.append(qrdata.getLength());
					sb.append(qrdata.getValue());
				}
			} else {
				while (it.hasNext()) {
					QRData qrdata = it.next();
					st.append(qrdata.getId());
					st.append(qrdata.getLength());
					st.append(qrdata.getValue());
				}
				sb.append(StringUtils.leftPad(String.valueOf(st.length()), 2, '0'));
				sb.append(st.toString());
			}
		}

		try {
			if (crc != null) {
				sb.append("6304");
				sb.append(getCrc().computeCRC(sb.toString()));
			}
		} catch (NullPointerException ex) {
			logger.debug("CRC Calculator Implementation Not Set");
		}
		return sb.toString();
	}

	public CRCCalculator getCrc() {
		return crc;
	}

	public void setCrc(CRCCalculator crc) {
		this.crc = crc;
	}

}
