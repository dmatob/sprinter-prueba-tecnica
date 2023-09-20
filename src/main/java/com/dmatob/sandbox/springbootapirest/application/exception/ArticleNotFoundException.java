package com.dmatob.sandbox.springbootapirest.application.exception;

public class ArticleNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -318034345472033556L;

	public ArticleNotFoundException(final String message) {
        super(message);
    }
}