package config;

import algorithms.common.ClusteringAlgorithm;
import algorithms.kmeans.KMeansEpsilon;

/**
 * Created on 10-Dec-17.
 */
public class Configuration {

    /**
     * An algorithm that will be used to perform clustering. For now only KMeansEpsilon is supported.
     */
    public static Class<? extends ClusteringAlgorithm> algorithm = KMeansEpsilon.class;

    /**
     * Indicates whether the results of auction download have to be serialized and saved into a file or not.
     * Serialization helps when the user queries multiple times for the same
     */
    public static boolean serialized = false;
}
