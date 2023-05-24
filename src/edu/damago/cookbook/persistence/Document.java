package edu.damago.cookbook.persistence;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Size;

public class Document extends BaseEntity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long documentIdentity;
	
	@Column(nullable = false, updatable =  true) 
	@Size(min = 64, max = 64)
	private String hash;
	
	@Column(nullable = false, updatable = true)
	private String type;
	
	@Column(nullable = false, updatable = true)
	private byte content;
	
/**	
	 PRIMARY KEY (documentIdentity),
	 FOREIGN KEY (documentIdentity) REFERENCES BaseEntity (identity) ON DELETE CASCADE ON UPDATE CASCADE,
	 UNIQUE KEY (hash)
**/
	public Document () {
	
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

public void setHash (String hash) {
	this.hash = hash;
}

public String getType () {
	return type;
}

public void setType (String type) {
	this.type = type;
}

public byte getContent () {
	return content;
}

public void setContent (byte content) {
	this.content = content;
}
	
	
	
}
