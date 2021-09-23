package ru.job4j.accident.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accident.model.Accident;

import java.util.HashMap;
import java.util.Map;

@Repository
public class AccidentMem {
    private Map<Integer, Accident> accidents = new HashMap<>();

    {
        accidents.put(1, Accident.of("name1", "text1", "address1"));
        accidents.put(2, Accident.of("name2", "text2", "address2"));
        accidents.put(3, Accident.of("name3", "text3", "address3"));
        accidents.put(4, Accident.of("name4", "text4", "address4"));
    }

    public Map<Integer, Accident> getAccidents() {
        return accidents;
    }
}
