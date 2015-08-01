package com.henningta.cryptoquips.data;

public class CryptoquipData {

	private String title;
	private String imgSrc;

	public CryptoquipData(String title, String imgSrc) {
		this.title = title;
		this.imgSrc = imgSrc;
	}

	public String getTitle() {
		return title;
	}

	public String getImgSrc() {
		return imgSrc;
	}

}
