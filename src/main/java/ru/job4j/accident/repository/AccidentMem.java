package ru.job4j.accident.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.model.Rule;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class AccidentMem {
    private final AtomicInteger accidentId = new AtomicInteger(0);
    private final AtomicInteger accidentTypeId = new AtomicInteger(0);
    private final AtomicInteger ruleId = new AtomicInteger(0);
    private final Map<Integer, Accident> accidents = new HashMap<>();
    private final Map<Integer, AccidentType> types = new HashMap<>();
    private final Map<Integer, Rule> rules = new HashMap<>();

    {
        AccidentType carToCarType = AccidentType.of(1, "Две машины");
        AccidentType carToHumanType = AccidentType.of(2, "Машина и человек");
        AccidentType carToBikeType = AccidentType.of(3, "Машина и велосипед");

        saveOrUpdate(carToCarType);
        saveOrUpdate(carToHumanType);
        saveOrUpdate(carToBikeType);

        Rule rule1 = Rule.of(1, "Статья. 1");
        Rule rule2 = Rule.of(2, "Статья. 2");
        Rule rule3 = Rule.of(3, "Статья. 3");

        saveOrUpdate(rule1);
        saveOrUpdate(rule2);
        saveOrUpdate(rule3);

        Accident accident1 = Accident.of("name1", "text1", "address1", carToCarType);
        Accident accident2 = Accident.of("name2", "text2", "address2", carToCarType);
        Accident accident3 = Accident.of("name3", "text3", "address3", carToBikeType);
        Accident accident4 = Accident.of("name4", "text4", "address4", carToHumanType);

        accident1.addRule(rule1);
        accident2.addRule(rule1);
        accident2.addRule(rule2);
        accident3.addRule(rule3);
        accident4.addRule(rule1);
        accident4.addRule(rule3);

        String[] rIds = {};
        saveOrUpdate(accident1, rIds);
        saveOrUpdate(accident2, rIds);
        saveOrUpdate(accident3, rIds);
        saveOrUpdate(accident4, rIds);
    }

    public Collection<Accident> getAccidents() {
        return accidents.values();
    }

    public Collection<AccidentType> getAccidentTypes() {
        return types.values();
    }

    public Collection<Rule> getRules() {
        return rules.values();
    }

    public void saveOrUpdate(Accident accident, String[] rIds) {
        if (accident.getId() == 0) {
            accident.setId(accidentId.incrementAndGet());
        }
        if (Objects.isNull(accident.getType().getName())) {
            accident.setType(findAccidentTypeById(accident.getType().getId()));
        }
        for (String rId : rIds) {
            accident.addRule(findRuleById(Integer.parseInt(rId)));
        }
        accidents.put(accident.getId(), accident);
    }

    public void saveOrUpdate(AccidentType type) {
        if (type.getId() == 0) {
            type.setId(accidentTypeId.incrementAndGet());
        }
        types.put(type.getId(), type);
    }

    public void saveOrUpdate(Rule rule) {
        if (rule.getId() == 0) {
            rule.setId(ruleId.incrementAndGet());
        }
        rules.put(rule.getId(), rule);
    }

    public Accident findAccidentById(int id) {
        return accidents.get(id);
    }

    public AccidentType findAccidentTypeById(int id) {
        return types.get(id);
    }

    public Rule findRuleById(int id) {
        return rules.get(id);
    }
}
