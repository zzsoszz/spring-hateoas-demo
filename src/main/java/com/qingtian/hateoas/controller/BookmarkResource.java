package com.qingtian.hateoas.controller;


import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;

import com.qingtian.hateoas.model.Bookmark;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

/**
 * @author Greg Turnquist
 */
class BookmarkResource extends ResourceSupport {

	private final Bookmark bookmark;

	public BookmarkResource(Bookmark bookmark) {
		String username = bookmark.getAccount().getUsername();
		this.bookmark = bookmark;
		this.add(new Link(bookmark.getUri(), "bookmark-uri"));
		this.add(linkTo(BookmarkRestController.class, username).withRel("bookmarks"));
		this.add(linkTo(methodOn(BookmarkRestController.class, username)
				.readBookmark(username, bookmark.getId())).withSelfRel());
		
	}

	public Bookmark getBookmark() {
		return bookmark;
	}
}
