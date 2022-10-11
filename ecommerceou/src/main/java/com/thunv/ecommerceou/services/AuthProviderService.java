package com.thunv.ecommerceou.services;


import com.thunv.ecommerceou.models.pojo.AuthProvider;

import java.util.List;

public interface AuthProviderService {
    AuthProvider getAuthProviderByID(int authID);
    List<AuthProvider> getAllAuthProvider();
    AuthProvider createAuthProvider(AuthProvider authProvider);
    AuthProvider updateAuthProvider(AuthProvider authProvider);
    boolean deleteAuthProvider(int authID);
}
