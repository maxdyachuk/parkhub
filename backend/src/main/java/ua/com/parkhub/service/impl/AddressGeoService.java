package ua.com.parkhub.service.impl;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.parkhub.dto.AddressGeoDTO;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@Service
public class AddressGeoService {
    private static final Logger LOGGER = Logger.getLogger(AddressGeoService.class.getName());
    private OkHttpClient httpClient;
    private static final double EARTH_RADIUS = 6372795;

    @Autowired
    public AddressGeoService(OkHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public Map<String, String> getLatLon(String query) {

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY, true);
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("lat", "0.0");
        hashMap.put("lon", "0.0");
        String url = "https://nominatim.openstreetmap.org/search.php?q=" +
                getUrl(query) + "&format=json";

        Request request = new Request.Builder().url(url).build();

        try (Response response = httpClient.newCall(request).execute()) {
            ResponseBody body = response.body();

            if (body != null) {
                String json = body.string();
                LOGGER.info("Reponse: " + json);
                AddressGeoDTO[] adressGeoDTO = mapper.readValue(json, AddressGeoDTO[].class);
                if (adressGeoDTO.length > 0) {
                    hashMap.put("lat", adressGeoDTO[0].getLat());
                    hashMap.put("lon", adressGeoDTO[0].getLon());
                }
            }
        } catch (IOException e1) {
            LOGGER.warning("Connection timed out ->"+e1);
        }
        LOGGER.info("lat: " + hashMap.get("lat") + " lon: " + hashMap.get("lon"));
            return hashMap;
    }


    private String getUrl(String query) {
        query = query.replaceAll(" ", "+");
        if (query.indexOf(",") < 0) {
            query = query.replaceFirst("\\+", ",+");
        }
        return query;
    }

    public boolean enteringTheRadius(String lat1, String lon1, String lat2, String lon2) {

        double latt1 = gradInRad(Double.valueOf(lat1));
        double latt2 = gradInRad(Double.valueOf(lat2));
        double lonn1 = gradInRad(Double.valueOf(lon1));
        double lonn2 = gradInRad(Double.valueOf(lon2));

        double cosLat1 = Math.cos(latt1);
        double cosLat2 = Math.cos(latt2);

        double sinLat1 = Math.sin(latt1);
        double sinLat2 = Math.sin(latt2);

        double deltaLon = lonn2 - lonn1;
        double cosDelta = Math.cos(deltaLon);
        double sinDelta = Math.sin(deltaLon);

        double x1 = Math.sqrt(Math.pow(cosLat2 * sinDelta, 2) +
                Math.pow(cosLat1 * sinLat2 - sinLat1 * cosLat2 * cosDelta, 2));

        double y1 = sinLat1 * sinLat2 + cosLat1 * cosLat2 * cosDelta;

        double arcTan = Math.atan2(x1, y1);
        double dist = arcTan * EARTH_RADIUS;


        return !(dist > 1100);
    }

    private double gradInRad(double i) {
        return i * Math.PI / 180;
    }


}
