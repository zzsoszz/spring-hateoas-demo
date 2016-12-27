package com.qingtian.hateoas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryLinksResource;
import org.springframework.hateoas.ResourceProcessor;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.stereotype.Component;

import com.qingtian.hateoas.dao.BookmarkRepository;
import com.qingtian.hateoas.model.Bookmark;

@Component
public class RootResourceProcessor implements ResourceProcessor<RepositoryLinksResource> {
	@Autowired
	BookmarkRepository bookmarkRepository;
	@Override
	public RepositoryLinksResource process(RepositoryLinksResource resource) {
		String id="jhoeller";
		Bookmark bookmark=bookmarkRepository.findByAccountUsername(id).iterator().next();
		resource.add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(BookmarkRestController.class).add(id, bookmark)).withRel("add"));
		return resource;
	}
}