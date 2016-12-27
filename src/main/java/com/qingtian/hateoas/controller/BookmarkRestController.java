package com.qingtian.hateoas.controller;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.qingtian.hateoas.dao.AccountRepository;
import com.qingtian.hateoas.dao.BookmarkRepository;
import com.qingtian.hateoas.model.Bookmark;

/**
 * @author Greg Turnquist
 */
@RestController
@RequestMapping("/{userId}/bookmarks")
class BookmarkRestController {

	private final BookmarkRepository bookmarkRepository;

	private final AccountRepository accountRepository;

    @Autowired
    BookmarkRestController(BookmarkRepository bookmarkRepository,
                           AccountRepository accountRepository) {
        this.bookmarkRepository = bookmarkRepository;
        this.accountRepository = accountRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
	Resources<BookmarkResource> readBookmarks(@PathVariable String userId) {

		this.validateUser(userId);

		List<BookmarkResource> bookmarkResourceList = bookmarkRepository
				.findByAccountUsername(userId).stream().map(BookmarkResource::new)
				.collect(Collectors.toList());

		return new Resources<>(bookmarkResourceList);
	}

	@RequestMapping(method = RequestMethod.POST)
	ResponseEntity<?> add(@PathVariable String userId, @RequestBody Bookmark input) {

		this.validateUser(userId);

		return accountRepository.findByUsername(userId)
            .map(account -> {
                Bookmark bookmark = bookmarkRepository
                        .save(new Bookmark(account, input.uri, input.description));

                Link forOneBookmark = new BookmarkResource(bookmark).getLink("self");

                return ResponseEntity.created(URI.create(forOneBookmark.getHref())).build();
            })
            .orElse(ResponseEntity.noContent().build());
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{bookmarkId}")
	BookmarkResource readBookmark(@PathVariable String userId,
                                  @PathVariable Long bookmarkId) {
		this.validateUser(userId);
		return new BookmarkResource(this.bookmarkRepository.findOne(bookmarkId));
	}

	private void validateUser(String userId) {
		this.accountRepository
			.findByUsername(userId)
			.orElseThrow(() -> new UserNotFoundException(userId));
	}
}


//
//import java.net.URI;
//import java.util.Collection;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.rest.webmvc.RepositoryRestController;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
//
//import com.qingtian.hateoas.dao.AccountRepository;
//import com.qingtian.hateoas.dao.BookmarkRepository;
//import com.qingtian.hateoas.model.Bookmark;
//
////@RestController
////@RequestMapping("/{userId}/bookmarks")
//@RepositoryRestController
//class BookmarkRestController {
//
//	private final BookmarkRepository bookmarkRepository;
//
//	private final AccountRepository accountRepository;
//
//	@Autowired
//	BookmarkRestController(BookmarkRepository bookmarkRepository,
//						   AccountRepository accountRepository) {
//		this.bookmarkRepository = bookmarkRepository;
//		this.accountRepository = accountRepository;
//	}
//
//	@RequestMapping(method = RequestMethod.GET)
//	Collection<Bookmark> readBookmarks(@PathVariable String userId) {
//		this.validateUser(userId);
//		return this.bookmarkRepository.findByAccountUsername(userId);
//	}
//
//	@RequestMapping(method = RequestMethod.POST)
//	ResponseEntity<?> add(@PathVariable String userId, @RequestBody Bookmark input) {
//		this.validateUser(userId);
//		return this.accountRepository
//				.findByUsername(userId)
//				.map(account -> {
//					Bookmark result = bookmarkRepository.save(new Bookmark(account,
//							input.uri, input.description));
//
//					URI location = ServletUriComponentsBuilder
//						.fromCurrentRequest().path("/{id}")
//						.buildAndExpand(result.getId()).toUri();
//
//					return ResponseEntity.created(location).build();
//				})
//				.orElse(ResponseEntity.noContent().build());
//	}
//
//	@RequestMapping(method = RequestMethod.GET, value = "/{bookmarkId}")
//	Bookmark readBookmark(@PathVariable String userId, @PathVariable Long bookmarkId) {
//		this.validateUser(userId);
//		return this.bookmarkRepository.findOne(bookmarkId);
//	}
//
//	private void validateUser(String userId) {
//		this.accountRepository.findByUsername(userId).orElseThrow(
//				() -> new UserNotFoundException(userId));
//	}
//}