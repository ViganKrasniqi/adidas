package coreAPI;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
public enum Endpoints {

    BaseProductURL("","http://localhost:3001"),
    BasePriceEngineURL("","http://localhost:3000"),
    PRODUCT("Description","/product");

    String summary;
    String path;

    Endpoints(String summary, String path){
        this.summary = summary;
        this.path = path;
    }

    public String getSummary(){
        return this.summary;
    }

    public String getPath(){
        return this.path;
    }
}