package edu.damago.seminar.persistence;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;



@Entity
@Table(name="Seminar", schema="seminar_jdo")
public class Seminar {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="seminar_id")
	private int identity;

	@NotNull
	@Column(name="termin", nullable=false, updatable=true)
	private Date day;

	@NotNull @Size(max=30)
	@Column(name="ort", nullable=false, updatable=true, length=30)
	private String location;

	@NotNull @Size(max=30)
	@Column(name="thema", nullable=false, updatable=true, length=30)
	private String topic;

	@NotNull @Size(max=250)
	@Column(name="beschreibung", nullable=false, updatable=true, length=250)
	private String description;


	public int getIdentity () {
		return this.identity;
	}


	protected void setIdentity (final int identity) {
		this.identity = identity;
	}


	public Date getDay () {
		return this.day;
	}


	public void setDay (final Date day) {
		this.day = day;
	}


	public String getLocation () {
		return this.location;
	}


	public void setLocation (final String location) {
		this.location = location;
	}


	public String getTopic () {
		return this.topic;
	}


	public void setTopic (final String topic) {
		this.topic = topic;
	}


	public String getDescription () {
		return this.description;
	}


	public void setDescription (final String description) {
		this.description = description;
	}
}