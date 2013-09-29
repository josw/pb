package com.webctl.pb.api;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Maps;
import com.webctl.pb.member.model.Member;
import com.webctl.pb.member.service.MemberService;

@Controller
public class TestController {
	
	@Autowired
	private MemberService memberService;
	
	@RequestMapping("/idx")
	@ResponseBody
	public List<Member> getIdx() {
		
		
		Map<String, Object> result = Maps.newHashMap();
		
		result.put("aaaa", "bbb");
		
		return memberService.getMember(); 
		
		
	}
	

}
