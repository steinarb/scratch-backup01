package no.priv.bang.modeling.modelstore.tests;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ModelstoreIntegrationtestBase {

    private String mavenProjectVersion;

    public ModelstoreIntegrationtestBase() {
        try {
            Properties examProperties = new Properties();
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("exam.properties");
            examProperties.load(inputStream);
            mavenProjectVersion = examProperties.getProperty("maven.project.version");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getMavenProjectVersion() {
        return mavenProjectVersion;
    }

}
