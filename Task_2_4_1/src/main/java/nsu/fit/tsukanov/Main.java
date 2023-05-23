package nsu.fit.tsukanov;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.util.DelegatingScript;
import nsu.fit.tsukanov.entity.Group;
import nsu.fit.tsukanov.entity.Student;
import org.codehaus.groovy.control.CompilerConfiguration;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello DSL!");
//        testStudent();
        testGroup();
    }

    private static void testStudent() {
        CompilerConfiguration cc = new CompilerConfiguration();
        cc.setScriptBaseClass(DelegatingScript.class.getName()); // благодаря этой настройке все создаваемые groovy скрипты будут наследоваться от DelegatingScript
        GroovyShell sh = new GroovyShell(Main.class.getClassLoader(), new Binding(), cc);
        DelegatingScript script = null;
        try {
            script = (DelegatingScript) sh.parse(new File("scripts/ts.groovy"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Student config = new Student(); // наш бин с конфигурацией
        script.setDelegate(config);
        script.run();
        System.out.println(config);
    }

    private static void testGroup() {
        CompilerConfiguration cc = new CompilerConfiguration();
        cc.setScriptBaseClass(DelegatingScript.class.getName()); // благодаря этой настройке все создаваемые groovy скрипты будут наследоваться от DelegatingScript
        GroovyShell sh = new GroovyShell(Main.class.getClassLoader(), new Binding(), cc);
        DelegatingScript script = null;
        try {
            script = (DelegatingScript) sh.parse(new File("scripts/group.groovy"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Group config = new Group(); // наш бин с конфигурацией
        script.setDelegate(config);
        script.run();
        System.out.println(config);

    }
}