package com.becoder.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangePasswordReq {
	
       public String oldPassword;
         
       public String newPassword;
        
}
