package com.nengyuanbox.repaircar.utils.PickerView;

import java.util.List;

/**
 * Created by Administrator on 2016/6/7 0007.
 */
public class ProvinceInfoModel {
    private String name;
    private String zipcode;
    private List<CityInfoModel> cityList;

    public ProvinceInfoModel() {
        super();
    }

    public ProvinceInfoModel(String name, String zipcode) {
        this.name = name;
        this.zipcode = zipcode;
    }

    public ProvinceInfoModel(String name, String zipcode, List<CityInfoModel> cityList) {
        this.name = name;
        this.zipcode = zipcode;
        this.cityList = cityList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public List<CityInfoModel> getCityList() {
        return cityList;
    }

    public void setCityList(List<CityInfoModel> cityList) {
        this.cityList = cityList;
    }


    @Override
    public String toString() {
        return  name;
    }
}
