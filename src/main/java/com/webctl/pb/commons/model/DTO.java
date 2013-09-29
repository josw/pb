package com.webctl.pb.commons.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public abstract class DTO implements Serializable{
	
	private static final long serialVersionUID = 2920441928957003861L;
	@Column private Date crt_dt;
	@Column private Date upd_dt;
	
    @PreUpdate
    public void preUpdate() {
    	upd_dt = new Date();
    }
    
    @PrePersist
    public void prePersist() {
        Date now = new Date();
        crt_dt = now;
        upd_dt = now;
    }
    
	public DTO(DTO dto) {
		super();
		this.crt_dt = dto.crt_dt;
		this.upd_dt = dto.upd_dt;
	}
}
