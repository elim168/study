/**
 * 
 */
package com.yeelim.learn.spring.security;

import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.model.Permission;

/**
 * @author Yeelim
 * @date 2014-8-7
 * @time 下午7:40:23 
 *
 */
public class BasePermissionExt extends BasePermission {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
    public static final Permission READ = new BasePermissionExt(1 << 0, 'R'); // 1
    public static final Permission WRITE = new BasePermissionExt(1 << 1, 'W'); // 2
    public static final Permission CREATE = new BasePermissionExt(1 << 2, 'C'); // 4
    public static final Permission DELETE = new BasePermissionExt(1 << 3, 'D'); // 8
    public static final Permission ADMINISTRATION = new BasePermissionExt(1 << 4, 'A'); // 16

	public BasePermissionExt(int mask) {
		super(mask);
	}
	
    public BasePermissionExt(int mask, char code) {
        super(mask, code);
    }
	
}
