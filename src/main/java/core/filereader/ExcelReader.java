package core.filereader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import com.monitorjbl.xlsx.StreamingReader;
import entity.dto.Event;
import org.apache.poi.ss.usermodel.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ExcelReader implements IExcelReader {

    private List<Event> events = new ArrayList<>();

    @Override
    public List<Event> read() {
        /*Входящий поток из файла NAMES.xlsx*/
        try (
                InputStream is = new FileInputStream(
                        new File("events.xlsx"));
                Workbook workbook = StreamingReader.builder()
                        .rowCacheSize(100)
                        .bufferSize(4096)
                        .open(is)) {
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            System.out.println(sheet.getSheetName());

            int i = 1;
            /*Проходит по строкам таблицы*/
            rowIterator.next();
            while (rowIterator.hasNext()) {
                boolean isBreak = false;
                Event tableCell = new Event();

                Row row = rowIterator.next();
                // Создание итератора для прохода по каждой ячейке таблицы внутри строки
                Iterator<Cell> cellIterator = row.cellIterator();
                int j = 1;
                while (cellIterator.hasNext()) {

                    Cell cell = cellIterator.next();
                    //tableList.setReg_number(cell.getNumericCellValue());

                    // Создание объекта для получения типа данных ячейки
                    CellType cellType = cell.getCellTypeEnum();
                    //Действия с данными производятся в зависимости от их типа
                    try {
                        switch (cellType) {
                            case _NONE:
                                System.out.print("");
                                System.out.print("\t");
                                break;
                            case NUMERIC:
                                tableCell.setId((int) cell.getNumericCellValue());
                                System.out.print(cell.getNumericCellValue());
                                System.out.print("\t");
                                break;
                            case STRING:
                                //Первая строка таблицы, где содержатся имена полей, выводится в консоль,
                                //но не помещается в таблицу базы данных
                                if (i == 0) {
                                    System.out.print(cell.getStringCellValue());
                                    System.out.print("\t");
                                }
                                else {
                                    //Значения попадают в базу данных, начиная со второй строки
                                    tableCell.setText(cell.getStringCellValue());

                                    System.out.print(cell.getStringCellValue());
                                    System.out.print("\t");
                                }
                                break;
                            case ERROR:
                                System.out.print("!");
                                System.out.print("\t");
                                break;

                        }
                        //Обработка исключения несоответствия формата ячейки
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                        isBreak = true;
                    }
                    j++;

                }
                i++;
                System.out.println("");

                //Строка таблицы попадает в таблицу базы данных если формат данных соответствует заданному
                //если не было разрывов в таблице Excel. Иначе осуществляется переходк следующей строке
                if (!isBreak)
                    events.add(tableCell);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return events;
    }
}
