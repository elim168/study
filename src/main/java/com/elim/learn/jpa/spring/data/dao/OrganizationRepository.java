package com.elim.learn.jpa.spring.data.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.elim.learn.jpa.spring.data.entity.Organization;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Integer> {

}
