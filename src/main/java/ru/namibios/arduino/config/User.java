package ru.namibios.arduino.config;

import org.apache.log4j.Logger;
import ru.namibios.arduino.utils.ExceptionUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

public class User {

    private static final Logger LOG = Logger.getLogger(User.class);

    private String hash;
    private String name;
    private String home;
    private String encoding;
    private String os;
    private String osArch;
    private String osVersion;
    private String country;
    private String version;

    public void setVersion(String version) {
        this.version = version;
    }

    public User() {
        this.hash = initHash();
        this.version = initVersion();
        this.name = new String(System.getProperty("user.name").getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
        this.home = new String(System.getProperty("user.home").getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
        this.encoding = System.getProperty("file.encoding");
        this.os = System.getProperty("os.name");
        this.osArch = System.getProperty("os.arch");
        this.osVersion = System.getProperty("os.version");
        this.country = System.getProperty("user.country");
    }

    private String initVersion(){

        try {

            if (version != null) {
                return version;

            } else {
                version = Files.readAllLines(Paths.get("version")).get(0);
                return version;
            }

        } catch (IOException e) {
            LOG.error(ExceptionUtils.getString(e));
        }

        return null;
    }

    private String initHash() {

        if (hash != null) {
            return hash;

        } else {
            String home = System.getProperty("user.home") + "/fishbotkey";

            if (!Files.exists(Paths.get(home))) {
                LOG.debug("File does not exist");

                hash = UUID.randomUUID().toString();
                LOG.debug("New key: " + hash);

                try {

                    LOG.debug("Load hash " + home);
                    Files.write(Paths.get(home), hash.getBytes());

                } catch (IOException e) {
                    LOG.error(ExceptionUtils.getString(e));
                }

                return hash;

            } else {

                LOG.debug("Load hash");
                try {

                    hash = Files.readAllLines(Paths.get(home)).get(0);
                    return hash;

                } catch (IOException e) {
                    LOG.error(ExceptionUtils.getString(e));
                }
            }

            LOG.error("hash not loaded!");
            System.exit(1);
        }

        return hash;
    }

    public String getHash() {
        return hash;
    }

    public String getName() {
        return name;
    }

    public String getHome() {
        return home;
    }

    public String getEncoding() {
        return encoding;
    }

    public String getOs() {
        return os;
    }

    public String getOsArch() {
        return osArch;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public String getCountry() {
        return country;
    }

    public String getVersion() {
        return version;
    }

    @Override
    public String toString() {
        return "User{" +
                "hash='" + hash + '\'' +
                ", name='" + name + '\'' +
                ", home='" + home + '\'' +
                ", encoding='" + encoding + '\'' +
                ", os='" + os + '\'' +
                ", osArch='" + osArch + '\'' +
                ", osVersion='" + osVersion + '\'' +
                ", country='" + country + '\'' +
                ", version='" + version + '\'' +
                '}';
    }
}
