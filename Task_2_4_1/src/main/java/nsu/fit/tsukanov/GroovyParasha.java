package nsu.fit.tsukanov;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.util.DelegatingScript;
import nsu.fit.tsukanov.entity.common.GeneralConfig;
import nsu.fit.tsukanov.entity.fixes.FixConfig;
import nsu.fit.tsukanov.entity.fixes.StudentInformation;
import nsu.fit.tsukanov.entity.group.GroupConfig;
import nsu.fit.tsukanov.entity.tasks.TaskConfig;
import org.codehaus.groovy.control.CompilerConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GroovyParasha {
    private final GroovyShell sh;

    public GroovyParasha() {
        CompilerConfiguration cc = new CompilerConfiguration();
        cc.setScriptBaseClass(DelegatingScript.class.getName());
        this.sh = new GroovyShell(GroovyParasha.class.getClassLoader(), new Binding(), cc);
    }

    public DelegatingScript getScript(File file) throws IOException {
        return (DelegatingScript) sh.parse(file);
    }

    public DelegatingScript getScript(String filePath) throws IOException {
        return getScript(new File(filePath));
    }

    public void parseScript(String filePath, Object delegate) throws IOException {
        DelegatingScript script = getScript(filePath);
        script.setDelegate(delegate);
        script.run();
    }


    private GeneralConfig readGeneral() {
        GeneralConfig generalConfig = new GeneralConfig();
        try {
            parseScript("scripts/general.groovy", generalConfig);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return generalConfig;
    }

    private TaskConfig readTasks(GeneralConfig generalConfig) {
        TaskConfig taskConfig = new TaskConfig(generalConfig);
        try {
            parseScript("scripts/tasks.groovy", taskConfig);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return taskConfig;

    }

    private GroupConfig readGroup(GeneralConfig generalConfig) {
        GroupConfig groupConfig = new GroupConfig(generalConfig); // наш бин с конфигурацией
        try {
            parseScript("scripts/group21214.groovy", groupConfig);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return groupConfig;
    }

    private FixConfig readFixes(Map<String, StudentInformation> studentInformation) {
        FixConfig fixConfig = new FixConfig(studentInformation); // наш бин с конфигурацией
        try {
            parseScript("scripts/fixes.groovy", fixConfig);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return fixConfig;
    }

    public static Map<String, StudentInformation> getStudentInformationMap(GroupConfig groupConfig,
                                                                           TaskConfig taskConfig) {
        Map<String, StudentInformation> informationMap = new HashMap<>();
        groupConfig.getStudentConfigs()
                .forEach(studentConfig -> informationMap.put(studentConfig.getGitName(),
                        new StudentInformation(studentConfig, taskConfig)
                ));
        return informationMap;
    }

    public static void main(String[] args) {
        GroovyParasha groovyParasha = new GroovyParasha();

        GeneralConfig generalConfig = groovyParasha.readGeneral();

        GroupConfig group = groovyParasha.readGroup(generalConfig);

        TaskConfig taskConfig = groovyParasha.readTasks(generalConfig);

        Map<String, StudentInformation> studentInformationMap =
                getStudentInformationMap(group, taskConfig);

        FixConfig fixes = groovyParasha.readFixes(studentInformationMap);

        System.out.println(fixes.getInformationMap().get("MihailCCfit"));

    }
}