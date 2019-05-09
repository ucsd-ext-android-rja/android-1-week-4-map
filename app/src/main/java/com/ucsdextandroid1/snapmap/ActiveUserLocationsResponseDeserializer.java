package com.ucsdextandroid1.snapmap;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by rjaylward on 2019-05-04
 *
 * This class is used to have custom rules when creating an {@link ActiveUserLocationsResponse}
 * from json. In this case we need to convert an object that looks like this
 *  {
 *      "user_01": { "location_name": ... },
 *      "user_02": { "location_name": ... },
 *  }
 *
 *  into something that looks like this
 *  [
 *      { "user_id": "user_01", "location_name": ... },
 *      { "user_id": "user_02", "location_name": ... },
 *  ]
 *
 */
//TODO added in class 5
public class ActiveUserLocationsResponseDeserializer implements JsonDeserializer<ActiveUserLocationsResponse> {

    @Override
    public ActiveUserLocationsResponse deserialize(JsonElement json,
                                                   Type typeOfT,
                                                   JsonDeserializationContext context
    ) throws JsonParseException {

        ActiveUserLocationsResponse response = new ActiveUserLocationsResponse();

        List<UserLocationData> locations = new ArrayList<>();

        for(Map.Entry<String, JsonElement> entry : json.getAsJsonObject().entrySet()) {

            //we use the gson context passed to this object to create the UserLocationData automatically
            UserLocationData locationData = context
                    .deserialize(entry.getValue(), UserLocationData.class);

            //since this endpoint has the user_id as the json key we need to add it to the object
            locationData.setUserId(entry.getKey());

            locations.add(locationData);

            //LONG WAY, we could parse each item manually but it is easier to use gson to do it
//            JsonObject object = entry.getValue().getAsJsonObject();
//            UserLocationData locationData = new UserLocationData(
//                    object.get("color").getAsString(),
//                    object.get("latitude").getAsDouble(),
//                    ...
//            )
        }

        response.setUserLocations(locations);

        return response;
    }

}
