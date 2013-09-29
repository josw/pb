package com.webctl.pb.member.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="member")
public class Member {

	@Id private int id;
	@Column private String name; 
}
