package com.korpodrony.validation;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import javax.enterprise.context.RequestScoped;

@RequestScoped
public class NumberValidator {

    public boolean validateInteger(String str) {
        return NumberUtils.isCreatable(str) && NumberUtils.isParsable(str) && NumberUtils.createInteger(str) > 0;
    }

}
