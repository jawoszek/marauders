package com.kawiory.marauders.initalization;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Stream;

import static java.lang.System.lineSeparator;

/**
 * @author Kacper
 */

@Component
@Order(1)
public class ModelFilesReader {

    public Stream<String[]> readFile(String fileName) {
        return Arrays
                .stream(
                        readResource(fileName).split(lineSeparator()))
                .filter(
                        line -> !line.startsWith("#"))
                .map(
                        line -> line.split(":"));
    }

    private static String readResource(final String fileName) {
        String fileContent = "";
        try {
            fileContent = Resources.toString(Resources.getResource(fileName), Charsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileContent;
    }
}
