package solutionportal.FGSP;

import java.io.*;
import java.sql.DriverManager;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import java.util.*;

import java.sql.Connection;
import java.sql.Statement;
import java.util.UUID;

public class DataLoader {
	final String pathOfExcel = "C://Users/bharthaa/Documents/Marco2Polo/marco2polo/Solution.xlsx";
	public static Statement statement;

	public Statement getDatabaseConnection(String db_connect_string, String db_userid, String db_password) {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			Connection conn = DriverManager.getConnection(db_connect_string);
			System.out.println("connected");
			Statement statement = conn.createStatement();
			return statement;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) {
		DataLoader connServer = new DataLoader();
		statement = connServer.getDatabaseConnection(
				"jdbc:sqlserver://RMUMCSHSQL4\\INST3;databaseName=TST_FGHR_SolutionMap;integratedSecurity=true;", "",
				"");
		connServer.readDataFromExcel();
	}

	public void readDataFromExcel() {
		try {
			File f = new File(pathOfExcel);
			XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(f));
			XSSFSheet sheet = workbook.getSheetAt(0);
			Iterator<Row> rowIterator = sheet.iterator();

			int rowNumber = 0;
			boolean complete = false;
			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();
				rowNumber++;
				if (rowNumber > 7) {
					if (row.getCell(1) == null || row.getCell(1).getNumericCellValue() == 0) {
						complete = true;
						break;
					}

					String uniqueID = UUID.randomUUID().toString();

					LoadSolutionTable lst = new LoadSolutionTable(statement);
					lst.loadDataInSolutionTable(uniqueID, row);
					lst.loadDataForOwner(uniqueID, row);
					lst.loadDataForDeputy(uniqueID, row);
					lst.loadDataForBO(uniqueID, row);
					//System.out.println(lst.checkUserInDB("ABHARTHA"));
				}
				if (complete) {
					break;
				}
			}
			workbook.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}