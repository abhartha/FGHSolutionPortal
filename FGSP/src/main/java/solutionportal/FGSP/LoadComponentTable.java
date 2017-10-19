package solutionportal.FGSP;

import java.sql.Statement;

import org.apache.poi.ss.usermodel.Row;

public class LoadComponentTable {

	public Statement statement;
	
	LoadComponentTable(Statement s) {
		this.statement = s;
	}
	
	public void loadDataInComponentTable(String id, Row row) {
	
	}
	
}
