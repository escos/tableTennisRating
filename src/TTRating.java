
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;


import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.Iterator;
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

    private static  File downloadArchive(String zipName, String zipLink) {
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

    private static void parseXlsFile() {
        try
        {
            FileInputStream file = new FileInputStream(new File("C:\\Users\\Роман\\Desktop\\tableTennisRating\\test.xls"));

            HSSFWorkbook workbook = new HSSFWorkbook(file);

            //Get first/desired sheet from the workbook
            HSSFSheet sheet = workbook.getSheetAt(0);

            //Iterate through each rows one by one
            Iterator<Row> rowIterator = sheet.iterator();
            while (rowIterator.hasNext())
            {
                Row row = rowIterator.next();
                //For each row, iterate through all the columns
                Iterator<Cell> cellIterator = row.cellIterator();

                while (cellIterator.hasNext())
                {
                    Cell cell = cellIterator.next();
                    //Check the cell type and format accordingly
                    switch (cell.getCellType()) {
                        case Cell.CELL_TYPE_STRING:
                            System.out.print(cell.getStringCellValue());
                            break;
                        case Cell.CELL_TYPE_NUMERIC:
                            System.out.print(cell.getNumericCellValue());
                            break;
                    }
                    System.out.print(" - ");
                }
                System.out.println();
            }
            file.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
//http://www.ttlife.ru/files/spb/2016_10.zip