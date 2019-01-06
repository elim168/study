package com.elim.springboot.core;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class TestConfigurationProperties {

    @NotBlank(message="参数test.config.name不能为空")
    private String name;
    
    @Valid
    private Inner inner;
    
    @Data
    public static class Inner {
        
        @NotBlank(message="参数test.config.inner.username不能为空")
        private String username;
        private String password;
    }
    
}
