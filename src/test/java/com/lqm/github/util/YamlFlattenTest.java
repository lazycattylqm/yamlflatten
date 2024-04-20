package com.lqm.github.util;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class YamlFlattenTest {

    YamlFlatten util = new YamlFlatten();

    @Test
    void readFile() {
        Map<String, Object> yamlMap = util.readFile("files/test.yaml");
        System.out.println(yamlMap);
        Map<String, Object> flatten = util.flatten(yamlMap, "");
        System.out.println(flatten);
        util.samplePrint(flatten);
        util.writeToFile(flatten, null);

    }
}