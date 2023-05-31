package edu.damago.cookbook.persistence;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.eclipse.persistence.annotations.CacheIndex;
import edu.damago.tool.HashCodes;


@Entity
@Table(schema = "cookbook", name = "Document")
@PrimaryKeyJoinColumn(name = "documentIdentity")
@DiscriminatorValue("Document")
public class Document extends BaseEntity {
	static private final byte[] EMPTY_CONTENT = {};
	static private final String EMPTY_HASH = HashCodes.sha2HashText(256, EMPTY_CONTENT);

	@NotNull
	@Size(max = 63)
	@Column(nullable = false, updatable = false, insertable = true, length = 63)
	private String type;

	@NotNull
	@Size(min = 64, max = 64)
	@CacheIndex(updateable = false)
	@Column(nullable = false, updatable = false, insertable = true, length = 64, unique = true)
	private String hash;

	@NotNull
	@Size(max = 0x10000000)	// max = 256MB
	@Column(nullable = false, updatable = false, insertable = true)
	private byte[] content;


	protected Document () {
		this(null, null);
	}


	public Document (final String type, final byte[] content) {
		this.content = content == null ? EMPTY_CONTENT : content;
		this.hash = this.content.length == 0 ? EMPTY_HASH : HashCodes.sha2HashText(256, this.content);
		this.type = type == null ? "application/octet-stream" : type;
	}


	public String getHash () {
		return this.hash;
	}


	protected void setHash (String hash) {
		this.hash = hash;
	}


	public String getType () {
		return this.type;
	}


	protected void setType (String type) {
		this.type = type;
	}


	public byte[] getContent () {
		return this.content;
	}


	protected void setContent (byte[] content) {
		this.content = content;
	}
}