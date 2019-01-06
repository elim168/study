/**
 * 
 */
package com.elim.learn.spring.mvc.controller;

import java.beans.PropertyEditorSupport;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

/**
 * @author Elim
 * 2018年1月18日
 */
public class BaseController {

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        System.out.println(binder);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), false));  
        
        binder.registerCustomEditor(int.class, new PropertyEditorSupport() {  

    @Override  
    public String getAsText() {  
        // TODO Auto-generated method stub  
        return getValue().toString();  
    }  

    @Override  
    public void setAsText(String text) throws IllegalArgumentException {  
        // TODO Auto-generated method stub  
        System.out.println(text + "...........................................");  
        setValue(Integer.parseInt(text));  
    }  
      
});  
    }
    
}
