package ru.job4j.accident.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class AccidentMem {
    private final AtomicInteger accidentId = new AtomicInteger(0);
    private final AtomicInteger accidentTypeId = new AtomicInteger(0);
    private final Map<Integer, Accident> accidents = new HashMap<>();
    private final Map<Integer, AccidentType> types = new HashMap<>();

    {
        AccidentType carToCarType = AccidentType.of(1, "Две машины");
        AccidentType carToHumanType = AccidentType.of(2, "Машина и человек");
        AccidentType carToBikeType = AccidentType.of(3, "Машина и велосипед");

        saveOrUpdate(carToCarType);
        saveOrUpdate(carToHumanType);
        saveOrUpdate(carToBikeType);

        saveOrUpdate(Accident.of("name1", "text1", "address1", carToCarType));
        saveOrUpdate(Accident.of("name2", "text2", "address2", carToCarType));
        saveOrUpdate(Accident.of("name3", "text3", "address3", carToBikeType));
        saveOrUpdate(Accident.of("name4", "text4", "address4", carToHumanType));
    }

    public Collection<Accident> getAccidents() {
        return accidents.values();
    }

    public Collection<AccidentType> getAccidentTypes() {
        return types.values();
    }

    public void saveOrUpdate(Accident accident) {
        if (accident.getId() == 0) {
            accident.setId(accidentId.incrementAndGet());
        }
        if (Objects.isNull(accident.getType().getName())) {
            accident.setType(findAccidentTypeById(accident.getType().getId()));
        }
        accidents.put(accident.getId(), accident);
    }

    public void saveOrUpdate(AccidentType type) {
        if (type.getId() == 0) {
            type.setId(accidentTypeId.incrementAndGet());
        }
        types.put(type.getId(), type);
    }

    public Accident findAccidentById(int id) {
        return accidents.get(id);
    }

    public AccidentType findAccidentTypeById(int id) {
        return types.get(id);
    }
}
