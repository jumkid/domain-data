package com.jumkid.domain.exception;

public class DomainDataNotFoundException extends RuntimeException{

    private static final String ERROR = "Can not find domain data with Id: ";

    public DomainDataNotFoundException(Long id) { super(ERROR + id); }

}
