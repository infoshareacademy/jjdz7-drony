package com.korpodrony.validation;

import org.apache.commons.lang3.StringUtils;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class StringValidator {

    public boolean validate(String str){
        return StringUtils.isNotBlank(str);
    }
}
