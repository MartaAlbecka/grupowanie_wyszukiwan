package serialize;

import java.io.*;
import java.util.List;
import java.util.logging.Logger;

public class SerializerImpl implements Serializer {
    private static final Logger log = Logger.getLogger(SerializerImpl.class.getName());

    private String filePath = "serialize/%s.ser";

    @Override
    public void serialize(List<AllegroItem> items, String fileName) {
        try {
            File directory = new File(filePath).getParentFile();
            if (!directory.exists()) {
                directory.mkdirs();
            }

            FileOutputStream fos = new FileOutputStream(getFilePath(fileName));
            ObjectOutputStream out = new ObjectOutputStream(fos);
            out.writeObject(items);
            out.close();
            fos.close();
        } catch (FileNotFoundException e) {
            log.info("File: " + fileName + " doesn't exist. Serialization: SerializerImpl " + e);
        } catch (IOException e) {
            log.info("Error reading file:" + fileName + ". Serialization: SerializerImpl " + e);
        }
    }

    @Override
    public List<AllegroItem> deserialize(String fileName) {
        List<AllegroItem> list = null;
        try {
            String filePath = getFilePath(fileName);

            File directory = new File(filePath).getParentFile();
            if (!directory.exists()) {
                directory.mkdirs();
                return null;
            }

            FileInputStream fin = new FileInputStream(filePath);
            ObjectInputStream in = new ObjectInputStream(fin);

            list = (List<AllegroItem>) in.readObject();
            in.close();
            fin.close();
        } catch (FileNotFoundException e) {
            log.info("File: " + fileName + " doesn't exist. Deserialization: SerializerImpl " + e);
        } catch (IOException e) {
            log.info("Error reading file: " + fileName + ". Deserialization: SerializerImpl " + e);
        } catch (ClassNotFoundException e) {
            log.info("Error reading from file: " + fileName + ". Deserialization: SerializerImpl " + e);
        }
        return list;
    }

    @Override
    public String getFilePath(String fileName) {
        return String.format(filePath, fileName);
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
