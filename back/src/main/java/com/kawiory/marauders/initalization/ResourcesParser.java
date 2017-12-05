package com.kawiory.marauders.initalization;

import com.kawiory.marauders.game.resource.Resource;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Kacper
 */

@Component
public class ResourcesParser {

    private final ModelFilesReader reader;

    public ResourcesParser(ModelFilesReader reader) {
        this.reader = reader;
    }

    public List<Resource> parse() {
        return reader
                .readFile("Resources")
                .map(line -> line[0])
                .map(Resource::new)
                .collect(
                        Collectors.toList());
    }
}
