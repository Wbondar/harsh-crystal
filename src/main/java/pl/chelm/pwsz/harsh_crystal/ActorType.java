package pl.chelm.pwsz.harsh_crystal;

public final class ActorType {
	private final int id;
	
	private ActorType (int id) {
		this.id = id;
	}
	
	public static ActorType getInstance(int id) {
		return new ActorType(id);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ActorType other = (ActorType) obj;
		if (id != other.id)
			return false;
		return true;
	}
}
