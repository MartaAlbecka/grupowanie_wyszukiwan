package serialize;

import java.util.List;

public interface Serializer {
    /**
     * Method serialize allegro items to a file
     *
     * @param list     of allegro items for serialization
     * @param fileName name of the file to serialize items to
     */
    void serialize(List<AllegroItem> list, String fileName);

    /**
     * Method deserialize allegro items from a file
     *
     * @param fileName name of the file for deserialization
     * @return List of items deserialized from a file
     */
    List<AllegroItem> deserialize(String fileName);

    /**
     * Gets a relative file path for a given file name
     *
     * @param fileName name of the file that is to be serialized or deserialized
     * @return relative file path
     */
    String getFilePath(String fileName);

    void setFilePath(String filePath);
}
