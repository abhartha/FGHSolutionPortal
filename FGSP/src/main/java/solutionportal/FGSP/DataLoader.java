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

import solutionportal.FGSP.Constants;

public class DataLoader {
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
			File f = new File(Constants.PATHOFEXCEL);
			XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(f));
			XSSFSheet sheet = workbook.getSheetAt(0);
			Iterator<Row> rowIterator = sheet.iterator();

			int rowNumber = 0;
			boolean complete = false;
			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();
				rowNumber++;
				if (rowNumber > 8) {
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
					// System.out.println(lst.checkUserInDB("RIZZID"));
				}
				if (complete) {
					break;
				}
			}

			XSSFSheet sheetComponents = workbook.getSheetAt(1);
			Iterator<Row> rowIteratorComponents = sheetComponents.iterator();

			int rowNumberComponents = 0;
			boolean isCompleteComponent = false;
			while (rowIteratorComponents.hasNext()) {
				Row row = rowIteratorComponents.next();
				rowNumberComponents++;
				
				System.out.println(row.getCell(28));
				if (rowNumberComponents > 7) {
					if ((row.getCell(3) == null || row.getCell(3).getNumericCellValue() == 0)) {
						System.out.println("Already empty");
						isCompleteComponent = true;
						break;
					}
					if (row.getCell(28).toString().equals("APS")) {
						String uniqueID = UUID.randomUUID().toString();
						System.out.println(uniqueID);
						LoadComponentTable lst = new LoadComponentTable(statement);
						lst.loadDataInComponentTable(uniqueID, row, false);
						lst.loadDataForOwner(uniqueID, row);
						lst.loadDataForDeputy(uniqueID, row);
						lst.loadDataForBO(uniqueID, row);
					}

				}
				if (isCompleteComponent) {
					break;
				}
			}

			// int rowNumberModule = 0;
			// boolean isCompleteModule = false;
			// Iterator<Row> rowIteratorModule = sheetComponents.iterator();
			// while (rowIteratorModule.hasNext()) {
			// Row row = rowIteratorModule.next();
			// rowNumberModule++;
			// if (row.getCell(28).equals("APM")) {
			//
			// if (rowNumberModule > 7) {
			// if ((row.getCell(3) == null ||
			// row.getCell(3).getNumericCellValue() == 0)) {
			// isCompleteModule = true;
			// break;
			// }
			//
			// String uniqueID = UUID.randomUUID().toString();
			//
			// LoadComponentTable lst = new LoadComponentTable(statement);
			// lst.loadDataInComponentTable(uniqueID, row, true);
			// lst.loadDataForOwner(uniqueID, row);
			// lst.loadDataForDeputy(uniqueID, row);
			// lst.loadDataForBO(uniqueID, row);
			//
			// }
			//
			// }
			// if (isCompleteModule) {
			// break;
			// }
			// }

			workbook.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}