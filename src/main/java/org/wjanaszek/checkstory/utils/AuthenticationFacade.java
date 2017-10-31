package org.wjanaszek.checkstory.utils;

import org.springframework.security.core.Authentication;

public interface AuthenticationFacade {

    Authentication getAuthentication();
}
