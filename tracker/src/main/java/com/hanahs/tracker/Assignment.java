package com.hanahs.tracker;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
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
	private Integer estimatedETA;
	private LocalDate estimatedStartDate;
	
	private Set<String> presentationWords = new HashSet<>(Arrays.asList("발표", "presentation", "피피티", "ppt"));
	private Set<String> bookReportWords = new HashSet<>(Arrays.asList("독후감", "book"));
	private Set<String> reportWords = new HashSet<>(Arrays.asList("보고서", "리포트", "report"));
	private Set<String> videoWords = new HashSet<>(Arrays.asList("영상", "시청", "video"));
	private Set<String> problemSolveWords = new HashSet<>(Arrays.asList("풀이", "문제", "문제풀이", "solve"));
	
	@Override
	public int hashCode() {
		return Objects.hash(description, estimatedETA, due, name, wordPool);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Assignment))
			return false;
		Assignment other = (Assignment) obj;
		return Objects.equals(description, other.description) && estimatedETA == other.estimatedETA
				&& Objects.equals(due, other.due) && Objects.equals(name, other.name)
				&& Objects.equals(wordPool, other.wordPool);
	}

	public Assignment(String name, String description, LocalDateTime due) {
		this.name = name;
		this.description = description;
		this.due = due;
		this.estimatedETA = null;
		this.wordPool = null;
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
		if (wordPool != null) return wordPool;
		CharSequence normalizedTitle = TwitterKoreanProcessorJava.normalize(name);
		Seq<KoreanToken> titleTokens = TwitterKoreanProcessorJava.tokenize(normalizedTitle);
		Seq<KoreanToken> stemmedTitleTokens = TwitterKoreanProcessorJava.stem(titleTokens);
		CharSequence normalizedDescription = TwitterKoreanProcessorJava.normalize(description);
		Seq<KoreanToken> descriptionTokens = TwitterKoreanProcessorJava.tokenize(normalizedDescription);
		Seq<KoreanToken> stemmedDescripionTokens = TwitterKoreanProcessorJava.stem(descriptionTokens);
		wordPool = new HashSet<>();
		wordPool.addAll(TwitterKoreanProcessorJava.tokensToJavaStringList(stemmedTitleTokens));
		wordPool.addAll(TwitterKoreanProcessorJava.tokensToJavaStringList(stemmedDescripionTokens));
		wordPool = wordPool.stream().filter((x) -> x.length() > 1).filter((x) -> x.trim().length() > 0)
				.collect(Collectors.toSet());
		return wordPool;
	}

	public Integer getEstimatedETA() {
		if (estimatedETA != null) return estimatedETA;
		Set<String> pool = getWordPool();
		Set<String> presentation = new HashSet<>(pool);
		presentation.retainAll(presentationWords);
		Set<String> video = new HashSet<>(pool);
		video.retainAll(videoWords);
		Set<String> bookReport = new HashSet<>(pool);
		bookReport.retainAll(bookReportWords);
		Set<String> report = new HashSet<>(pool);
		report.retainAll(reportWords);
		Set<String> problemSolve = new HashSet<>(pool);
		problemSolve.retainAll(problemSolveWords);
		if (presentation != null && presentation.size() > 0) estimatedETA = 5;
		else if (video != null && video.size() > 0) estimatedETA = 4;
		else if (bookReport != null && bookReport.size() > 0) estimatedETA = 3;
		else if (report != null && report.size() > 0) estimatedETA = 2;
		else if (problemSolve != null && problemSolve.size() > 0) estimatedETA = 1;
		else estimatedETA = 0;
 		return estimatedETA;
	}

	public LocalDate getEstimatedStartDate() {
		if (estimatedStartDate != null) return estimatedStartDate;
		estimatedStartDate = LocalDate.from(due).minusDays(getEstimatedETA());
		return estimatedStartDate;
	}
}
