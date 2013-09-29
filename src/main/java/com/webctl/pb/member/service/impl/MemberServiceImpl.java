package com.webctl.pb.member.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webctl.pb.member.model.Member;
import com.webctl.pb.member.repository.MemberRepository;
import com.webctl.pb.member.service.MemberService;

@Service
public class MemberServiceImpl implements MemberService {
	
	@Autowired
	private MemberRepository memberRepository;

	@Override
	public List<Member> getMember() {
		return memberRepository.findAll();
	}

}
