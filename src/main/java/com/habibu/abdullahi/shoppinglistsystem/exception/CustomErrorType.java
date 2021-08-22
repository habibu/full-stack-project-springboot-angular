package com.habibu.abdullahi.shoppinglistsystem.exception;

import com.habibu.abdullahi.shoppinglistsystem.dto.UserDTO;

public class CustomErrorType extends UserDTO{

    private String errorMessage;
    public CustomErrorType(final String errorMessage){
        this.errorMessage = errorMessage;
    }
    public String getErrorMessage(){
        return errorMessage;
    }
    
}
