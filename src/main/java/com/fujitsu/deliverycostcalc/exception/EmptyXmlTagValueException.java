package com.fujitsu.deliverycostcalc.exception;

public class EmptyXmlTagValueException extends Exception {
    public EmptyXmlTagValueException(String tagName) {
        super(String.format("Empty value in the XML tag: <%s>", tagName));
    }
}
