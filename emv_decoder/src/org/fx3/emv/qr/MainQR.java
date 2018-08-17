package org.fx3.emv.qr;

import com.google.common.collect.SortedSetMultimap;

public class MainQR {

	public static void main(String[] args) {
		QRComposer bc = new QRComposer();
		bc.set("00", "01");
		bc.set("01", "12");
		bc.set("02", "00", "ABCDEF");
		bc.set("02", "01", "GHIJKL");
		bc.set("02", "02", "99987UYEAH");
		bc.set("62", "789012");
		System.out.println(bc.doCompose());

		QRDecomposer dc = new QRDecomposer(bc.doCompose());
		SortedSetMultimap<String, String> map = dc.doDecompose();
		System.out.println(map);
	
	}

}
