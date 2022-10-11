package com.thunv.ecommerceou.services.impl;

import com.thunv.ecommerceou.models.pojo.Role;
import com.thunv.ecommerceou.repositories.RoleRepository;
import com.thunv.ecommerceou.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;
    @Override
    public Role getRoleByID(int roleID) throws RuntimeException{
        return this.roleRepository.findById(roleID).orElseThrow(() ->
                new RuntimeException("Can not find role with id = " + roleID));
    }

    @Override
    public List<Role> getAllRole() throws RuntimeException{
        try {
            return this.roleRepository.findAll();
        }catch (Exception ex){
            String error_ms = ex.getMessage();
            throw new RuntimeException(error_ms);
        }
    }

    @Override
    public Role createRole(Role role) throws RuntimeException{
        try {
            return this.roleRepository.save(role);
        }catch (Exception ex){
            String error_ms = ex.getMessage();
            throw new RuntimeException(error_ms);
        }
    }

    @Override
    public Role updateRole(Role role) throws RuntimeException{
        try {
            return this.roleRepository.save(role);
        }catch (Exception ex){
            String error_ms = ex.getMessage();
            throw new RuntimeException(error_ms);
        }
    }

    @Override
    public boolean deleteRole(int roleID) throws RuntimeException{
        try {
            if (this.roleRepository.existsById(roleID) == false){
                throw new RuntimeException("Role does not exist");
            }
            this.roleRepository.deleteById(roleID);
            return true;
        }catch (Exception ex){
            String error_ms = ex.getMessage();
            throw new RuntimeException(error_ms);
        }
    }
}
