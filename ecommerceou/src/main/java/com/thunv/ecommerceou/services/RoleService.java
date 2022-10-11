package com.thunv.ecommerceou.services;


import com.thunv.ecommerceou.models.pojo.Role;

import java.util.List;

public interface RoleService {
    Role getRoleByID(int roleID);
    List<Role> getAllRole();
    Role createRole(Role role);
    Role updateRole(Role role);
    boolean deleteRole(int roleID);
}
