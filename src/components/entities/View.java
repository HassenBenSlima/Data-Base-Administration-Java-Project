package components.entities;

public class View {
	private String viewName;

	public View(String table) {
		this.viewName = table;

	}

	public View() {
	}

	public String toString() {
		return viewName;
	}

	public String getViewName() {
		return viewName;
	}

	public void setViewName(String tableName) {
		this.viewName = tableName;
	}

}
