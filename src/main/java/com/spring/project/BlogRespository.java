package com.spring.project;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BlogRespository extends JpaRepository<Blog,Integer> {
    //custom query to search blog post by title or by content

    List<Blog> findByTitleContainingOrContentContaining(String text, String textAgain);
}
