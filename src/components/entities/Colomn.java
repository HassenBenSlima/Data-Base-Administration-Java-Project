package components.entities;

public class Colomn {
	private String colomnName;

	public Colomn(String colomn) {
		this.colomnName = colomn;

	}

	public Colomn() {
	}

	public String toString() {
		return colomnName;
	}

	public String getColomnName() {
		return colomnName;
	}

	public void setColomnName(String colomnName) {
		this.colomnName = colomnName;
	}

}
