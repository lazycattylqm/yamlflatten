package com.lqm.github;

import com.lqm.github.util.YamlFlatten;

import java.util.Map;

public class App {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: java App <inputFilePath> <outputFilePath>");
            return;
        }

        String inputFilePath = args[0];
        String outputFilePath = args[1];

        YamlFlatten util = new YamlFlatten();

        // Read the YAML file and flatten it
        Map<String, Object> yamlMap = util.readFile(inputFilePath);
        Map<String, Object> flattenedMap = util.flatten(yamlMap, "");

        // Write the flattened map to the output file
        util.writeToFile(flattenedMap, outputFilePath);

        System.out.println("Flattened YAML has been written to " + outputFilePath);
    }
}