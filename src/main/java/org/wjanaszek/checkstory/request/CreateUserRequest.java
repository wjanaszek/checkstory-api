package org.wjanaszek.checkstory.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CreateUserRequest {
    private String username;
    private String password;
    private String email;
}
