package org.morm.record.compo;

public interface Collaborable<T> {

	T getCollaboration();

	void setCollaboration(T collaboration);

	String getColumnName();

	Collaborable<T> doCloneCollaboration();
}
