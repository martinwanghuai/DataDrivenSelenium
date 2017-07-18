package utility;

import static org.apache.poi.ss.usermodel.Cell.CELL_TYPE_BOOLEAN;
import static org.apache.poi.ss.usermodel.Cell.CELL_TYPE_FORMULA;
import static org.apache.poi.ss.usermodel.Cell.CELL_TYPE_NUMERIC;
import static org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.cgg.model.TestingStep;
import com.csvreader.CsvWriter;
import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;

public class ExcelUtils {
	private XSSFSheet ExcelWSheet;
	private XSSFWorkbook ExcelWBook;
	private XSSFCell Cell;
	private XSSFRow Row;

	public ExcelUtils(String excelPath, String sSheetName){

		try {
			FileInputStream ExcelFile = new FileInputStream(excelPath);
			ExcelWBook = new XSSFWorkbook(ExcelFile);
			ExcelWSheet = ExcelWBook.getSheet(sSheetName);
		} catch (Exception e) {
			Log.error(e.getMessage());
		}
	}
	
	public boolean resetExcelWSheet(final String sSheetName){
		
		boolean result = true;
		try {
			ExcelWSheet = ExcelWBook.getSheet(sSheetName);
			
		} catch (Exception e) {
			Log.error(e.getMessage());
			result = false;
		}
		
		return result;
	}
	
	public String getCellValue(final Cell cell) {

		String cellValue = "";

		if (cell != null) {
			switch (cell.getCellType()) {
			case CELL_TYPE_STRING:
				cellValue = cell.getRichStringCellValue().getString();
				break;

			case CELL_TYPE_NUMERIC:
				if (DateUtil.isCellDateFormatted(cell)) {
					final Date date = cell.getDateCellValue();
					cellValue = IOUtils.dateToString(date);
				} else {
					cell.setCellType(CELL_TYPE_STRING); // treat numeric
														// cells as
														// String type
					cellValue = cell.getRichStringCellValue().getString();
				}
				break;

			case CELL_TYPE_BOOLEAN:
				cellValue = cell.getBooleanCellValue() + "";
				break;

			case CELL_TYPE_FORMULA:
				cellValue = this
						.getCellValue(ExcelWBook.getCreationHelper().createFormulaEvaluator().evaluateInCell(cell));
				break;

			default:
				cellValue = "";
				break;
			}
		}

		return cellValue;
	}

	public String getCellData(int RowNum, int ColNum){

		String cellData = "";
		try {
			Cell = ExcelWSheet.getRow(RowNum).getCell(ColNum);
			cellData = Cell.getStringCellValue();
		
		} catch (Exception e) {
			Log.error(e.getMessage());
		}
		return cellData;
	}

	@SuppressWarnings("static-access")
	public void setCellData(String sResult, int RowNum, int ColNum){

		try {
			Row = ExcelWSheet.getRow(RowNum);
			Cell = Row.getCell(ColNum, Row.RETURN_BLANK_AS_NULL);
			if (Cell == null) {
				Cell = Row.createCell(ColNum);
				Cell.setCellValue(sResult);
			} else {
				Cell.setCellValue(sResult);
			}
			FileOutputStream fileOut = new FileOutputStream(
					Constant.USRDIR + Constant.PATH_TESTDATA + Constant.FILE_TESTDATA);
			ExcelWBook.write(fileOut);
			fileOut.flush();
			fileOut.close();
		} catch (Exception e) {
			Log.error(e.getMessage());
		}
	}

	public static void writeToCSVFile(String sResult, final TestingStep testingStep, String testCaseSheetName){

		String filePath = Constant.USRDIR + Constant.PATH_RESULTSDATA + testCaseSheetName + "Results.csv";
		boolean isFileExist = new File(filePath)
				.exists();
		try {
			// use FileWriter constructor that specifies open for appending
			if(!isFileExist){
				BufferedWriter writer = new BufferedWriter(new FileWriter(new File(filePath)));
				writer.write("");
				writer.flush();
				writer.close();
			}
			CsvWriter testcases = new CsvWriter(new FileWriter(
					filePath, true), ',');

			// if the file didn't already exist then we need to write out
			// the header line
			if (!isFileExist) {

				testcases.write("TimeStamp");
				testcases.write("TestStepNo");
				testcases.write("TestStepName");
				testcases.write("TestResult");
				// end the record
				testcases.endRecord();

			}
			testcases.write(IOUtils.dateToString(new Date()));
			testcases.write(testingStep.getStepNo());
			testcases.write(testingStep.getStepName());
			testcases.write(sResult);
			testcases.endRecord();
			testcases.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public int getRowContains(String sTestCaseName, int colNum){

		int i = 0;
		try {
			int rowCount = this.getRowUsed();
			for (; i < rowCount; i++) {
				if (this.getCellData(i, colNum).equalsIgnoreCase(sTestCaseName)) {
					break;
				}
			}

		} catch (Exception e) {
			Log.error("Class ExcelUtil | Method getRowContains | Exception desc : " + e.getMessage());
		}
		return i;
	}

	public int getUniqueRow(String sStepNo, String sStepName) {

		int i = 1;
		String sStep;
		String sStepN;
		int var;
		try {
			int rowCount = this.getRowUsed();
			for (; i < rowCount; i++) {
				Cell = ExcelWSheet.getRow(i).getCell(Constant.COL_STEPNO);
				if (Cell.getCellType() == 0) {
					var = (int) Cell.getNumericCellValue();
					sStep = String.valueOf(var);
				} else {
					sStep = Cell.toString();
				}

				Cell = ExcelWSheet.getRow(i).getCell(Constant.COL_STEPNAME);
				if (Cell.getCellType() == 0) {
					var = (int) Cell.getNumericCellValue();
					sStepN = String.valueOf(var);
				} else {
					sStepN = Cell.toString();
				}

				if (sStep.equals(sStepNo.replace(".0", "")) && sStepN.equalsIgnoreCase(sStepName)) {
					break;
				}
			}

		} catch (Exception e) {
			Log.error(e.getMessage());
		}
		return i;
	}

	public int getColumnContains(String colVal, int rowNum) {

		int i = 0;
		try {
			int colCount = this.getColumnCount();
			for (; i < colCount; i++) {
				if (this.getCellData(rowNum, i).equalsIgnoreCase(colVal)) {
					break;
				}
			}
			
		} catch (Exception e) {
			Log.error( e.getMessage());
		}
		return i;
	}

	public int getRowUsed() {

		int RowCount = -1;
		try {
			RowCount = ExcelWSheet.getLastRowNum();
		} catch (Exception e) {
			Log.error(e.getMessage());
		}
		
		return RowCount;
	}

	public int getColumnCount(){

		int columnCount = -1;
		try {
			Row = ExcelWSheet.getRow(0);
			columnCount = Row.getLastCellNum();
			
		} catch (Exception e) {
			Log.error(e.getMessage());
		}
		
		return columnCount;
	}

	public List<String> getColumnData(final int iCol){
		
		List<List<String>> rowData = getAllRows();
		
		return FluentIterable.from(rowData).transform(new Function<List<String>, String>(){
		
			@Override
			public String apply(final List<String> objs){
				
				return objs.get(iCol);
			}
		}).filter(Checker.FILTER_BLANK).toList();
		
	}
	
	public List<String> getColumnData(final String columnName) {
	
		try {
			int columnIndex = 0;
			int colCount = this.getColumnCount();
			for (; columnIndex < colCount; columnIndex ++) {
				if (this.getCellData(0, columnIndex).equalsIgnoreCase(columnName)) {
					break;
				}
			}
			
			if(columnIndex == colCount){
				System.out.println("Cannot find column with name:" + columnName);
			}
			
			return this.getColumnData(columnIndex);
			
		} catch (Exception e) {
			Log.error(e.getMessage());
			return null;
		}
	}
	
	public List<List<String>> getAllRows() {

		List<List<String>> arrayExcelData = Lists.newArrayList();
		try {
			int totalNoOfRows = ExcelWSheet.getLastRowNum();
			Row = ExcelWSheet.getRow(0);
			int totalNoOfCols = Row.getLastCellNum();

			for (int rowNum = 1; rowNum <= totalNoOfRows; rowNum++) {
				try {
					List<String> sRowActionData = Lists.newArrayList();
					Row = ExcelWSheet.getRow(rowNum);
					if(Row != null){
						for (int colNum = 0; colNum < totalNoOfCols; colNum++) {
							
							XSSFCell cell2 = Row.getCell(colNum);
							final String cellValue = this.getCellValue(cell2);
							sRowActionData.add(cellValue);	
						}

						if (sRowActionData.size() != 0) {
							arrayExcelData.add(sRowActionData);
						}	
					}
				} catch (Exception e) {
					Log.error(e.getMessage());
				}
			}
		} catch (Exception e) {
			Log.error( e.getMessage());
		}
		return arrayExcelData;
	}

	public List<String> locateRowData(String excelFile, String sTestCaseName, String sObjectId) {

		ArrayList<String> srowData = Lists.newArrayList();
		ExcelUtils util = new ExcelUtils(excelFile, sTestCaseName);
		try {
			int iRow = util.getRowContains(sObjectId, 0);
			util.setRow(util.getExcelWSheet().getRow(0));
			int iCols = util.getRow().getLastCellNum();
			util.setRow(util.getExcelWSheet().getRow(iRow));
			for (int i = 1; i < iCols; i++) {
				if (util.getRow().getCell(i) == null)
					srowData.add("");
				else
					srowData.add(util.getRow().getCell(i).toString());
			}
		} catch (Exception e) {
			Log.error(e.getMessage());
		}
		return srowData;
	}

	public static List<String> locateColumnData(String excelFile, String sTestCaseName, String sObjectId, String sDataId, String sArgs){

		ArrayList<String> scolumnData = Lists.newArrayList();
		ExcelUtils util = new ExcelUtils(excelFile, sTestCaseName);
		try {
			int iCol;
			int iRow = util.getRowContains(sDataId, 0);
			if (sArgs == "") {
				iCol = util.getColumnContains(sObjectId, 0);
			} else {
				iCol = util.getColumnContains(sArgs, 0);
			}
			util.setRow(util.getExcelWSheet().getRow(iRow));
			if (util.getRow().getCell(iCol) != null) {
				if (util.getRow().getCell(iCol).getCellType() == 0) {
					int i = (int) util.getRow().getCell(iCol).getNumericCellValue();
					// strCellValue = String.valueOf(i);
					scolumnData.add(String.valueOf(i));
				} else {
					scolumnData.add(util.getRow().getCell(iCol).toString());
				}
			}
		} catch (Exception e) {
			Log.error(e.getMessage());
		}
		return scolumnData;
	}

	public void saveIntoExcelColumns(final List<String> refinedMissingKeys, final String saveFileName, int columnNum){
		
		try {
			for (int rowNum = 0; rowNum < refinedMissingKeys.size(); rowNum++) {
					Row = ExcelWSheet.getRow(rowNum);
					if(Row == null){
						Row = ExcelWSheet.createRow(rowNum);
					}
					XSSFCell cell = Row.getCell(columnNum);
					if(cell == null){
						cell = Row.createCell(columnNum);
					}
					
					cell.setCellValue(refinedMissingKeys.get(rowNum));
			}
//			XSSFFormulaEvaluator.evaluateAllFormulaCells(ExcelWBook);
			ExcelWBook.write(new FileOutputStream(saveFileName));
		} catch (Exception e) {
			e.printStackTrace();
			Log.error( e.getMessage());
		}
	}
	
	public void closeExcels(String spath) {
		try {
			FileOutputStream fileOut = new FileOutputStream(spath);
			fileOut.flush();
			fileOut.close();
		} catch (Exception e) {
			Log.error(e.getMessage());
		}
	}

	public XSSFSheet getExcelWSheet() {
		return ExcelWSheet;
	}

	public void setExcelWSheet(XSSFSheet excelWSheet) {
		ExcelWSheet = excelWSheet;
	}

	public XSSFWorkbook getExcelWBook() {
		return ExcelWBook;
	}

	public void setExcelWBook(XSSFWorkbook excelWBook) {
		ExcelWBook = excelWBook;
	}

	public XSSFCell getCell() {
		return Cell;
	}

	public void setCell(XSSFCell cell) {
		Cell = cell;
	}

	public XSSFRow getRow() {
		return Row;
	}

	public void setRow(XSSFRow row) {
		Row = row;
	}
}