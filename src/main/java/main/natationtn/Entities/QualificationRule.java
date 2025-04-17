package main.natationtn.Entities;

import java.util.List;

public class QualificationRule {
    private String category;
    private List<String> rules;

    public QualificationRule(String category, List<String> rules) {
        this.category = category;
        this.rules = rules;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<String> getRules() {
        return rules;
    }

    public void setRules(List<String> rules) {
        this.rules = rules;
    }
}
