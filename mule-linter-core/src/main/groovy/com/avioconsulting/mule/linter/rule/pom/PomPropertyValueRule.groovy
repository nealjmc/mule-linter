package com.avioconsulting.mule.linter.rule.pom

import com.avioconsulting.mule.linter.model.Application
import com.avioconsulting.mule.linter.model.pom.PomElement
import com.avioconsulting.mule.linter.model.rule.Param
import com.avioconsulting.mule.linter.model.rule.Rule
import com.avioconsulting.mule.linter.model.rule.RuleViolation

class PomPropertyValueRule extends Rule {

    static final String RULE_ID = 'MAVEN_PROPERTY'
    static final String RULE_NAME = 'The given Maven property matches the given value. '
    static final String RULE_VIOLATION_MESSAGE = ' maven property value does not match expected value. '
    static final String ATTRIBUTE_MISSING_MESSAGE = ' does not exist in <properties></properties>'

    @Param("propertyName") String propertyName
    @Param("propertyValue") String propertyValue

    PomPropertyValueRule(){
        super(RULE_ID, RULE_NAME)
    }

    PomPropertyValueRule(String ruleId, String ruleName, String propertyName){
        super(ruleId, ruleName)
        this.propertyName = propertyName
    }

    @Override
    List<RuleViolation> execute(Application app) {
        List<RuleViolation> violations = []

        try {
            PomElement pomProperty = app.pomFile.getPomProperty(propertyName)
            if (!pomProperty.value.equalsIgnoreCase(propertyValue)) {
                violations.add(new RuleViolation(this, app.pomFile.path,
                        pomProperty.lineNo, pomProperty.name + RULE_VIOLATION_MESSAGE + 'Expected: ' + propertyValue
                        + ' found: ' + pomProperty.value))
            }
        } catch (IllegalArgumentException e) {
            violations.add(new RuleViolation(this, app.pomFile.path,
                    app.pomFile.propertiesLineNo, propertyName + ATTRIBUTE_MISSING_MESSAGE))
        }

        return violations
    }

}
