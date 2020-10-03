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

public class EventReaderImpl implements IReader<Event> {

    private static final String FILE_NAME = "events.xlsx";

    @Override
    public List<Event> read() throws IOException {
        List<Event> events = new ArrayList<>();
        /*Входящий поток из файла NAMES.xlsx*/
        try (
                InputStream is = new FileInputStream(
                        new File(FILE_NAME));
                Workbook workbook = StreamingReader.builder()
                        .rowCacheSize(100)
                        .bufferSize(4096)
                        .open(is)) {
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            System.out.println(sheet.getSheetName());

            /*Проходит по строкам таблицы*/
            rowIterator.next();
            while (rowIterator.hasNext()) {
                boolean isBreak = false;
                Event tableCell = new Event();

                Row row = rowIterator.next();
                // Создание итератора для прохода по каждой ячейке таблицы внутри строки
                Iterator<Cell> cellIterator = row.cellIterator();
                while (cellIterator.hasNext()) {

                    Cell cell = cellIterator.next();
                    //tableList.setReg_number(cell.getNumericCellValue());

                    // Создание объекта для получения типа данных ячейки
                    CellType cellType = cell.getCellTypeEnum();
                    //Действия с данными производятся в зависимости от их типа
                    try {
                        switch (cellType) {
                            case NUMERIC:
                                tableCell.setId((int) cell.getNumericCellValue());
                                break;
                            case STRING:
                                //Значения попадают в базу данных, начиная со второй строки
                                tableCell.setText(cell.getStringCellValue());
                                break;
                            case ERROR:
                                System.out.println("error in EventReader");
                                break;

                        }
                        //Обработка исключения несоответствия формата ячейки
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                        isBreak = true;
                    }
                }
                //Строка таблицы попадает в таблицу базы данных если формат данных соответствует заданному
                //если не было разрывов в таблице Excel. Иначе осуществляется переходк следующей строке
                if (!isBreak)
                    events.add(tableCell);
            }
        }
        return events;
    }
}
