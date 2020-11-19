package com.hanahs.tracker;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import com.twitter.penguin.korean.TwitterKoreanProcessorJava;
import com.twitter.penguin.korean.tokenizer.KoreanTokenizer.KoreanToken;

import scala.collection.Seq;

public class Assignment {
	private String name;
	private String description;
	private LocalDateTime due;
	private Set<String> wordPool;
	
	public Assignment(String name, String description, LocalDateTime due) {
		this.name = name;
		this.description = description;
		this.due = due;
		CharSequence normalizedTitle = TwitterKoreanProcessorJava.normalize(this.name);
		Seq<KoreanToken> titleTokens = TwitterKoreanProcessorJava.tokenize(normalizedTitle);
		CharSequence normalizedDescription = TwitterKoreanProcessorJava.normalize(this.description);
		Seq<KoreanToken> descriptionTokens = TwitterKoreanProcessorJava.tokenize(normalizedDescription);
		this.wordPool = new HashSet<>();
		this.wordPool.addAll(TwitterKoreanProcessorJava.tokensToJavaStringList(titleTokens));
		this.wordPool.addAll(TwitterKoreanProcessorJava.tokensToJavaStringList(descriptionTokens));
		this.wordPool = this.wordPool.stream().filter((x) -> x.length() > 1).filter((x) -> x.trim().length() > 0)
				.collect(Collectors.toSet());
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public LocalDateTime getDue() {
		return due;
	}

	public Set<String> getWordPool() {
		return wordPool;
	}
}