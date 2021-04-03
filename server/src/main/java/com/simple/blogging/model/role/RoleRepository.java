package com.simple.blogging.model.role;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

	public Role findRoleByFullName(String name);

}
