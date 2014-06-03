package com.fsfive.renderer.util;

import com.fsfive.renderer.render.RenderObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Michael on 6/3/2014.
 */
public class RawObjectLoader {
    private static Logger LOGGER = Logger.getLogger(RawObjectLoader.class.getName());

    public static List<float[]> loadRawFile(String filename) {
        LOGGER.log(Level.INFO, "Loading File " + filename);

        // retrieve the lines from the file
        List<String> lines = readLines(filename);
        if (lines == null || lines.size() <= 0) {
            LOGGER.log(Level.WARNING, "Invalid or empty file, aborting");
            return null;
        }

        // parse the lines
        List<float[]> data = new ArrayList<>();
        for (String line : lines) {
            if (line.length() == 0) continue;

            // capture individual entries
            String[] entries = line.split(" ");
            float[] values = new float[entries.length];
            for (int i = 0; i < entries.length; i++) {
                values[i] = Float.parseFloat(entries[i]);
            }
            data.add(values);
        }

        return data;
    }

    /**
     * Break a resource file into lines
     * @param filename The name of the resource in the classpath
     * @return A list of lines, or null if the filename is invalid
     */
    public static List<String> readLines(String filename) {
        LOGGER.log(Level.INFO, "Reading File " + filename);

        List<String> lines = new ArrayList<>();

        // find resource location
        URL resource = RawObjectLoader.class.getResource(filename);
        if (resource == null) {
            LOGGER.log(Level.WARNING, "Failed to find resource " + filename);
            return null;
        }


        // get the resource path
        Path path = null;
        try {
            path = Paths.get(resource.toURI());
        } catch (URISyntaxException e) {
            LOGGER.log(Level.WARNING, "Failed to access resource location", e);
            return null;
        }

        // check the file
        if (!Files.exists(path)) {
            LOGGER.log(Level.WARNING, "File doesn't exist");
            return null;
        }

        if (!Files.isReadable(path)) {
            LOGGER.log(Level.WARNING, "File is not accessible for reading");
            return null;
        }

        // open BufferedReader on the File and read lines
        Charset charset = Charset.forName("UTF-8");
        try (BufferedReader reader = Files.newBufferedReader(path, charset)) {
            // parse file
            String line = null;
            while ((line = reader.readLine()) != null)
                lines.add(line);
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "Failed to read file", e);
            return null;
        }

        return lines;
    }
}
