import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class helloWorld {



    public static void main(String[] args)  {


        Logger logger = Logger.getLogger(Constants.APPLICATION_LOGGER);

        File ratingArchive = new File("rating");

        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH) + 1;
        int year = cal.get(Calendar.YEAR);
        try {
            URL url = new URL("tlife.ru/files/spb/" + year + "_" + month + ".zip");
            FileUtils.copyURLToFile(url, ratingArchive);
        } catch (MalformedURLException e) {
            if(logger.isLoggable(Level.WARNING)){
                logger.warning("URL is incorrect" + e);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (ZipFile zipFile = new ZipFile(ratingArchive, ZipFile.OPEN_READ, Charset.forName("ISO-8859-1"))) {
            if(zipFile.size() > 0){
                Enumeration<? extends ZipEntry> entries = zipFile.entries();
                    int i =0;
                while(entries.hasMoreElements()){
                    ZipEntry entry = entries.nextElement();
                    InputStream stream = zipFile.getInputStream(entry);

                    File file = new File("rate"+i+".xls");
                    OutputStream outputStream = new FileOutputStream(file);
                    IOUtils.copy(stream, outputStream);
                    outputStream.close();
                    i++;
                }
            }
        }catch (IOException e) {
            e.printStackTrace();
        }



    }
}
//http://www.ttlife.ru/files/spb/2016_10.zip