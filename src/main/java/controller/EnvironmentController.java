package controller;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import model.Environment;
import model.Product;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import static java.io.File.separator;
import static java.lang.System.getProperty;
import static java.text.MessageFormat.format;
import static java.util.Objects.requireNonNull;
import static org.apache.commons.lang3.StringUtils.defaultIfEmpty;

@UtilityClass
@Slf4j
public class EnvironmentController {

    private final String DIRECTORY = "user.dir";

    private Environment environment;

    public Environment getEnvironment() {
        if (environment == null) {
            environment = Environment.builder()
                    .id(getActiveEnvironmentName())
                    .base_url(getEnvironmentValueByKey("base_url"))
                    .configurationFile(new File(getEnvironmentConfigFileName()))
                    .object(getEnvironmentConfiguration())
                    .build();
        }
        return environment;
    }

    public String getActiveEnvironmentName() {
        return defaultIfEmpty(getProperty("ENVIRONMENT"), "prod");
    }

    public String getEnvironmentConfigFileName() {
        String filePath = format("{0}{1}src{1}test{1}resources{1}routee.{2}.json"
                , getProperty(DIRECTORY), separator, getActiveEnvironmentName());
        log.info("Running in " + getActiveEnvironmentName() + " environment using file: " + filePath);
        return filePath;
    }

    public String getEnvironmentValueByKey(final String key) {
        return Optional.ofNullable((requireNonNull(getEnvironmentConfiguration())).get(key).getAsString())
                .orElseThrow(() -> new IllegalArgumentException(
                        "No record found in JSON file matching the " + key + " key."));
    }

    public JsonObject getEnvironmentConfiguration() {
        JsonObject environmentJson;
        try {
            Gson gson = new Gson();
            environmentJson = gson.fromJson(getFileReader(getEnvironmentConfigFileName()), JsonObject.class);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("The configuration file for environment: " + getActiveEnvironmentName()
                    + " is not parsable to JsonObject.");
        }
        return environmentJson;
    }

    // use explicit charset so we keep common behaviour across platforms
    @NotNull
    @Contract("_ -> new")
    private InputStreamReader getFileReader(String filePath) throws FileNotFoundException {
        return new InputStreamReader(new FileInputStream(filePath), StandardCharsets.UTF_8);
    }

    // return resource file contents as JsonObject
    public JsonObject getJsonObject(String file) throws IOException {
        String path = System.getProperty("user.dir");
        Gson gson = new Gson();
        return gson.fromJson(getFileReader(path + "/src/main/resources/" + file + ".json"), JsonObject.class);
    }

    public Product getProductById(String id) {
        AtomicReference<Product> userValue = new AtomicReference<>(new Product());
        JsonArray users = getEnvironment().getObject().get("users").getAsJsonArray();

        users.forEach(user -> {
            Product productObject = new Gson().fromJson(user, Product.class);
            if (productObject.getId().equals(id)) {
                userValue.set(productObject);
            }
        });
        return userValue.get();
    }
}