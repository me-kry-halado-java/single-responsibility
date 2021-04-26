package hu.meiit.sweng.solid.singleresponsibility.repository;

import hu.meiit.sweng.solid.singleresponsibility.dto.RegionAmap;

public interface RegionAmapMapperCustom {
    void insertSelective(RegionAmap record);
}
