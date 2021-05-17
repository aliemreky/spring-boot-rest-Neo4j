package com.volaid.volaid.util;

import com.volaid.volaid.exception.ResponseApiError;
import org.neo4j.ogm.exception.core.NotFoundException;

import java.util.Optional;

public class Validation {

    public static void isValid(Optional<?> eOptional){

        if(!eOptional.isPresent()){
            throw new ResponseApiError(ServiceConstants.FAILURE_CODE ,ErrorMessagesConstants.INVALID_OBJECT);
        }
    }
}
