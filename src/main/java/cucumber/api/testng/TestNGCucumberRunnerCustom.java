//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package cucumber.api.testng;

import cucumber.api.event.TestRunFinished;
import cucumber.api.junit.Cucumber;
import cucumber.runtime.ClassFinder;
import cucumber.runtime.CucumberException;
import cucumber.runtime.Runtime;
import cucumber.runtime.RuntimeOptions;
import cucumber.runtime.RuntimeOptionsFactory;
import cucumber.runtime.io.MultiLoader;
import cucumber.runtime.io.ResourceLoader;
import cucumber.runtime.io.ResourceLoaderClassFinder;
import cucumber.runtime.model.CucumberFeature;
import gherkin.events.PickleEvent;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TestNGCucumberRunnerCustom extends TestNGCucumberRunner{
    public ThreadLocal<CucumberFeature> CURRENT_FEATURE_EXECUTION = new ThreadLocal<>();
    public Runtime runtime;
    public TestNGReporter reporter;
    public RuntimeOptions runtimeOptions;
    public ResourceLoader resourceLoader;
    public TestCaseResultListener testCaseResultListener;

    public TestNGCucumberRunnerCustom(Class clazz) {
        super(clazz);
        ClassLoader classLoader = clazz.getClassLoader();
        this.resourceLoader = new MultiLoader(classLoader);
        RuntimeOptionsFactory runtimeOptionsFactory = new RuntimeOptionsFactory(clazz);
        this.runtimeOptions = runtimeOptionsFactory.create();
        this.reporter = new TestNGReporter(new PrintStream(System.out) {
            public void close() {
            }
        });
        ClassFinder classFinder = new ResourceLoaderClassFinder(this.resourceLoader, classLoader);
        this.runtime = new Runtime(this.resourceLoader, classFinder, classLoader, this.runtimeOptions);
        this.reporter.setEventPublisher(this.runtime.getEventBus());
        this.testCaseResultListener = new TestCaseResultListener(this.runtimeOptions.isStrict());
        this.testCaseResultListener.setEventPublisher(this.runtime.getEventBus());
    }

    public List<PickleEvent> provideScenarios(CucumberFeature feature) {
        CURRENT_FEATURE_EXECUTION.set(feature);
        try {
            return this.runtime.compileFeature(feature);
        } catch (CucumberException var8) {
            return new ArrayList<PickleEvent>();
        }
    }

    public List<CucumberFeature> getFeatures() {
        return this.runtimeOptions.cucumberFeatures(this.resourceLoader, this.runtime.getEventBus());
    }
}
