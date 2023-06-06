package nsu.fit.tsukanov.entity.common;

import groovy.lang.Closure;
import lombok.Data;
import nsu.fit.tsukanov.dsl.Delegator;

@Data
public class GeneralConfig {
    GitConfig git = new GitConfig();
    EvaluationConfig evaluationConfig = new EvaluationConfig();

    public void git(Closure<?> closure) {
        Delegator.groovyDelegate(git, closure);
    }

    public void evaluation(Closure<?> closure) {
        Delegator.groovyDelegate(evaluationConfig, closure);
    }

}
