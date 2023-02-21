package lims.api.integration.properties;

import com.sap.conn.jco.ext.DestinationDataProvider;
import lims.api.util.ThreadUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

@Slf4j
@Component
@PropertySource("classpath:interface-system/${spring.profiles.active}.properties")
public class JcoProperties {

    @Value("${jco.ashost}")
    private String ashost;

    @Value("${jco.sysnr}")
    private String sysnr;

    @Value("${jco.client}")
    private String client;

    @Value("${jco.user}")
    private String user;

    @Value("${jco.passwd}")
    private String passwd;

    @Value("${jco.lang}")
    private String lang;

    @PostConstruct
    public void postConstructor() {
        try {
            deleteDestinationDataFile();
            Properties properties = createJCOProperties(ashost, sysnr, client, user, passwd, lang);
            createDestinationDataFile(properties);
        } catch (Exception e) {
            log.error("[{}] Failed to create jcoDestination file. check the JCO environment variables in application-[].yml." +
                            "\n ashost: {}," +
                            "\n sysnr: {}," +
                            "\n client: {}," +
                            "\n user: {}," +
                            "\n passwd: {}," +
                            "\n lang: {}.",
                    ThreadUtil.getCurrentMethodName(),
                    ashost,
                    sysnr,
                    client,
                    user,
                    passwd,
                    lang
            );
            throw new RuntimeException(e);
        }
    }

    private void deleteDestinationDataFile() {
        File destCfg = new File(getJcoDestinationFileFullName());
        if (destCfg.exists()) {
            destCfg.delete();
        }
    }

    private Properties createJCOProperties(String ashost, String sysnr, String client, String user, String passwd, String lang) {
        Properties properties = new Properties();
        properties.setProperty(DestinationDataProvider.JCO_ASHOST, ashost);
        properties.setProperty(DestinationDataProvider.JCO_SYSNR, sysnr);
        properties.setProperty(DestinationDataProvider.JCO_CLIENT, client);
        properties.setProperty(DestinationDataProvider.JCO_USER, user);
        properties.setProperty(DestinationDataProvider.JCO_PASSWD, passwd);
        properties.setProperty(DestinationDataProvider.JCO_LANG, lang);
        return properties;
    }

    private void createDestinationDataFile(Properties properties) throws IOException {
        File destCfg = new File(getJcoDestinationFileFullName());
        FileOutputStream fos = new FileOutputStream(destCfg, false);
        properties.store(fos, "jco_destination_data_file");
        fos.close();
    }

    private String getJcoDestinationFileFullName() {
        return getJcoDestinationFileName() + ".jcoDestination";
    }

    public String getJcoDestinationFileName() {
        return "autoCreatedJcoInfoWhenApplicationStart";
    }

}