package org.fx3.emv.qr;

public class DefaultCrcCalculator implements CRCCalculator {

	private int initValue;
	private int polynomial;

	public DefaultCrcCalculator() {
		this.initValue = 0xFFFF;
		this.polynomial = 0x1021;
	}

	public DefaultCrcCalculator(int initValue, int polynomial) {
		this.initValue = initValue;
		this.polynomial = polynomial;
	}

	@Override
	public String computeCRC(String data) {
		byte[] bytes = data.getBytes();

		for (byte b : bytes) {
			for (int i = 0; i < 8; i++) {
				boolean bit = ((b >> (7 - i) & 1) == 1);
				boolean c15 = ((initValue >> 15 & 1) == 1);
				initValue <<= 1;
				if (c15 ^ bit)
					initValue ^= polynomial;
			}
		}

		initValue &= 0xffff;
		return Integer.toHexString(initValue).toUpperCase();
	}

	public int getCrc() {
		return initValue;
	}

	public int getPolynomial() {
		return polynomial;
	}

	public void setPolynomial(int polynomial) {
		this.polynomial = polynomial;
	}

	public void setInitValue(int initValue) {
		this.initValue = initValue;

	}

}
