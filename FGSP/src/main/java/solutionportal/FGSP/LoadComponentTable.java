package solutionportal.FGSP;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.UUID;

import org.apache.poi.ss.usermodel.Row;

public class LoadComponentTable {

	public Statement statement;

	LoadComponentTable(Statement s) {
		this.statement = s;
	}

	public void loadDataInComponentTable(String id, Row row, boolean isModule) {

		String hpsm_id = row.getCell(6).getStringCellValue();
		String name = row.getCell(7).getStringCellValue();
		String description = row.getCell(8).getStringCellValue().replace("'", "''"); //TODO preparedstmt
		String picture = row.getCell(38).getStringCellValue();
		String servicenow_id = row.getCell(37).getStringCellValue();
		String hpsm_name = row.getCell(27).getStringCellValue();
		String hpsm_type = row.getCell(29).getStringCellValue();
		String hpsm_status = row.getCell(30).getStringCellValue();
		String hpsm_assigned_group = row.getCell(32).getStringCellValue();
		String bpo = row.getCell(51).getStringCellValue();
		String business_capabilities = row.getCell(51).getStringCellValue().replace("'", "''");
		String coe = row.getCell(51).getStringCellValue();
		String solution_architect = row.getCell(51).getStringCellValue();
		String architecture = row.getCell(51).getStringCellValue();
		String ba_application = row.getCell(51).getStringCellValue();
		String technical_specialist = row.getCell(51).getStringCellValue();
		String support_analyst = row.getCell(51).getStringCellValue();
		String support_incidents = row.getCell(51).getStringCellValue();
		String documentation = row.getCell(51).getStringCellValue().replace("'", "''");
		String training_documentation = row.getCell(51).getStringCellValue().replace("'", "''");
		String roadmap = row.getCell(51).getStringCellValue();
		String access_type = row.getCell(51).getStringCellValue();
		String technical_capabilities = row.getCell(51).getStringCellValue().replace("'", "''");
		String related_component_id = "";

		if (isModule) {
			try {
				String query = "select id from component where hpsm_id = '" + row.getCell(4).getStringCellValue() + "'";
				related_component_id = statement.executeQuery(query).getString(1);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

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
			queryString.append("','");
			queryString.append(bpo);
			queryString.append("','");
			queryString.append(coe);
			queryString.append("','");
			queryString.append(solution_architect);
			queryString.append("','");
			queryString.append(architecture);
			queryString.append("','");
			queryString.append(ba_application);
			queryString.append("','");
			queryString.append(technical_specialist);
			queryString.append("','");
			queryString.append(support_analyst);
			queryString.append("','");
			queryString.append(support_incidents);
			queryString.append("','");
			queryString.append(documentation);
			queryString.append("','");
			queryString.append(training_documentation);
			queryString.append("','");
			queryString.append(roadmap);
			queryString.append("','");
			queryString.append(access_type);
			queryString.append("','");
			queryString.append(technical_capabilities);
			queryString.append("','");
			queryString.append(related_component_id);
			queryString.append("')");
			System.out.println(queryString.toString());
			statement.execute(queryString.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (!isModule) {
			String related_hpsm_id = row.getCell(4).getStringCellValue();
			loadRelations(related_hpsm_id, id);
		}

	}

	public void loadDataForOwner(String solution_id, Row row) {

		String owner_roche_id = row.getCell(9).getStringCellValue();
		if (owner_roche_id.length() > 8) {
			return;
		}
		String owner_id = checkUserInDB(owner_roche_id);
		if (owner_id == null) {
			String solution_owner = row.getCell(10).getStringCellValue();
			String name[] = solution_owner.split(",");
			String owner_first_name = "";
			String owner_last_name = "";
			if (name.length == 2) {
				owner_first_name = name[1];
				owner_last_name = name[0];
			}
			String owner_email = row.getCell(11).getStringCellValue();
			String owner_site = row.getCell(12).getStringCellValue();
			String owner_country = row.getCell(13).getStringCellValue();
			String owner_department = row.getCell(14).getStringCellValue();

			owner_id = UUID.randomUUID().toString();
			System.out.println(owner_id);

			try {
				StringBuffer queryString = new StringBuffer("insert into contact(id, name, surname, email, "
						+ "site_location, country, departement, roche_id) values ('");
				queryString.append(owner_id);
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

		loadDataForSolutionContactRelation("is_owner_component", solution_id, owner_id);
	}

	public void loadDataForDeputy(String id, Row row) {
		String deputy_roche_id = row.getCell(15).getStringCellValue();
		if (deputy_roche_id.length() > 8) {
			return;
		}
		String deputy_id = checkUserInDB(deputy_roche_id);
		if (deputy_id == null) {
			String solution_deputy = row.getCell(16).getStringCellValue();
			String name[] = solution_deputy.split(",");
			String deputy_first_name = "";
			String deputy_last_name = "";
			if (name.length == 2) {
				deputy_first_name = name[1];
				deputy_last_name = name[0];
			}

			String deputy_email = row.getCell(17).getStringCellValue();
			String deputy_site = row.getCell(18).getStringCellValue();
			String deputy_country = row.getCell(19).getStringCellValue();
			String deputy_department = row.getCell(20).getStringCellValue();

			deputy_id = UUID.randomUUID().toString();
			System.out.println(deputy_id);

			try {
				StringBuffer queryString = new StringBuffer("insert into contact(id, name, surname, email, "
						+ "site_location, country, departement, roche_id) values ('");
				queryString.append(deputy_id);
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

		loadDataForSolutionContactRelation("is_deputy_component", id, deputy_id);
	}

	public void loadDataForBO(String id, Row row) {
		String bo_roche_id = row.getCell(21).getStringCellValue();
		if (bo_roche_id.length() > 8) {
			return;
		}
		String bo_id = checkUserInDB(bo_roche_id);
		if (bo_id == null) {
			String solution_bo = row.getCell(22).getStringCellValue();
			String name[] = solution_bo.split(",");
			String bo_first_name = "";
			String bo_last_name = "";
			if (name.length == 2) {
				bo_first_name = name[1];
				bo_last_name = name[0];
			}

			String bo_email = row.getCell(23).getStringCellValue();
			String bo_site = row.getCell(24).getStringCellValue();
			String bo_country = row.getCell(25).getStringCellValue();
			String bo_department = row.getCell(26).getStringCellValue();

			bo_id = UUID.randomUUID().toString();
			System.out.println(bo_id);

			try {
				StringBuffer queryString = new StringBuffer("insert into contact(id, name, surname, email, "
						+ "site_location, country, departement, roche_id) values ('");
				queryString.append(bo_id);
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

		loadDataForSolutionContactRelation("is_bo_component", id, bo_id);
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

	public void loadRelations(String related_hpsm_id, String component_id) {
		try {
			ArrayList<String> list = new ArrayList<String>();
			String queryString = "select id from solution where hpsm_id = '" + related_hpsm_id + "'";
			System.out.println(queryString.toString());
			ResultSet rs = statement.executeQuery(queryString);
			while (rs.next()) {
				list.add(rs.getString(1));
			}
			
			for (String s: list) {
				String query = "insert into sc_relationship values ('" + s + "','" + component_id + "');";
				System.out.println(query);
				statement.execute(query);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
