package algorithms;

import serialize.AllegroFolderItem;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created on 27-Oct-17.
 *
 * Result of clusterization of auctions.
 */
public interface AlgorithmResult {

    Logger log = Logger.getLogger(AlgorithmResultImpl.class.getName());
    String filePath = "../serialize/serializeClusters/%s.ser";

    /**
     * Gets a list of folders.
     *
     * @return list of folders
     */
    List<Folder> getFolders();

    /**
     * Serializes folders to a file.
     *
     * @param fileName name of the file
     */
    void serializeFolders(String fileName);

    /**
     * Creates AllegroFolderItems from folders.
     *
     * @return list of AllegroFolderItems
     */
    List<AllegroFolderItem> getItemsClusterMapping();

    /**
     * Deserializes folders from a file.
     *
     * @param fileName name of the file
     * @return deserialized list of folders
     */
    static List<AllegroFolderItem> deserializeFolders(String fileName) {
        List<AllegroFolderItem> clusters = new ArrayList<>();
        try {
            FileInputStream fin = new FileInputStream(getFilePath(fileName));
            BufferedReader br = new BufferedReader(new InputStreamReader(fin));
            String line = null;
            while ((line = br.readLine()) != null) {
                String[] split = line.split("\t");
                AllegroFolderItem item =
                        new AllegroFolderItem(Integer.valueOf(split[1]), split[7], Long.parseLong(split[5]), split[3]);
                clusters.add(item);
            }
            br.close();

        } catch (FileNotFoundException e) {
            log.info("File: " + fileName + " doesn't exist. Deserialization of folders" + e);
        } catch (IOException e) {
            log.info("Error reading file: " + fileName + ". Deserialization of folders" + e);
        }
        return clusters;
    }

    /**
     * Gets a relative file path for a given file name.
     * @param fileName name of the file
     * @return relative path
     */
    static String getFilePath(String fileName) {
        return String.format(filePath, fileName);
    }
}
