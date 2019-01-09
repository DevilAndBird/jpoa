package com.fh.util;

@SuppressWarnings("serial")
public class RulesCheckedException extends RuntimeException {
    
    public RulesCheckedException() {
        super();
    }

    public RulesCheckedException(String error) {
        super(error);
    }
}
