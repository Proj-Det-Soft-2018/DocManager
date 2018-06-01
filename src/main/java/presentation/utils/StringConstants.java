package presentation.utils;

public enum StringConstants {
	TITULO_APLICACAO ("DocManager"),
	TITLE_PDF_VIEWER ("Certidão");
	
	private String text;

	private StringConstants(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}
}
