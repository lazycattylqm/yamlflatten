package com.lqm.github.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.apache.commons.codec.Resources;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

public class YamlFlatten {

    private final ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

    public Map<String, Object> readFile(String filePath) {
        try(InputStream stream = Resources.getInputStream(filePath)) {
            return mapper.readValue(stream, Map.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public Map<String, Object> flatten(Map<String, Object> ori, String prefix) {
        Map<String, Object> r = new HashMap<>();
        flatten(ori, prefix, r);
        return r;
    }

    private void flatten(Map<String, Object> ori, String prefix, Map<String, Object> result) {
        ori.forEach((key, value) -> {
            String p = StringUtils.isEmpty(prefix) ? key : prefix + "." + key;
            if (value instanceof Map m) {
                flatten(m, p, result);
            } else if (value instanceof List<?> l) {
                result.put(p, flatList(l));
            } else {
                result.put(p, value);
            }
        });
    }

    public String flatList( List l) {
        StringJoiner sj = new StringJoiner(",");
        l.forEach(e-> sj.add(e.toString()));
        return sj.toString();
    }

    public void samplePrint(Map<String, Object> m) {
        m.forEach((k,v)->{
            System.out.println(k + ": " + v);
        });
    }

    public void writeToFile(Map<String, Object> source) {
        File file = new File(Constant.FILE_NAME);
        if (file.exists()) {
            file.delete();
        }
        try {
            mapper.writeValue(file, source);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
