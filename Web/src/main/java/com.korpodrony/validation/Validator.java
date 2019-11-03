package com.korpodrony.validation;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import javax.enterprise.context.RequestScoped;

@RequestScoped
public class Validator {

    public static final int MAX_SHORT = 32_768;
    public static final int MAX_BYTE = 128;

    public boolean validateString(String str) {
        return StringUtils.isNotBlank(str);
    }

    public boolean validateInteger(String str) {
        return NumberUtils.isCreatable(str) && NumberUtils.isParsable(str) && NumberUtils.createInteger(str) > 0;
    }

    public boolean validateAcitvityTypeInteger(String str) {
        return NumberUtils.isCreatable(str)
                && NumberUtils.isParsable(str)
                && NumberUtils.createInteger(str) > 0
                && NumberUtils.createInteger(str) < 4;
    }

    public boolean validateShort(String str) {
        return NumberUtils.isCreatable(str) &&
                NumberUtils.isParsable(str) &&
                NumberUtils.createInteger(str) > 0
                && NumberUtils.createInteger(str) < MAX_SHORT;
    }

    public boolean validateByte(String str) {
        return NumberUtils.isCreatable(str) &&
                NumberUtils.isParsable(str) &&
                NumberUtils.createInteger(str) > 0
                && NumberUtils.createInteger(str) < MAX_BYTE;
    }
}
