package br.com.ancoraweb.excepetion;

public class SistemaExcepetion extends RuntimeException{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SistemaExcepetion(String message) {
        super(message);
    }

    public SistemaExcepetion(String message, Throwable cause) {
        super(message, cause);
    }
}
