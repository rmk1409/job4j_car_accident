package ru.job4j.accident.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accident.model.Accident;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class AccidentMem {
    private AtomicInteger newId = new AtomicInteger(0);
    private Map<Integer, Accident> accidents = new HashMap<>();

    {
        saveOrUpdate(Accident.of("name1", "text1", "address1"));
        saveOrUpdate(Accident.of("name2", "text2", "address2"));
        saveOrUpdate(Accident.of("name3", "text3", "address3"));
        saveOrUpdate(Accident.of("name4", "text4", "address4"));
    }

    public Map<Integer, Accident> getAccidents() {
        return accidents;
    }

    public void saveOrUpdate(Accident accident) {
        if (accident.getId() == 0) {
            accident.setId(newId.incrementAndGet());
        }
        accidents.put(accident.getId(), accident);
    }
}
