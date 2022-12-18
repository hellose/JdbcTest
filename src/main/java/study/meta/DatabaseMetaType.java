package study.meta;

public enum DatabaseMetaType {

	CATALOG("DatabaseMetaData.getCatalogs"), SCHEMA("DatabaseMetaData.getSchemas"), TABLE("DatabaseMetaData.getTables");

	private String metaString;

	private DatabaseMetaType(String metaString) {
		this.metaString = metaString;
	}

	public String getMetaString() {
		return metaString;
	}

}
