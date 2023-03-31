package kr.co.notice.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.notice.model.service.NoticeService;
import kr.co.notice.model.vo.Notice;

@Controller
public class NoticeController {
	@Autowired
	private NoticeService service;
	
	@RequestMapping(value="/noticeList.do")
	public String noticeList(Model model) {
		ArrayList<Notice> list = service.selectAllNotice();
		model.addAttribute("list", list);
		return "notice/noticeList";
	}
	
}