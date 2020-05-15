package com.br.sfb.model;

import java.sql.Date;

import javax.persistence.*;

@Entity
public class PasswordResetToken {
  
  
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;  
    @OneToOne(targetEntity = Usuario.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private Usuario user;
    
    public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Usuario getUser() {
		return user;
	}
	public void setUser(Usuario user) {
		this.user = user;
	}
	public Long getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(Long expiryDate) {
		this.expiryDate = expiryDate;
	}
	public Date getDataSolicitação() {
		return dataSolicitação;
	}
	public void setDataSolicitação(Date dataSolicitação) {
		this.dataSolicitação = dataSolicitação;
	}
	private Long expiryDate;
    private Date dataSolicitação;
   

	
}