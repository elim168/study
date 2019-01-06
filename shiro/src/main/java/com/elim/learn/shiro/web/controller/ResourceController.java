/**
 * 
 */
package com.elim.learn.shiro.web.controller;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresGuest;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.authz.annotation.RequiresUser;

/**
 * @author Elim
 * 2017年12月24日
 */
public class ResourceController {

    @RequiresUser
    public void requiresUser() {
        
    }
    
    @RequiresAuthentication
    public void requiresAuthentication() {
        
    }
    
    @RequiresGuest
    public void requiresGuest() {
        
    }
    
@RequiresRoles("#{#role}")
public void requiresRoles(String role) {
    
}

@RequiresPermissions("#{#permission}")
public void requiresPermissions(String permission) {
    
}
    
}
