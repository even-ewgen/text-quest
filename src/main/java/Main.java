import core.filereader.ExcelReader;
import hibernate.dao.EventDaoImpl;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
    //Проверка работы с HBase
        //new EventDaoImpl().save(new Event(0, "Hi"));
        //System.out.println(new EventDaoImpl().findAll());
        //new EventDaoImpl().update(new Event(0, "Ho"));
        //new EventDaoImpl().delete(new EventDaoImpl().findById(0));

        //new EventDaoImpl().save(new ExcelReader().getEvents());
    }
}