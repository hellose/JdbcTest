package study;

import study.connection.Dbms;
import study.meta.DatabaseMetaType;

public class Main {

	public static void main(String[] args) throws Exception {
		Dbms mysql = Dbms.LOCAL_MYSQL;

		DatabaseMetaType[] metaTypes = DatabaseMetaType.values();
		for (DatabaseMetaType item : metaTypes) {
			mysql.getDbInfoAndData(item);
		}

	}

}
