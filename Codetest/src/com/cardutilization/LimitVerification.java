package com.cardutilization;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.cardutilization.model.EntityPOJO;
import com.cardutilization.util.Utility;

public class LimitVerification {

	public static void main(String[] args) {
		try {

			FileInputStream file = new FileInputStream(new File("src\\Inputfile\\Inputfile.xlsx"));

			XSSFWorkbook workbook = new XSSFWorkbook(file);

			XSSFSheet sheet = workbook.getSheet("Input");

			ArrayList<EntityPOJO> inputList = new ArrayList<>();

			for (int rowNo = 1; rowNo <= sheet.getLastRowNum(); rowNo++) {

				EntityPOJO inputExcelFile = new EntityPOJO();

				Row row = sheet.getRow(rowNo);
				for (int columnNo = row.getFirstCellNum(); columnNo <= row.getLastCellNum(); columnNo++) {
					Cell cell = row.getCell(columnNo);

					if (columnNo == 0 && cell != null) {
						inputExcelFile.setEntity(cell.getStringCellValue());
					}
					if (columnNo == 1 && cell != null) {
						inputExcelFile.setParent(cell.getStringCellValue());
					}
					if (columnNo == 2 && cell != null) {
						inputExcelFile.setLimit(cell.getNumericCellValue());
					}
					if (columnNo == 3 && cell != null) {
						inputExcelFile.setUtilisation(cell.getNumericCellValue());
					}

					inputExcelFile.setCombindUtilisation(0);
				}
				inputList.add(inputExcelFile);
			}

			List<EntityPOJO> root = Utility.getRoot(inputList);

			// Root fetched, row iterating over each Parent
			List<EntityPOJO> inputList1 = new ArrayList<>();

			for (EntityPOJO entityPOJO : inputList) {

				if (entityPOJO.getParent() != null
						&& inputList.stream().filter(c -> c.getEntity().trim().equals(entityPOJO.getParent().trim()))
								.collect(Collectors.toList()) != null) {

					entityPOJO.setParentObj(
							inputList.stream().filter(c -> c.getEntity().trim().equals(entityPOJO.getParent().trim()))
									.collect(Collectors.toList()).get(0));
				}

				inputList1.add(entityPOJO);

			}
			List<EntityPOJO> inputListFinal = Utility.populateChild(inputList1);

			List<EntityPOJO> list = Utility.populateGroup(root, inputListFinal);

			Map<String, List<EntityPOJO>> map = list.stream()
					.collect(Collectors.groupingBy(EntityPOJO::getGroup, Collectors.toList()));

			map.forEach((k, v) -> {

				System.out.println("Entities:" + k);

				v.forEach(c -> {

					EntityPOJO entityPOJO1 = Utility.combinedUtilization(c);

					if (entityPOJO1.getCombindUtilisation() <= entityPOJO1.getLimit()) {

					} else {

						System.out.println("Limit breach at " + entityPOJO1.getEntity() + " limit="
								+ entityPOJO1.getLimit() + " direct utilisation=" + entityPOJO1.getUtilisation()
								+ " combined utilisation=" + entityPOJO1.getCombindUtilisation());
					}
				});

			});

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}