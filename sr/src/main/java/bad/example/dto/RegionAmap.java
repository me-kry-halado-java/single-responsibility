package bad.example.dto;

import lombok.Data;

@Data
public class RegionAmap {
    private Long id;
    private String adcode;
    private Integer citycode;
    private String center;
    private Integer level;
    private String mergerName;
    private String name;
    private Integer parentId;
    private String pinyin;
    private String shortName;
    private String zipCode;
}
