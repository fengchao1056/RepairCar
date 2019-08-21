package com.nengyuanbox.repaircar.fragment;

import android.os.Bundle;
import android.webkit.WebView;

import com.nengyuanbox.repaircar.R;
import com.nengyuanbox.repaircar.base.BaseFragment;
import com.nengyuanbox.repaircar.eventbus.GoodsDetailEventBean;
import com.vise.log.ViseLog;
import com.vondear.rxtool.RxDataTool;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
//商品参数
public class GoodsParameterFragment extends BaseFragment {
    @BindView(R.id.wv_view)
    WebView wv_view;

    @Override
    public void init(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);

//        String s="<p>\n" +
//                "\t<img src=\"https://lxcadmin.nengyuanbox.com/Public/kindeditor-4.1.4/attached/image/20190430/20190430114851_91076.jpeg\" alt=\"\" /> \n" +
//                "</p>\n" +
//                "<p>\n" +
//                "\t<img src=\"https://lxcadmin.nengyuanbox.com/Public/kindeditor-4.1.4/attached/image/20190430/20190430114911_89728.jpeg\" alt=\"\" /> \n" +
//                "</p>";
//
//
//        wv_view.loadDataWithBaseURL(null,
//                getHtmlData(s), "text/html", "utf-8", null);
//        RxToast.normal("GraphicDetailsFragment");
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_graphic_details;
    }


    /**
     * 加载html标签
     *
     * @param bodyHTML
     * @return
     */
    private String getHtmlData(String bodyHTML) {
        String head = "<head>" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no\"> " +
                "<style>img{max-width: 100%; width:auto; height:auto!important;}</style>" +
                "</head>";
        return "<html>" + head + "<body>" + bodyHTML + "</body></html>";
    }

    @Subscribe
     public  void  getGoodsDetailsEvent(GoodsDetailEventBean eventBean){
          if (!RxDataTool.isEmpty(eventBean.getGoods_parameter())){
              ViseLog.d("图文详情："+eventBean.getGoods_parameter());
              wv_view.loadDataWithBaseURL(null,
                      getHtmlData(eventBean.getGoods_parameter()), "text/html", "utf-8", null);
          }


    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        EventBus.getDefault().unregister(getActivity());
    }
}
