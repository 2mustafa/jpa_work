package edu.damago.cookbook.persistence;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.Size;


@Table(name="Document", schema="cookbook")
public class Document extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@PrimaryKeyJoinColumn(name = "documentIdentity")
	private long documentIdentity;

	@Column(nullable = false, updatable = true)
	@Size(min = 64, max = 64)
	private String hash;

	@Column(nullable = false, updatable = true)
	private String type;

	@Column(nullable = false, updatable = true)
	private byte content;

	public Document () {
		
	}

	public Document (String hash, byte content) {
		this.hash = hash;
		this.content = content;
		
	}


	public long getDocumentIdentity () {
		return documentIdentity;
	}


	protected void setDocumentIdentity (long documentIdentity) {
		this.documentIdentity = documentIdentity;
	}


	public String getHash () {
		return hash;
	}


	protected void setHash (String hash) {
		this.hash = hash;
	}


	public String getType () {
		return type;
	}


	protected void setType (String type) {
		this.type = type;
	}


	public byte getContent () {
		return content;
	}


	protected void setContent (byte content) {
		this.content = content;
	}

}
