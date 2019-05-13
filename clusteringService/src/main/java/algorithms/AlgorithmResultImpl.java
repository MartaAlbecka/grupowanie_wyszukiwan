package algorithms;

import serialize.AllegroFolderItem;
import serialize.AllegroItem;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created on 06-Nov-17.
 */
public class AlgorithmResultImpl implements AlgorithmResult {

    private static final Logger log = Logger.getLogger(AlgorithmResultImpl.class.getName());

    private List<Folder> folders;

    public AlgorithmResultImpl(List<Folder> folders) {
        this.folders = folders;
    }

    @Override
    public List<Folder> getFolders() {
        return folders;
    }

    @Override
    public List<AllegroFolderItem> getItemsClusterMapping() {
        List<Folder> folders = new ArrayList<>(this.folders);
        folders.sort((a, b) -> a.getAllegroItems().size() > b.getAllegroItems().size() ? -1 :
                a.getAllegroItems().size() < b.getAllegroItems().size() ? 1 :
                        a.getId() > b.getId() ? 1 : -1);
        List<AllegroFolderItem> itemsClusters = new ArrayList<>();
        for (Folder folder : this.folders) {
            for (AllegroItem allegroItem : folder.getAllegroItems()) {
                itemsClusters.add(new AllegroFolderItem(folder.getId(), allegroItem));
            }
        }
        return itemsClusters;
    }

    @Override
    public void serializeFolders(String fileName) {
        try {
            FileOutputStream fos = new FileOutputStream(AlgorithmResult.getFilePath(fileName));
            PrintWriter pw = new PrintWriter(fos);
            for (AllegroFolderItem item : getItemsClusterMapping()) {
                pw.println(item.toString());
            }
            pw.close();
            fos.close();
        } catch (FileNotFoundException e) {
            log.info("File: " + fileName + " doesn't exist. Serialization of Clusters" + e);
        } catch (IOException e) {
            log.info("Error reading file:" + fileName + ". Serialization of Clusters" + e);
        }
    }

}
