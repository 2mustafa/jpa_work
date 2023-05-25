package edu.damago.cookbook.persistence;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Table(name = "Document", schema = "cookbook")
@PrimaryKeyJoinColumn(name = "documentIdentity")
@DiscriminatorValue("Document")
public class Document extends BaseEntity {

	/**
	CREATE TABLE Document (
    documentIdentity BIGINT NOT NULL,
    hash CHAR(64) NOT NULL,
    type VARCHAR(63) NOT NULL,
    content LONGBLOB NOT NULL,
    PRIMARY KEY (documentIdentity),
    FOREIGN KEY (documentIdentity) REFERENCES BaseEntity (identity) ON DELETE CASCADE ON UPDATE CASCADE,
    UNIQUE KEY (hash)
);
	 */
	
	/**
	@NotNull 
	@Size(max=63) 
	@Column(nullable = false //defined///,
		updatable = false //readOnly//,
		insertable = true, //SQL INSET befehl//
		length = 63 //VARCHAR(63)//
		)	
	private String type;
	**/
	
	@Column(nullable = false, updatable = true)
	private String type;

	@Column(nullable = false)
	@Size(min = 64, max = 64)
	private String hash;

	@Column(nullable = false, updatable = true)
	private byte[] content;


	public Document () {

	}


	public Document (String hash, String type, byte[] content) {
		this.hash = hash;
		this.type = type;
		this.content = content;
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


	public byte[] getContent () {
		return content;
	}


	protected void setContent (byte[] content) {
		this.content = content;
	}

}
