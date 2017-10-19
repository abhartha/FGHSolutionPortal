package solutionportal.FGSP;

import java.sql.Statement;

import org.apache.poi.ss.usermodel.Row;

public class LoadComponentTable {

	public Statement statement;
	
	LoadComponentTable(Statement s) {
		this.statement = s;
	}
	
	public void loadDataInComponentTable(String id, Row row) {
	
		String hpsm_id = row.getCell(6).getStringCellValue();
		String name = row.getCell(7).getStringCellValue();
		String description = row.getCell(8).getStringCellValue();
		String picture = row.getCell(38).getStringCellValue();
		String servicenow_id = row.getCell(31).getStringCellValue();
		String hpsm_name = row.getCell(27).getStringCellValue();
		String hpsm_type = row.getCell(29).getStringCellValue();
		String hpsm_status = row.getCell(30).getStringCellValue();
		String hpsm_assigned_group = row.getCell(32).getStringCellValue();
		String business_capabilities = row.getCell(51).getStringCellValue();
		
		
		try {
			StringBuffer queryString = new StringBuffer("insert into component values ('");
			queryString.append(id);
			queryString.append("','");
			queryString.append(hpsm_id);
			queryString.append("','");
			queryString.append(name);
			queryString.append("','");
			queryString.append(description);
			queryString.append("','");
			queryString.append(picture);
			queryString.append("','");
			queryString.append(servicenow_id);
			queryString.append("','");
			queryString.append(hpsm_name);
			queryString.append("','");
			queryString.append(hpsm_type);
			queryString.append("','");
			queryString.append(hpsm_status);
			queryString.append("','");
			queryString.append(hpsm_assigned_group);
			queryString.append("','");
			queryString.append(business_capabilities);
			queryString.append("')");

			System.out.println(queryString.toString());
			statement.execute(queryString.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	
}
