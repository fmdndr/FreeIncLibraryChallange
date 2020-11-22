package com.freeinc.Library.DataAccess;

import com.freeinc.Library.Entites.Role;

public interface IRoleDao {
    Role findRoleByName(String roleName);
}
