
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;


import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class TTRating {

    public static void main(String[] args) {
        //String zipName = "rating";
        //String zipLink = "http://www.ttlife.ru/files/spb/";
        //TTRating.decompressRatingArchive(downloadArchive(zipName, zipLink));
        parseXlsFile();
    }

    private static File downloadArchive(String zipName, String zipLink) {
        Logger logger = Logger.getLogger(Constants.APPLICATION_LOGGER);
        File ratingArchive = new File(zipName);
        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH) + 1;
        int year = cal.get(Calendar.YEAR);
        try {
            URL url = new URL(zipLink + year + "_" + month + ".zip");
            FileUtils.copyURLToFile(url, ratingArchive);
        } catch (MalformedURLException e) {
            if (logger.isLoggable(Level.WARNING)) {
                logger.warning("URL is incorrect" + e);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ratingArchive;
    }

    private static void decompressRatingArchive(File ratingArchive) {
        try (ZipFile zipFile = new ZipFile(ratingArchive, ZipFile.OPEN_READ, Charset.forName("ISO-8859-1"))) {
            if (zipFile.size() > 0) {
                Enumeration<? extends ZipEntry> entries = zipFile.entries();
                int i = 0;
                while (entries.hasMoreElements()) {
                    ZipEntry entry = entries.nextElement();
                    InputStream stream = zipFile.getInputStream(entry);

                    File file = new File("rate" + i + ".xls");
                    OutputStream outputStream = new FileOutputStream(file);
                    IOUtils.copy(stream, outputStream);
                    outputStream.close();
                    i++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<Sportsman> parseXlsFile() {
        List<Sportsman> tennisists = new ArrayList<Sportsman>();
        try {
            FileInputStream file = new FileInputStream(new File("C:\\Users\\Роман\\Desktop\\tableTennisRating\\rate0.xls"));

            HSSFWorkbook workbook = new HSSFWorkbook(file);

            HSSFSheet sheet = workbook.getSheetAt(0);

            Iterator<Row> rowIterator = sheet.iterator();
            if (rowIterator.hasNext()) {
                rowIterator.next();
            }
            while (rowIterator.hasNext()) {
                HSSFRow row = (HSSFRow) rowIterator.next();
                //Получаем ячейки из строки по номерам столбцов
                HSSFCell nameCell = row.getCell(Constants.NAME_COLUMN_NUMBER);
                HSSFCell teamCell = row.getCell(Constants.TEAM_COLUMN_NUMBER);
                HSSFCell ratingCell = row.getCell(Constants.TEAM_COLUMN_NUMBER);
                // Если в первом столбце нет данных, то контакт не создаём
                if (nameCell != null) {
                    Sportsman sportsman = new Sportsman();
                    sportsman.setName(nameCell.getStringCellValue()); //Получаем строковое значение из ячейки

                    sportsman.setTeam("");
                    if (teamCell != null && !"".equals(teamCell.getStringCellValue())) {
                        sportsman.setTeam(teamCell.getStringCellValue()); //Адрес - строка
                    }

                    sportsman.setRating(0);
                    if (ratingCell != null && !"".equals(ratingCell.getStringCellValue())) {
                        sportsman.setRating(ratingCell.getNumericCellValue());
                    }
                    tennisists.add(sportsman);

                }
            }
            file.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tennisists;
    }
}
//http://www.ttlife.ru/files/spb/2016_10.zip