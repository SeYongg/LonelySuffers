package kr.co.review.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import common.FileManager;
import kr.co.review.model.service.ReviewService;

@Controller
public class ReviewController {
	@Autowired
	private ReviewService service;
	@Autowired
	private FileManager manager;
	
	
}
