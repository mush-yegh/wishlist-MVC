package com.wishlist.util;

import com.wishlist.persistence.entity.UserEntity;
import com.wishlist.security.details.UserDetailsImpl;
import org.springframework.security.core.Authentication;

public class StaticMethContainer {
    public static UserEntity findLoggedInUser(Authentication auth){
        UserDetailsImpl details = (UserDetailsImpl)auth.getPrincipal();
        UserEntity loggedInUser = details.getUserEntity();
        return loggedInUser;
    }
}
