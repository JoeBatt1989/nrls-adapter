package nrls.adapter.model.documentreference;

import java.util.Date;

import nrls.adapter.model.documentreference.Author;
import nrls.adapter.model.documentreference.Custodian;
import nrls.adapter.model.documentreference.Subject;
import nrls.adapter.model.task.Type;
import nrls.adapter.model.task.Task;
import nrls.adapter.model.task.Content;

public class DocumentReference {

	private String resourceType;
	private String status;
	private Type type;
	private Subject subject;
	private String indexed;
	private Author author;
	private Custodian custodian;
	private Content content;
	private String action;
	private MasterIdentifier masterIdentifier;

	public String getResourceType() {
		return resourceType;
	}

	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	public String getIndexed() {
		return indexed;
	}

	public void setIndexed(String indexed) {
		this.indexed = indexed;
	}

	public Author getAuthor() {
		return author;
	}

	public void setAuthor(Author author) {
		this.author = author;
	}

	public Custodian getCustodian() {
		return custodian;
	}

	public void setCustodian(Custodian custodian) {
		this.custodian = custodian;
	}

	public Content getContent() {
		return content;
	}

	public void setContent(Content content) {
		this.content = content;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public MasterIdentifier getMasterIdentifier() {
		return masterIdentifier;
	}

	public void setMasterIdentifier(MasterIdentifier masterIdentifier) {
		this.masterIdentifier = masterIdentifier;
	}

}