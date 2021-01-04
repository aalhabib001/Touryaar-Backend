package com.touryaar.respository;

import com.touryaar.model.BlogPostModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogPostRepository extends JpaRepository<BlogPostModel, String> {
}
