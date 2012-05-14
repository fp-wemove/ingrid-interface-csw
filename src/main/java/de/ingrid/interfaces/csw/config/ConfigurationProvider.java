/*
 * Copyright (c) 2012 wemove digital solutions. All rights reserved.
 */
package de.ingrid.interfaces.csw.config;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.thoughtworks.xstream.XStream;

import de.ingrid.interfaces.csw.config.model.Configuration;
import de.ingrid.interfaces.csw.config.model.RequestDefinition;
import de.ingrid.interfaces.csw.config.model.impl.IBusHarvesterConfiguration;
import de.ingrid.interfaces.csw.config.model.impl.RecordCacheConfiguration;

/**
 * ConfigurationProvider gives access to the configuration of the update job
 * client.
 * 
 * @author ingo@wemove.com
 */
@Service
public class ConfigurationProvider {

    final protected static Log log = LogFactory.getLog(ConfigurationProvider.class);

    /**
     * The name of the system property that defines the configuration file
     */
    private static final String CONFIGURATION_FILE_NAME_PROPERTY = "config";

    /**
     * The name of the system property that defines the ingrid home directory
     */
    private static final String INGRID_HOME = "ingrid_home";

    /**
     * The XML configuration file
     */
    private File configurationFile = null;

    /**
     * The configuration instance
     */
    private Configuration configuration = null;

    /**
     * Constructor.
     */
    public ConfigurationProvider() {

        // check if the config property is set and load the appropriate
        // file if yes
        String configurationFilename = System.getProperty(CONFIGURATION_FILE_NAME_PROPERTY);

        if (configurationFilename == null) {
            // check if the ingrid home path is set and derive config file
            String ingridHome = System.getProperty(INGRID_HOME);
            if (ingridHome != null) {
                File f = new File(ingridHome, "config.xml");
                configurationFilename = f.getAbsolutePath();
            }
        }

        if (configurationFilename != null) {
            this.configurationFile = new File(configurationFilename);
        }

    }

    /**
     * Set the configuration file.
     * 
     * @param configurationFile
     */
    public void setConfigurationFile(File configurationFile) {
        this.configurationFile = configurationFile.getAbsoluteFile();
    }
    
    public File getRecordCachePath() {
        return new File(this.configurationFile.getParentFile(), "cache");
    }

    public File getIndexPath() {
        return new File(this.configurationFile.getParentFile(), "index");
    }

    public File getNewIndexPath() {
        return new File(this.configurationFile.getParentFile(), "index_new");
    }
    
    public File getMappingScript() {
        try {
            File mappingScript = new File(new File(this.configurationFile.getParentFile(), "conf"), this.getConfiguration().getMappingScript());
            if (!mappingScript.exists()) {
                throw new IOException("Mapping script does not exists: " + mappingScript.getAbsolutePath());
            }
            return mappingScript;
        } catch (IOException e) {
            log.error("Cannot find configuration.", e);
            return null;
        }
    }
    
    public File getSchedulingPatternFile() {
        return new File(this.configurationFile.getParentFile(), "scheduling.pattern");
    }
    
    public File getConfigurationFile() {
        return this.configurationFile;
    }
    
    

    /**
     * Read the configuration from disk. Creates an empty Configuration instance
     * if the file does not exist or is empty.
     */
    private synchronized void read() throws IOException {

        // create empty configuration, if not existing yet
        if (!this.configurationFile.exists()) {
            log.warn("Configuration file " + this.configurationFile + " does not exist.");
            if (this.configurationFile.getParentFile() != null && !this.configurationFile.getParentFile().exists() && !this.configurationFile.getParentFile().mkdirs()) {
                log.error("Unable to create directories for '"+this.configurationFile.getParentFile()+"'");
            }
            log.info("Creating configuration file " + this.configurationFile);
            this.configurationFile.createNewFile();
        }

        BufferedReader input = null;
        try {
            if (log.isDebugEnabled()) {
                log.debug("Read configuration file: " + this.configurationFile);
            }
            // read the configuration file content
            StringBuilder content = new StringBuilder();
            input = new BufferedReader(new InputStreamReader(new FileInputStream(this.configurationFile), "UTF-8"));
            String line = null;
            while ((line = input.readLine()) != null) {
                content.append(line);
                content.append(System.getProperty("line.separator"));
            }
            input.close();
            input = null;

            if (content.length() == 0) {
                log.warn("Configuration file " + this.configurationFile + " is empty.");
            }

            // deserialize a temporary Configuration instance from xml
            String xml = content.toString();
            if (xml.length() > 0) {
                XStream xstream = new XStream();
                this.setXStreamAliases(xstream);
                this.configuration = (Configuration) xstream.fromXML(xml);
            } else {
                this.configuration = new Configuration();
            }
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                }
            }
        }
    }

    /**
     * Write the given Configuration to the disc. To keep the time for modifying
     * the actual configuration file as short as possible, the method writes the
     * configuration into a temporary file first and then renames this file to
     * the original configuration file name. Note: Since renaming a file is not
     * atomic in Windows, if the target file exists already (we need to delete
     * and then rename), this method is synchronized.
     * 
     * @param configuration
     *            Configuration instance
     * @throws IOException
     */
    public synchronized void write(Configuration configuration) throws IOException {

        // make sure the configuration is loaded
        this.getConfiguration();

        // serialize the Configuration instance to xml
        XStream xstream = new XStream();
        this.setXStreamAliases(xstream);
        String xml = xstream.toXML(configuration);

        // write the configuration to a temporary file first
        File tmpFile = File.createTempFile("config", null);
        BufferedWriter output = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(tmpFile
                .getAbsolutePath()), "UTF8"));
        try {
            output.write(xml);
            output.close();
            output = null;
        } finally {
            if (output != null) {
                output.close();
            }
        }

        // move the temporary file to the configuration file
        this.configurationFile.delete();
        FileUtils.moveFile(tmpFile, this.configurationFile);
    }

    /**
     * Get the configuration
     * 
     * @return Configuration
     * @throws IOException
     */
    public Configuration getConfiguration() throws IOException {
        if (this.configuration == null) {
            this.read();
        }
        return this.configuration;
    }

    public Configuration reloadConfiguration() throws IOException {
        this.read();
        return this.configuration;
    }

    /**
     * Set the xml aliases for model classes
     * 
     * @param xstream
     *            XStream instance
     */
    private void setXStreamAliases(XStream xstream) {
        xstream.alias("configuration", Configuration.class);
        xstream.alias("request", RequestDefinition.class);
        xstream.alias("iBusHarvester", IBusHarvesterConfiguration.class);
        xstream.alias("recordCache", RecordCacheConfiguration.class);
    }


}
