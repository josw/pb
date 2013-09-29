package com.webctl.pb.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.webctl.pb.member.model.Member;


public interface MemberRepository extends JpaRepository<Member, Integer> {

//	@Modifying
//	@Query("update Member set last_login_dt = current_timestamp where member_id = :member_id")
//	void updateLastLoginDt(@Param(value="member_id")Long memberId);

}
