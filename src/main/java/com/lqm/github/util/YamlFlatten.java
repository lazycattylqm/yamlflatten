package com.lqm.github.util;

import cn.hutool.core.io.resource.ResourceUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class YamlFlatten {

    private final ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

    public Map<String, Object> readFile(String filePath) {
        try(InputStream stream = ResourceUtil.getStream(filePath)) {
            return mapper.readValue(stream, Map.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Map<String, Object> flatten(Map<String, Object> ori, String prefix) {
        Map<String, Object> r = new HashMap<>();
        ori.forEach((key, value)->{
            String p = key;
            if (!StringUtils.isEmpty(prefix)) {
                p = prefix+"."+key;
            }
            if (value instanceof Map m) {
                r.putAll(flatten(m, p));
            } else if (value instanceof List<?> l) {
                String v = flatList(l);
                r.put(p, v);
            }
            else {
                r.put(p, value);
            }
        });
        return r;
    }

    public String flatList( List l) {
        List list = l.stream()
                .map(Object::toString)
                .toList();
        return String.join(",", list);
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
