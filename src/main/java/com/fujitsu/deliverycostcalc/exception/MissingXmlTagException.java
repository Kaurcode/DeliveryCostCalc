package com.fujitsu.deliverycostcalc.exception;

public class MissingXmlTagException extends Exception {
    public MissingXmlTagException(String tagName) {
        super(String.format("Missing XML tag: <%s>", tagName));
    }
}
