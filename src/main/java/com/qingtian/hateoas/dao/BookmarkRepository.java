package com.qingtian.hateoas.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.qingtian.hateoas.model.Bookmark;
import java.util.Collection;
public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    Collection<Bookmark> findByAccountUsername(String username);
}