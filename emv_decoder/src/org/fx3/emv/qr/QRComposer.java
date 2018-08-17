package org.fx3.emv.qr;

import java.util.Collection;
import java.util.Iterator;
import com.google.common.collect.SortedSetMultimap;
import com.google.common.collect.TreeMultimap;

public class QRComposer {

	private SortedSetMultimap<Header, QRData> map;

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
				sb.append(st.length());
				sb.append(st.toString());
			}
		}

		sb.append("6304");
		CrcCalculator crc = new CrcCalculator();
		sb.append(crc.computeCRC(sb.toString()));
		return sb.toString();
	}

}
