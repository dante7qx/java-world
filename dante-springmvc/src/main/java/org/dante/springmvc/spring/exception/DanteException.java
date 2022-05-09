package org.dante.springmvc.spring.exception;

public class DanteException extends Exception {

	private static final long serialVersionUID = 7907006615225401513L;
	
	public DanteException(String msg) {
		super(msg);
	}

	public DanteException(String message, Throwable cause) {
        super(message, cause);
    }
}
