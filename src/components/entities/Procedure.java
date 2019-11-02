package components.entities;

public class Procedure {

	private String procedureName;

	public Procedure(String procedure) {
		this.procedureName = procedure;

	}

	public Procedure() {
	}

	public String toString() {
		return procedureName;
	}

	public String getProcedureName() {
		return procedureName;
	}

	public void setProcedureName(String procedureName) {
		this.procedureName = procedureName;
	}

}
