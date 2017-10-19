package solutionportal.FGSP;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.UUID;

import org.apache.poi.ss.usermodel.Row;

public class LoadSolutionTable {
	public Statement statement;
	
	LoadSolutionTable(Statement s) {
		this.statement = s;
	}
	
	public void loadDataInSolutionTable(String id, Row row) {
		String hpsm_id = row.getCell(2).getStringCellValue();
		String name = row.getCell(3).getStringCellValue();
		String description = row.getCell(4).getStringCellValue();
		String picture = row.getCell(32).getStringCellValue();
		String servicenow_id = row.getCell(31).getStringCellValue();
		String hpsm_name = row.getCell(23).getStringCellValue();
		String hpsm_type = row.getCell(25).getStringCellValue();
		String hpsm_status = row.getCell(26).getStringCellValue();
		String hpsm_assigned_group = row.getCell(27).getStringCellValue();
		String business_capabilitues = row.getCell(33).getStringCellValue();

		try {
			StringBuffer queryString = new StringBuffer("insert into solution values ('");
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
			queryString.append(business_capabilitues);
			queryString.append("')");

			System.out.println(queryString.toString());
			statement.execute(queryString.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void loadDataForOwner(String solution_id, Row row) {

		String owner_roche_id = row.getCell(5).getStringCellValue();
		if (owner_roche_id.length() > 8) { return; }
		String owner_id = checkUserInDB(owner_roche_id);
		if (owner_id == null) {
			String solution_owner = row.getCell(6).getStringCellValue();
			String name[] = solution_owner.split(",");
			String owner_first_name = "";
			String owner_last_name = "";
			if (name.length == 2) {
				owner_first_name = name[1];
				owner_last_name = name[0];
			}
			String owner_email = row.getCell(7).getStringCellValue();
			String owner_site = row.getCell(8).getStringCellValue();
			String owner_country = row.getCell(9).getStringCellValue();
			String owner_department = row.getCell(10).getStringCellValue();

			String uniqueID = UUID.randomUUID().toString();
			System.out.println(uniqueID);

			try {
				StringBuffer queryString = new StringBuffer("insert into contact(id, name, surname, email, "
						+ "site_location, country, departement, roche_id) values ('");
				queryString.append(uniqueID);
				queryString.append("','");
				queryString.append(owner_first_name);
				queryString.append("','");
				queryString.append(owner_last_name);
				queryString.append("','");
				queryString.append(owner_email);
				queryString.append("','");
				queryString.append(owner_site);
				queryString.append("','");
				queryString.append(owner_country);
				queryString.append("','");
				queryString.append(owner_department);
				queryString.append("','");
				queryString.append(owner_roche_id);
				queryString.append("')");

				System.out.println(queryString.toString());
				statement.execute(queryString.toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		loadDataForSolutionContactRelation("is_owner_solution", solution_id, owner_id);
	}

	public void loadDataForDeputy(String id, Row row) {
		String deputy_roche_id = row.getCell(11).getStringCellValue();
		if (deputy_roche_id.length() > 8) { return; }
		String deputy_id = checkUserInDB(deputy_roche_id);
		if (deputy_id == null) {
			String solution_deputy = row.getCell(12).getStringCellValue();
			String name[] = solution_deputy.split(",");
			String deputy_first_name = "";
			String deputy_last_name = "";
			if (name.length == 2) {
				deputy_first_name = name[1];
				deputy_last_name = name[0];
			}

			String deputy_email = row.getCell(13).getStringCellValue();
			String deputy_site = row.getCell(14).getStringCellValue();
			String deputy_country = row.getCell(15).getStringCellValue();
			String deputy_department = row.getCell(16).getStringCellValue();

			String uniqueID = UUID.randomUUID().toString();
			System.out.println(uniqueID);

			try {
				StringBuffer queryString = new StringBuffer("insert into contact(id, name, surname, email, "
						+ "site_location, country, departement, roche_id) values ('");
				queryString.append(uniqueID);
				queryString.append("','");
				queryString.append(deputy_first_name);
				queryString.append("','");
				queryString.append(deputy_last_name);
				queryString.append("','");
				queryString.append(deputy_email);
				queryString.append("','");
				queryString.append(deputy_site);
				queryString.append("','");
				queryString.append(deputy_country);
				queryString.append("','");
				queryString.append(deputy_department);
				queryString.append("','");
				queryString.append(deputy_roche_id);
				queryString.append("')");

				System.out.println(queryString.toString());
				statement.execute(queryString.toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		loadDataForSolutionContactRelation("is_deputy_solution", id, deputy_id);
	}

	public void loadDataForBO(String id, Row row) {
		String bo_roche_id = row.getCell(17).getStringCellValue();
		if (bo_roche_id.length() > 8) { return; }
		String bo_id = checkUserInDB(bo_roche_id);
		if (bo_id == null) {
			String solution_bo = row.getCell(18).getStringCellValue();
			String name[] = solution_bo.split(",");
			String bo_first_name = "";
			String bo_last_name = "";
			if (name.length == 2) {
				bo_first_name = name[1];
				bo_last_name = name[0];
			}

			String bo_email = row.getCell(19).getStringCellValue();
			String bo_site = row.getCell(20).getStringCellValue();
			String bo_country = row.getCell(21).getStringCellValue();
			String bo_department = row.getCell(22).getStringCellValue();

			String uniqueID = UUID.randomUUID().toString();
			System.out.println(uniqueID);

			try {
				StringBuffer queryString = new StringBuffer("insert into contact(id, name, surname, email, "
						+ "site_location, country, departement, roche_id) values ('");
				queryString.append(uniqueID);
				queryString.append("','");
				queryString.append(bo_first_name);
				queryString.append("','");
				queryString.append(bo_last_name);
				queryString.append("','");
				queryString.append(bo_email);
				queryString.append("','");
				queryString.append(bo_site);
				queryString.append("','");
				queryString.append(bo_country);
				queryString.append("','");
				queryString.append(bo_department);
				queryString.append("','");
				queryString.append(bo_roche_id);
				queryString.append("')");

				System.out.println(queryString.toString());
				statement.execute(queryString.toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		loadDataForSolutionContactRelation("is_bo_solution", id, bo_id);
	}
	
	public void loadDataForSolutionContactRelation(String tableName, String solution_id, String deputy_id) {
		try {
			StringBuffer queryString = new StringBuffer("insert into ");
			queryString.append(tableName);
			queryString.append(" values('");
			queryString.append(solution_id);
			queryString.append("','");
			queryString.append(deputy_id);
			queryString.append("')");
			
			System.out.println(queryString.toString());
			statement.execute(queryString.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String checkUserInDB(String userid) {
		try {
			String queryString = "select id from contact where roche_id = '" + userid + "'";
			System.out.println(queryString.toString());
			ResultSet rs = statement.executeQuery(queryString);
			if (rs.next()) {
				return rs.getString(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
