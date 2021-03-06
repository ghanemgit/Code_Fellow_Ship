package com.mycode.codefellowship.Repository;

import com.mycode.codefellowship.Models.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post,Long> {

    Post findById(long id);
    Post findAllByApplicationUserId(long id);

}
