package presentation.utils;

public enum StringConstants {
	TITULO_APLICACAO ("DocManager");
	
	private String text;

	private StringConstants(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}
}
