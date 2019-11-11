package com.korpodrony.validation;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import javax.enterprise.context.RequestScoped;

@RequestScoped
public class Validator {

    private static final int MAX_SHORT = 32_768;
    private static final int MAX_BYTE = 128;
    private static final int LOWER_LIMIT_OF_INDEX= 0;
    private static final int LOWER_LIMIT_OF_ACTIVITY_TYPE = 0;
    private static final int UPPER_LIMIT_OF_ACTIVITY_TYPE = 4;
    private static final int LOWER_LIMIT_OF_LENGTH_IN_QUARTERS = 0;


    public boolean validateString(String str) {
        return StringUtils.isNotBlank(str);
    }

    public boolean validateInteger(String str) {
        return NumberUtils.isCreatable(str) && NumberUtils.isParsable(str) && NumberUtils.createInteger(str) > 0;
    }

    public boolean validateActivityTypeInteger(String str) {
        return NumberUtils.isCreatable(str)
                && NumberUtils.isParsable(str)
                && NumberUtils.createInteger(str) > LOWER_LIMIT_OF_ACTIVITY_TYPE
                && NumberUtils.createInteger(str) < UPPER_LIMIT_OF_ACTIVITY_TYPE;
    }

    public boolean validateShort(String str) {
        return NumberUtils.isCreatable(str) &&
                NumberUtils.isParsable(str) &&
                NumberUtils.createInteger(str) > LOWER_LIMIT_OF_INDEX
                && NumberUtils.createInteger(str) < MAX_SHORT;
    }

    public boolean validateByte(String str) {
        return NumberUtils.isCreatable(str) &&
                NumberUtils.isParsable(str) &&
                NumberUtils.createInteger(str) > LOWER_LIMIT_OF_LENGTH_IN_QUARTERS
                && NumberUtils.createInteger(str) < MAX_BYTE;
    }
}
