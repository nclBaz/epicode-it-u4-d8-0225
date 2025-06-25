package exceptions;

public class NumberLessThanZeroException extends RuntimeException {
	// Se estendo RuntimeException questa sarà un'eccezione di tipo UNCHECKED

	public NumberLessThanZeroException(int num) {
		super("Il numero inserito " + num + " è inferiore allo zero!");
	}

	public NumberLessThanZeroException(double num) {
		super("Il numero inserito " + num + " è inferiore allo zero!");
	}
}
