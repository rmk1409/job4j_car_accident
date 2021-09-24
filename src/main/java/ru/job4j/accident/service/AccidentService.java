package ru.job4j.accident.service;

import org.springframework.stereotype.Service;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.model.Rule;
import ru.job4j.accident.repository.AccidentRepository;
import ru.job4j.accident.repository.AccidentTypeRepository;
import ru.job4j.accident.repository.RuleRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class AccidentService {
    private final AccidentRepository accidentRepository;
    private final RuleRepository ruleRepository;
    private final AccidentTypeRepository accidentTypeRepository;

    public AccidentService(AccidentRepository accidentRepository, RuleRepository ruleRepository, AccidentTypeRepository accidentTypeRepository) {
        this.accidentRepository = accidentRepository;
        this.ruleRepository = ruleRepository;
        this.accidentTypeRepository = accidentTypeRepository;
    }

    public Collection<Rule> getRules() {
        List<Rule> rules = new ArrayList<>();
        ruleRepository.findAll().forEach(rules::add);
        return rules;
    }

    public Collection<AccidentType> getAccidentTypes() {
        List<AccidentType> types = new ArrayList<>();
        accidentTypeRepository.findAll().forEach(types::add);
        return types;
    }

    public Collection<Accident> getAccidents() {
        List<Accident> accidents = new ArrayList<>();
        accidentRepository.findAll().forEach(accidents::add);
        return accidents;
    }

    public Accident findAccidentById(int id) {
        return accidentRepository.findById(id).get();
    }

    public void saveOrUpdate(Accident accident, String[] rIds) {
        for (String id : rIds) {
            Rule rule = new Rule();
            rule.setId(Integer.parseInt(id));
            accident.addRule(rule);
        }
        accidentRepository.save(accident);
    }
}
