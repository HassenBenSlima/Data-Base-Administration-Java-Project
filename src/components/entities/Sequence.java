package components.entities;

public class Sequence {

	private String sequenceName;

	public Sequence(String sequence) {
		this.sequenceName = sequence;

	}

	public Sequence() {
	}

	public String toString() {
		return sequenceName;
	}

	public String getSequenceName() {
		return sequenceName;
	}

	public void setSequenceName(String sequenceName) {
		this.sequenceName = sequenceName;
	}

}
