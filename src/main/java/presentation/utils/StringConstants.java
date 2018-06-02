package presentation.utils;

public enum StringConstants {
	TITLE_APPLICATION ("DocManager"),
	TITLE_PDF_VIEWER_SCREEN ("Certidão"),
	TITLE_CREATE_PROCESS_SCREEN ("Novo Processo / Ofício"),
	TITLE_EDIT_PROCESS_SCREEN ("Editar Processo"),
	TITLE_SEARCH_SCREEN ("Buscar Processos / Ofícios"),
	TITLE_STATISTICS_SCREEN ("Gráficos Administrativos"),
	TITLE_DELETE_DIALOG ("Autorização"),
	
	ERROR_PASSWORD ("Usuário ou Senha Incorretos!");
	
	private String text;

	private StringConstants(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}
}
