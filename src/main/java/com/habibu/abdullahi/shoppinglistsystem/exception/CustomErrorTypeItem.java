package com.habibu.abdullahi.shoppinglistsystem.exception;

import com.habibu.abdullahi.shoppinglistsystem.dto.ItemDTO;


public class CustomErrorTypeItem extends ItemDTO {
    
    private String errorMessage;

    public CustomErrorTypeItem(final String errorMessage){
        this.errorMessage = errorMessage;
    }
    public String getErrorMessage(){
        return errorMessage;
    }
}
