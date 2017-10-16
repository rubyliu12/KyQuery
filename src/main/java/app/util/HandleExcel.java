package app.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import javax.servlet.http.Part;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class HandleExcel {

  public String handleExcelUsePOI(Part file) throws IOException {
    InputStream inputStream = file.getInputStream();
    Workbook workbook = new XSSFWorkbook(inputStream);
    Sheet sheet = workbook.getSheetAt(0);
    Iterator<Row> iterator = sheet.iterator();
    JSONArray jObject = new JSONArray();
    String[] indexes = {"A", "B", "C", "D", "E", "F", "G", "H", "name", "mobile", "K", "L", "M",
        "N", "O",
        "certNo", "Q", "bankCardNo", "S", "T", "U", "address", "W", "X", "Y", "Z", "AA", "AB", "AC",
        "AD", "AE", "AF",
        "AG", "AH", "AI", "AJ", "AK", "AL", "AM", "AN", "AO", "AP", "AQ", "AR", "AS", "AT", "AU",
        "AV", "AW", "AX", "AY", "AZ", "BA", "BB", "BC", "BD", "BE", "BF", "BG", "BH", "BI", "BJ",
        "BK", "BL", "BM", "BN", "BO", "BP", "BQ", "BR", "BS", "BT", "BU", "BV", "BW", "BX", "BY",
        "BZ", "CA", "CB", "CC", "CD", "CE", "CF", "CG", "CH", "CI", "CJ", "CK", "CL"};
    JSONArray ids = JSONArray.fromObject(indexes);
//    if (iterator.hasNext()) {
//      iterator.next();
//    }
    while (iterator.hasNext()) {

      Row nextRow = iterator.next();
      Iterator<Cell> cellIterator = nextRow.cellIterator();

      JSONArray tmpJsonObject = new JSONArray();
      while (cellIterator.hasNext()) {
        Cell cell = cellIterator.next();
        tmpJsonObject.add(cellToObject(cell));
      }
      jObject.add(tmpJsonObject.toJSONObject(ids).toString());
      //dao.writeDataBase(cellIterator);
      workbook.close();
      inputStream.close();
    }
    return jObject.toString();
  }

  private Object cellToObject(Cell cell) {

    int type = cell.getCellType();

    if (type == Cell.CELL_TYPE_STRING) {
      return cleanString(cell.getStringCellValue());
    }

    if (type == Cell.CELL_TYPE_BOOLEAN) {
      return cell.getBooleanCellValue();
    }

    if (type == Cell.CELL_TYPE_NUMERIC) {

      //if (cell.getCellStyle().getDataFormatString().contains("%")) {
      //  return cell.getNumericCellValue() * 100;
      //}

      return numeric(cell);
    }

    if (type == Cell.CELL_TYPE_FORMULA) {
      switch (cell.getCachedFormulaResultType()) {
        case Cell.CELL_TYPE_NUMERIC:
          return numeric(cell);
        case Cell.CELL_TYPE_STRING:
          return cleanString(cell.getRichStringCellValue().toString());
      }
    }



    return null;

  }

  private String cleanString(String str) {
    return str.replace("\n", "").replace("\r", "");
  }

  private Object numeric(Cell cell) {

    if (HSSFDateUtil.isCellDateFormatted(cell)) {
      Date date = cell.getDateCellValue();
      return DateFormatUtils.format(date, "yyyy-MM-dd");
    }
    return new DecimalFormat("#").format(cell.getNumericCellValue());
//    return Double.valueOf(cell.getNumericCellValue());
  }

  public static String save(String fileName, String result) throws IOException {
    String excelFileName = "src/main/resources/upload/" + fileName;//name of excel file

    String sheetName = "查询结果";//name of sheet

    Workbook wb = new XSSFWorkbook();
    Sheet sheet = wb.createSheet(sheetName);
    JSONArray results = JSONArray.fromObject(result);
    //iterating r number of rows
    Iterator<JSONObject> r = results.iterator();
    int rowIndex = 0;
    while (r.hasNext()) {
      Row row = sheet.createRow(rowIndex);
      JSONObject content = r.next();
      int cellIndex = 0;
      for (Object s : content.values()) {
        Cell cell = row.createCell(cellIndex);
        cell.setCellValue(s.toString());
        cellIndex++;
      }
      rowIndex++;
    }
    FileOutputStream fileOut = new FileOutputStream(excelFileName);
    //write this workbook to an Outputstream.
    wb.write(fileOut);
    fileOut.flush();
    fileOut.close();
    return excelFileName;
  }
}
