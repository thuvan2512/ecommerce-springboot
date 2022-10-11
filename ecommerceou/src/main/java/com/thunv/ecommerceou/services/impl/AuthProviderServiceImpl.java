package com.thunv.ecommerceou.services.impl;

import com.thunv.ecommerceou.models.pojo.AuthProvider;
import com.thunv.ecommerceou.repositories.AuthProviderRepository;
import com.thunv.ecommerceou.services.AuthProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthProviderServiceImpl implements AuthProviderService {
    @Autowired
    private AuthProviderRepository authProviderRepository;
    @Override
    public AuthProvider getAuthProviderByID(int authID) throws RuntimeException{
        return this.authProviderRepository.findById(authID).orElseThrow(() ->
                new RuntimeException("Can not find auth provider with id = " + authID));
    }

    @Override
    public List<AuthProvider> getAllAuthProvider() throws RuntimeException{
        try {
            return this.authProviderRepository.findAll();
        }catch (Exception ex){
            String error_ms = ex.getMessage();
            throw new RuntimeException(error_ms);
        }
    }

    @Override
    public AuthProvider createAuthProvider(AuthProvider authProvider) throws RuntimeException{
        try {
            return this.authProviderRepository.save(authProvider);
        }catch (Exception ex){
            String error_ms = ex.getMessage();
            throw new RuntimeException(error_ms);
        }
    }

    @Override
    public AuthProvider updateAuthProvider(AuthProvider authProvider) throws RuntimeException{
        try {
            return this.authProviderRepository.save(authProvider);
        }catch (Exception ex){
            String error_ms = ex.getMessage();
            throw new RuntimeException(error_ms);
        }
    }

    @Override
    public boolean deleteAuthProvider(int authID) throws RuntimeException{
        try {
            if (this.authProviderRepository.existsById(authID) == false){
                throw new RuntimeException("Auth provider does not exist");
            }
            this.authProviderRepository.deleteById(authID);
            return true;
        }catch (Exception ex){
            String error_ms = ex.getMessage();
            throw new RuntimeException(error_ms);
        }
    }
}
