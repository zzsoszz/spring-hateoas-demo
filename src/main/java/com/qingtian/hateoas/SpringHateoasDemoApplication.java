package com.qingtian.hateoas;

import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.qingtian.hateoas.dao.AccountRepository;
import com.qingtian.hateoas.dao.BookmarkRepository;
import com.qingtian.hateoas.model.Account;
import com.qingtian.hateoas.model.Bookmark;

@SpringBootApplication
public class SpringHateoasDemoApplication {
	public static void main(String[] args) {
		SpringApplication.run(SpringHateoasDemoApplication.class, args);
	}
	
	@Bean
	CommandLineRunner init(AccountRepository accountRepository, BookmarkRepository bookmarkRepository) {
		return (evt) -> Arrays.asList("jhoeller,dsyer,pwebb,ogierke,rwinch,mfisher,mpollack,jlong".split(","))
				.forEach(a -> {
					Account account = accountRepository.save(new Account(a, "password"));
					bookmarkRepository.save(new Bookmark(account, "http://bookmark.com/1/" + a, "A description"));
					bookmarkRepository.save(new Bookmark(account, "http://bookmark.com/2/" + a, "A description"));
				});
	}
	
}