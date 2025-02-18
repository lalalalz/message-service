package kr.co.kwt.messagecore.template.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.*;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static kr.co.kwt.messagecore.template.domain.DomainException.ErrorCode;
import static lombok.AccessLevel.PACKAGE;

@Document(collection = "templates")  // 올바른 사용법
@Getter
@AllArgsConstructor(access = PACKAGE)
public class Template {

    private static final String VARIABLE_PATTERN = "\\$\\{\\{\\s*([^}]+)\\s*\\}\\}";

    @Id
    private Long id;
    private Code code;
    private String text;
    private List<Constraint> constraints;

    public void addConstraint(Constraint constraint) {
        validateConstraints(constraint);
        constraints.add(constraint);
    }

    public void removeConstraint(Constraint constraint) {
        constraints.remove(constraint);
        validateConstraints();
    }

    public void validateConstraints(Constraint... constraintArray) {
        List<Constraint> newConstraintList = new ArrayList<>(constraints);

        if (constraintArray.length > 0) {
            newConstraintList.addAll(List.of(constraintArray));
        }
//
//        newConstraintList.forEach(constraint -> {
//            constraint.
//        });
    }

    public String render(List<Variable> variables) {
        StringBuilder renderedResult = new StringBuilder(text);

        if (variables.size() != getVariableCount()) {
            throw new DomainException(ErrorCode.RENDER_ARGS_COUNT);
        }

        getMatchResultsOfTemplateVariablesInReverseOrder(renderedResult)
                .forEach(matchResult -> replaceVariable(
                        matchResult,
                        getVariableNameToValueMap(variables),
                        renderedResult));

        return renderedResult.toString();
    }

    private List<MatchResult> getMatchResultsOfTemplateVariablesInReverseOrder(StringBuilder renderedResult) {
        return Pattern
                .compile(VARIABLE_PATTERN)
                .matcher(renderedResult.toString())
                .results()
                .sorted(Comparator.comparing((MatchResult m) -> m.start(0)).reversed()) // 역순 정렬
                .collect(Collectors.toList());
    }

    private Map<String, String> getVariableNameToValueMap(List<Variable> variables) {
        return variables
                .stream()
                .collect(Collectors.toMap(Variable::getName, Variable::getValue));
    }

    private void replaceVariable(
            MatchResult matchResult,
            Map<String, String> variableNameToValueMap,
            StringBuilder renderedResult
    ) {
        String variableName = getVariableName(matchResult);
        String variableValue = getVariableValue(variableNameToValueMap, variableName);
        renderedResult.replace(matchResult.start(), matchResult.end(), variableValue);
    }

    private String getVariableName(MatchResult matchResult) {
        return matchResult
                .group(1)
                .trim();
    }

    private String getVariableValue(Map<String, String> variableNameToValueMap, String variableName) {
        return Optional
                .ofNullable(variableNameToValueMap.get(variableName))
                .orElseThrow(() -> new DomainException(ErrorCode.RENDER_ARGS_COUNT));
    }

    private int getVariableCount() {
        int count = 0;
        Pattern pattern = Pattern.compile(VARIABLE_PATTERN);
        Matcher matcher = pattern.matcher(text);

        while (matcher.find()) {
            count++;
        }

        return count;
    }
}
