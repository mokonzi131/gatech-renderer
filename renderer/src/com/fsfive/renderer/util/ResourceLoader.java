package com.fsfive.renderer.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Michael on 6/3/2014.
 */
public class ResourceLoader {
    private static Logger LOGGER = Logger.getLogger(ResourceLoader.class.getName());

    public static Properties getProperties(String filename) {
        Properties properties = new Properties();
        Path path;
        try {
            path = accessFile(filename);
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "Failed to access properties file", e);
            return null;
        }

        // load the file
        try (InputStream stream = Files.newInputStream(path)) {
            properties.load(stream);
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "Failed to load properties file", e);
            return null;
        }

        return properties;
    }

    /**
     * Create access to a file
     * @param filename
     * @return A valid Path object for the file
     * @throws IOException
     */
    public static Path accessFile(String filename) throws IOException {
        LOGGER.log(Level.INFO, "Accessing File " + filename);

        // find resource location
        URL resource = ResourceLoader.class.getResource(filename);
        if (resource == null) {
            LOGGER.log(Level.WARNING, "Failed to find resource " + filename);
            throw new FileNotFoundException();
        }

        // get resource path
        Path path = null;
        try {
            path = Paths.get(resource.toURI());
        } catch (URISyntaxException e) {
            LOGGER.log(Level.WARNING, "Failed to access resource location", e);
            throw new IOException();
        }

        // check the file
        if (!Files.exists(path)) {
            LOGGER.log(Level.WARNING, "File doesn't exist");
            throw new IOException();
        }

        if (!Files.isReadable(path)) {
            LOGGER.log(Level.WARNING, "File is not accessible for reading");
            throw new IOException();
        }

        return path;
    }
}
