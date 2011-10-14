package org.mb.session.identity;

import org.mb.record.Entity;

import java.util.HashSet;
import java.util.Set;

public class StoredSet {

	protected final Set<Entity> storeds = new HashSet<Entity>();

	public void clear() {
		storeds.clear();
	}

	public boolean isStored(final Entity e) {
		if (storeds.contains(e)) {
			return true;
		} else {
			storeds.add(e);
			return false;
		}
	}

}
