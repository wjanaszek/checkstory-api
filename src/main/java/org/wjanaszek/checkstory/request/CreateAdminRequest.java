package org.wjanaszek.checkstory.request;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CreateAdminRequest extends CreateUserRequest {
    private Boolean isAdmin;
}
