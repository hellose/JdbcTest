package study;

import study.connection.Dbms;

public class Main {

	public static void main(String[] args) throws Exception {

//		Dbms labPostgre = Dbms.LAB_POSTGRESQL;
//		String queryString = "select * from etl_data.mara;";
//		labPostgre.dmlQuery(queryString);

		
		
//		Dbms labOracle = Dbms.LAB_ORACLE;
//		String queryString = "select ts from csh_timestamp";
//		labOracle.selectQuery(queryString);

		Dbms.closeAll();
	}

}
