package com.nengyuanbox.repaircar.utils.PickerView;

import java.util.List;

/**
 * Created by Administrator on 2016/6/7 0007.
 */
public class CityInfoModel {

    private String name;
    private String zipcode;

    private List<DistrictInfoModel> districtList;

    public CityInfoModel() {
        super();
    }


    public CityInfoModel(String name, String zipcode) {
        this.name = name;
        this.zipcode = zipcode;
    }

    public CityInfoModel(String name, String zipcode, List<DistrictInfoModel> districtList) {
        this.name = name;
        this.zipcode = zipcode;
        this.districtList = districtList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<DistrictInfoModel> getDistrictList() {
        return districtList;
    }

    public void setDistrictList(List<DistrictInfoModel> districtList) {
        this.districtList = districtList;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }


    @Override
    public String toString() {
        return name;
    }
}
