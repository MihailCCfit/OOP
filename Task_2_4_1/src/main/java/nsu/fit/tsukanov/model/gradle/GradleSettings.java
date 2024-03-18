package nsu.fit.tsukanov.model.gradle;

import lombok.Builder;

@Builder
public class GradleSettings {
    @Builder.Default
    private boolean runTest = true;
    @Builder.Default
    private boolean runDocs = true;
    @Builder.Default
    private boolean runCheckstyle = true;
}
