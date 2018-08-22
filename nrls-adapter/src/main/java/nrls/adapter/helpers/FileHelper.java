package nrls.adapter.helpers;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;

import nrls.adapter.model.Nrls;
import nrls.adapter.model.task.Task;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.stream.Stream;

import org.jboss.logging.Logger;
import org.springframework.stereotype.Service;

@Service
public class FileHelper {

    private static final Logger LOG = Logger.getLogger(FileHelper.class);
    private FileReader reader;
    private ObjectInputStream inputStream;

    public static boolean writeObjectToFileAsXML(String filePath, Object object) {

        boolean success = true;
        FileWriter fileWriter = null;

        try {
            File file = new File(filePath);
            file.getParentFile().mkdirs();
            fileWriter = new FileWriter(file);
            XStream xstream = new XStream();        // Using 'XStream' as it produces more readable XML output than JAXB and is quicker that XMLEncoder
            xstream.processAnnotations(Nrls.class);
            xstream.toXML(object, fileWriter);
        } catch (IOException ex) {
            LOG.error("Error converting object to XML when saving to file: " + ex.getMessage());
            success = false;
        } finally {
            try {
                if (null != fileWriter) {
                    fileWriter.close();
                }
            } catch (IOException fileCloseEx) {
                LOG.error("Error closing FileWriter after writing xml to file: " + fileCloseEx.getMessage());
                success = false;
            }
        }

        return success;
    }
    
    public ArrayList<Path> getFileList(String folderPath) {
    	ArrayList<Path> files = new ArrayList<Path>();
    	try (Stream<Path> filePathStream=Files.walk(Paths.get(folderPath),1)) {
    	    filePathStream.forEach(filePath -> {
    	        if (Files.isRegularFile(filePath)) {
    	            files.add(filePath);
    	        }
    	    });
    	} catch (IOException readingEx) {
    		LOG.error("Error reading folder: " + readingEx.getMessage());
		}
    	return files;
    }

    public ObjectInputStream getObjectInputStream(String filePath) {

        try {
            reader = new FileReader(filePath);
            XStream xstream = new XStream(new StaxDriver());
            xstream.alias("Task", Task.class);
            xstream.processAnnotations(Task.class);
            inputStream = xstream.createObjectInputStream(reader);

        } catch (FileNotFoundException fileNotFoundEx) {
            LOG.error("Error opening File: " + fileNotFoundEx.getMessage());
        } catch (IOException openingInputStreamEx) {
            LOG.error("Error opening Input Stream: " + openingInputStreamEx.getMessage());
        }

        return inputStream;
    }

    public void closeFile() {
        try {
            inputStream.close();
            reader.close();
        } catch (IOException closeStreamEx) {
            LOG.error("Error closing Input Stream: " + closeStreamEx.getMessage());
        }
    }

    public static void archiveFile(String filePath) {

        File fromFile = new File(filePath);
        String fileName = fromFile.getName();

        String newFilePath = filePath.replace(fileName, "archive/" + fileName + "_" + formatCurrentDate());
        File toFile = new File(newFilePath);
        toFile.getParentFile().mkdirs();

        if (!fromFile.renameTo(toFile)) {
            LOG.error("Error archiving Tasks file (" + filePath + ")");
        }
    }
    
    public static String formatCurrentDate(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss-SSS");
        return simpleDateFormat.format(new Date());
    }
    
    public static String formatDate(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss.SSS");
        return simpleDateFormat.format(new Date());
    }

}