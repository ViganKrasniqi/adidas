package model;

import com.google.gson.JsonObject;
import lombok.Builder;
import lombok.Data;

import java.io.File;

@Data
@Builder
public class Environment {
    private String id;
    private String base_url;
    private File configurationFile;
    private JsonObject object;
}