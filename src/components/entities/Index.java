package components.entities;

public class Index {
	private String indexName;

	public Index(String index) {
		this.indexName = index;

	}

	public Index() {
	}

	public String toString() {
		return indexName;
	}

	public String getIndexName() {
		return indexName;
	}

	public void setIndexName(String indexName) {
		this.indexName = indexName;
	}

}
