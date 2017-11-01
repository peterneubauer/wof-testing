import com.google.gson.*;
import org.junit.Test;
import org.locationtech.spatial4j.context.jts.JtsSpatialContext;
import org.locationtech.spatial4j.context.jts.JtsSpatialContextFactory;
import org.locationtech.spatial4j.io.ShapeIO;
import org.locationtech.spatial4j.io.ShapeReader;
import org.locationtech.spatial4j.shape.Shape;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.URL;
import java.text.ParseException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.SortedMap;

import static java.lang.String.format;

public class WofJTsTest {

    @Test
    public void testJtsValidity() throws IOException, ParseException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("data/region.txt")));
        String line;
        int count = 0;
        Gson gson = new Gson();
        JtsSpatialContextFactory factory = new JtsSpatialContextFactory();
//        factory.geo = true;
//        factory.normWrapLongitude = true;
//        factory.useJtsLineString = false; // false so that buffering lineString round-trips
        ShapeReader shapeReader = new JtsSpatialContext(factory).getFormats().getReader(ShapeIO.GeoJSON);
        while ((line = bufferedReader.readLine()) != null) {
            BufferedReader buffer = new BufferedReader(new InputStreamReader(new URL(line).openConnection().getInputStream()));
            StringBuilder content = new StringBuilder();
            String fileLine;
            while ((fileLine = buffer.readLine()) != null) {
                content.append(fileLine + "\n");
            }
            buffer.close();
            String json = content.toString();
            Map geojson = gson.fromJson(json, Map.class);
            Map geometryObj = (Map) geojson.get("geometry");
            Map properties = (Map) geojson.get("properties");
            System.out.println(format("%s, %d, %s", count, ((Double)properties.get("wof:id")).intValue(), properties.get("wof:name") ));
            Map geomMap = new LinkedHashMap();
            geomMap.put("type", geometryObj.get("type"));
            geomMap.put("coordinates", geometryObj.get("coordinates"));
            String geometry = gson.toJson(geomMap);
            Shape shape = shapeReader.read(geometry);

            // parse Geometry from Feature
//            GeoJSONReader reader = new GeoJSONReader();
//            reader.read(feature.getGeometry());
            count++;
        }

    }
}
