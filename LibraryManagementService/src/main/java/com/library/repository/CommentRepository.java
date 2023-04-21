package com.library.repository;

import com.library.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    @Query(
            value = "select * from comment c where c.usedId=id",nativeQuery = true
    )
    List<Comment> getBookmostComment();
}
