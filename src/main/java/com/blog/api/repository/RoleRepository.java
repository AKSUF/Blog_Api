package com.blog.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.api.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Integer>{

}
