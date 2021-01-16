package com.oneservereach.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.oneservereach.main.model.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
}
