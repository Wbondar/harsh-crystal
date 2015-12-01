package pl.chelm.pwsz.harsh_crystal;

public final class Actor {
	private final ActorType type;
	
	Actor (ActorType type) {
		this.type = type;
	}

	public final ActorType getType() {
		return type;
	}

	@Override
	public String toString() {
		return "Actor [type=" + type + "]";
	}
}
