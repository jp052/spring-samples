package com.planksoftware.springmvcresthateoasjpa.bookmarks;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    public Collection<Bookmark> findByAccountUsername(String username);
}
