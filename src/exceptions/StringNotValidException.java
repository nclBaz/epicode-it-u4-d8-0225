package exceptions;

public class StringNotValidException extends Exception {
	// Se estendo Exception sto definendo un'eccezione di tipo CHECKED
	public StringNotValidException(String str) {
		super("La stringa " + str + " inserita non è valida!"); // Nel costruttore del padre inserisco quello che sarà impostato come messaggio d'errore
	}
}
