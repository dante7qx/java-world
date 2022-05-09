package org.java.world.distributeid.zookeeper;

public abstract class SequenceHandler {
	public abstract long nextId(final SequenceEnum sequenceEnum);
}
