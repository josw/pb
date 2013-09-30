package com.webctl.pb.www;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.webctl.pb.member.service.MemberService;

@RequestMapping("/www")
@Controller
public class WwwController {
	
	@Autowired
	private MemberService memberService;
	
	
	@RequestMapping("/")
	public String getIndex(
			final ModelMap model) {
		
		model.addAttribute("attr1", "thymeleaf~");
		model.addAttribute("members", memberService.getMember());
		
		return "Index";
	}
	
}
