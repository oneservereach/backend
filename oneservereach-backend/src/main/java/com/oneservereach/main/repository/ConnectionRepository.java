package com.oneservereach.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.oneservereach.main.model.Connection;

@Repository
public interface ConnectionRepository extends JpaRepository<Connection,Long> {
}
