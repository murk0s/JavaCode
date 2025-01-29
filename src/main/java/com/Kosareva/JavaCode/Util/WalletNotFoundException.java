package com.Kosareva.JavaCode.Util;

public class WalletNotFoundException extends RuntimeException {
    public WalletNotFoundException(String message) {
        super(message);
    }
    public WalletNotFoundException() {
        super();
    }
}
