package com.lxw.mytool.service;

import com.lxw.mytool.model.QueryFormModel;
import com.lxw.mytool.model.ViewModel4MyTool;

public class FE4MyToolService {
    /**
     * 生产XxxView.js代码
     * @param nameSpace
     * @param coreClassName
     * @return
     */
    public String getView(String nameSpace,String coreClassName){
        String viewModel = ViewModel4MyTool.viewModel;
        viewModel = viewModel.replaceAll("<#nameSpace>",nameSpace);
        viewModel = viewModel.replaceAll("<#coreClassName>",coreClassName);
        return viewModel;
    }
    public void getQueryForm(){
        String itemModel = QueryFormModel.itemModel;
    }

}
