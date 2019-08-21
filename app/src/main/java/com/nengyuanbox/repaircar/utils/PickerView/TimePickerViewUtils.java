package com.nengyuanbox.repaircar.utils.PickerView;

import android.content.Context;
import android.content.res.AssetManager;
import android.view.View;

import com.bigkoo.pickerview.OptionsPickerView;
import com.nengyuanbox.repaircar.R;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * Created by Administrator on 2018/4/27.
 */

public class TimePickerViewUtils {

    protected static String mCurrentProviceName;
    protected static String mCurrentCityName;
    protected static String mCurrentDistrictName;

    protected static String mCurrentProviceCode;
    protected static String mCurrentCityCode;
    protected static String mCurrentDistrictCode;
    private static List<CityInfoModel> list1;//三级联动数据
    private static List<List<ProvinceInfoModel>> list2;
    private static List<List<List<DistrictInfoModel>>> list3;

    /**
     * 地址三级联动
     */
    public static void showBindOption(Context context, final ShowPickerCallBack selectCallBack ) {
        list1 = new ArrayList<>();
        list2 = new ArrayList<>();
        list3 = new ArrayList<>();
        readAddrDatas(context);

        OptionsPickerView optionsPickerView = new OptionsPickerView.Builder(context, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                selectCallBack.selectCallBack(list1.get(options1), list2.get(options1).get(options2), list3.get(options1).get(options2).get(options3));
            }
        }).setCancelText("取消").setSubmitText("确定")
                .setCancelColor(context.getResources().getColor(R.color.txt_red))
                .setSubmitColor(context.getResources().getColor(R.color.txt_red))
                .build();

        optionsPickerView.setPicker(list1, list2, list3);
        optionsPickerView.show();
    }


    protected static boolean readAddrDatas(Context context) {
        List<ProvinceInfoModel> provinceList = null;
        AssetManager asset = context.getAssets();
        try {

            InputStream input = asset.open("province_data.xml");
            SAXParserFactory spf = SAXParserFactory.newInstance();
            SAXParser parser = spf.newSAXParser();
            AddrXmlParser handler = new AddrXmlParser();
            parser.parse(input, handler);
            input.close();
            provinceList = handler.getDataList();
            if (provinceList != null && !provinceList.isEmpty()) {
                mCurrentProviceName = provinceList.get(0).getName();
                mCurrentProviceCode = provinceList.get(0).getZipcode();
                List<CityInfoModel> cityList = provinceList.get(0).getCityList();
                if (cityList != null && !cityList.isEmpty()) {
                    mCurrentCityName = cityList.get(0).getName();
                    mCurrentCityCode = provinceList.get(0).getZipcode();
                    List<DistrictInfoModel> districtList = cityList.get(0).getDistrictList();
                    mCurrentDistrictName = districtList.get(0).getName();
                    mCurrentDistrictCode = districtList.get(0).getZipcode();
                }
            }
            for (int i = 0; i < provinceList.size(); i++) {
                list1.add(new CityInfoModel(provinceList.get(i).getName(), provinceList.get(i).getZipcode()));

                List<CityInfoModel> cityList = provinceList.get(i).getCityList();

                List<List<DistrictInfoModel>> dist = new ArrayList<>();
                ArrayList<ProvinceInfoModel> cityNames = new ArrayList<>();

                for (int j = 0; j < cityList.size(); j++) {

                    cityNames.add(new ProvinceInfoModel(cityList.get(j).getName(), cityList.get(j).getZipcode()));

                    List<DistrictInfoModel> districtList = cityList.get(j).getDistrictList();

                    ArrayList<DistrictInfoModel> distrinctNameArray = new ArrayList<>();
                    DistrictInfoModel[] distrinctArray = new DistrictInfoModel[districtList.size()];
                    for (int k = 0; k < districtList.size(); k++) {
                        DistrictInfoModel districtModel = new DistrictInfoModel(districtList.get(k).getName(), districtList.get(k).getZipcode());
                        distrinctArray[k] = districtModel;

                        distrinctNameArray.add(new DistrictInfoModel(districtModel.getName(), districtModel.getZipcode()));
                    }
                    dist.add(distrinctNameArray);
                }
                list2.add(cityNames);
                list3.add(dist);
            }
            return true;
        } catch (Throwable e) {
            e.printStackTrace();
            return false;
        }
    }


    public interface ShowPickerCallBack {
        void selectCallBack(CityInfoModel cityInfo, ProvinceInfoModel provinceInfo, DistrictInfoModel districtInfo);

    }


}
