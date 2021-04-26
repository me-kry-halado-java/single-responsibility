package bad.example;

import bad.example.dto.RegionAmap;
import bad.example.http.HttpAdapter;
import bad.example.repository.RegionAmapMapperCustom;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.RequiredArgsConstructor;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

@RequiredArgsConstructor
public class UserMasterController {
    private final RegionAmapMapperCustom regionAmapMapperCustom;
    private final HttpAdapter httpAdapter;

    public void getMasterList() {
        InputStream inStream = httpAdapter.doGet("https://restapi.amap.com/v3/config/district?subdistrict=4&key=1e1f1c04c8b72367efe4431de2f038f5", null);
        String jsonString = null;
        try (Scanner scanner = new Scanner(inStream, StandardCharsets.UTF_8.name())) {
            jsonString = scanner.useDelimiter("\\A").next();
        }
        JSONObject jsonObject = JSONObject.parseObject(jsonString);

        JSONArray countryAll = jsonObject.getJSONArray("districts");
        for (int i = 0; i < countryAll.size(); i++) {
            JSONObject countryLeve0 = countryAll.getJSONObject(i);
            String citycode0 = countryLeve0.getString("citycode");
            String adcode0 = countryLeve0.getString("adcode");
            String name0 = countryLeve0.getString("name");
            String center0 = countryLeve0.getString("center");
            String country = countryLeve0.getString("level");
            int level = 0;
            if (country.equals("country")) {
                level = 0;
            }
            Integer id1 = insert(0, adcode0, citycode0, name0, center0, level, name0);
            JSONArray province0 = countryLeve0.getJSONArray("districts");

            for (int j = 0; j < province0.size(); j++) {
                JSONObject province1 = province0.getJSONObject(j);
                String citycode1 = province1.getString("citycode");
                String adcode1 = province1.getString("adcode");
                String name1 = province1.getString("name");
                String center1 = province1.getString("center");
                String province = province1.getString("level");
                int level1 = 0;
                if (province.equals("province")) {
                    level1 = 1;
                }
                Integer id2 = insert(id1, adcode1, citycode1, name1, center1, level1, name0, name1);
                JSONArray city0 = province1.getJSONArray("districts");

                for (int z = 0; z < city0.size(); z++) {
                    JSONObject city2 = city0.getJSONObject(z);
                    String citycode2 = city2.getString("citycode");
                    String adcode2 = city2.getString("adcode");
                    String name2 = city2.getString("name");
                    String center2 = city2.getString("center");
                    String city = city2.getString("level");
                    int level2 = 0;
                    if (city.equals("city")) {
                        level2 = 2;
                    }
                    Integer id3 = insert(id2, adcode2, citycode2, name2, center2, level2, name0, name1, name2);

                    JSONArray street0 = city2.getJSONArray("districts");
                    for (int w = 0; w < street0.size(); w++) {
                        JSONObject street3 = street0.getJSONObject(w);
                        String citycode3 = street3.getString("citycode");
                        String adcode3 = street3.getString("adcode");
                        String name3 = street3.getString("name");
                        String center3 = street3.getString("center");
                        String street = street3.getString("level");
                        int level3 = 0;
                        if (street.equals("street")) {
                            level3 = 2;
                        }
                        insert(id3, adcode3, citycode3, name3, center3, level3, name0, name1, name2, name3);
                        //  JSONArray street = street3.getJSONArray("districts");

                    }
                }
            }
        }
    }

    public Integer insert(Integer parentId, String citycode, String adcode, String name, String center, int level, String... mergeName) {
        RegionAmap record = new RegionAmap();
        if (!adcode.equals("[]")) {
            record.setAdcode(adcode);
        }
        record.setCitycode(Integer.parseInt(citycode));
        record.setCenter(center);
        record.setLevel(level);
        record.setName(name);
        record.setParentId(parentId);
        String megName = "";
        for (int i = 0; i < mergeName.length; i++) {
            megName = megName + mergeName[i];
            if (i < mergeName.length - 1) {
                megName = megName + ",";
            }
        }
        record.setMergerName(megName);
        regionAmapMapperCustom.insertSelective(record);
        return record.getCitycode();
    }

}