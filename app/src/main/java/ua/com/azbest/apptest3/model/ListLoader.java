package ua.com.azbest.apptest3.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ListLoader {

    public static List<ListItem> fromJson(String json) {
        if (json == null)
            return Collections.emptyList();
        ObjectMapper mapper = new ObjectMapper();
        List<Long> listLong = null;
        try {
            listLong = mapper.readValue(json, new TypeReference<List<Long>>(){});
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return listLong.stream().map((it) -> {
            return new ListItem(it, String.valueOf(it));
        }).collect(Collectors.toList());
    }

}
