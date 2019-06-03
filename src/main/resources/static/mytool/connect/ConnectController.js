Ext.define('Sgai.view.lb.storIn.StorInController', {
    extend : 'Ext.app.ViewController',
    alias : 'controller.storInController',

    render : function() {
        var me = this;
        me.storInQueryBtnClick();

    },

    getMaingrid : function() {
        return this.lookupReference('storInList');
    },

    // 根据表单中的条件查询数据
    storInQueryBtnClick : function() {
        var matGrid = this.getMaingrid();
        var store = matGrid.getStore();
        Sgai.util.Util.postPageForm(this.lookupReference('storInQueryForm'), this.getMaingrid());
        store.load({//加载
            callback:function(){//回调函数
//				matGrid.headerCt.getGridColumns()[5].autoSize();
//				matGrid.headerCt.getGridColumns()[6].autoSize();
//				matGrid.headerCt.getGridColumns()[14].autoSize();
            }
        });
    },

    // 重置表单
    storInResetBtnClick : function(button) {
        this.lookupReference('storInQueryForm').getForm().reset();
    },


    /**
     * 弹出新增记录窗口
     */
    storInAddBtnClick : function(btn, evt) {
        var winHandle;
        if (!winHandle) {
            winHandle = Ext.create('Sgai.view.lb.storIn.StorInWin', {
                operate : 'add',
                ctrl : this
            });
        }
        winHandle.show();
    },
    /**
     * 删除选中的数据
     */
    storInDeleteBtnClick : function(btn, evt) {
        var me = this;
        var grid = this.lookupReference('storInList');
        var selected = grid.getSelectionModel();//查询的当前的Modael内容
        var userName=Sgai.config.Runtime.getUserName(); //当前登录人
        if (selected.getSelection().length == 1) {
            var delArray = [];
            Ext.each(selected.getSelection(), function(item) {
                var param = {
                    sid : item.data.sid,
                    version : item.data.version,
                    upLoadBy:userName
                }
                delArray.push(param);
            });

            var jsonData = Ext.JSON.encode(delArray);
            Ext.Msg.confirm('操作提示', '确认删除数据？', function(btn2) {
                if (btn2 == 'no') {
                    return;
                } else {
                    var url = "lb/storIn/destroy.action";
                    Sgai.util.Util.postAjaxRequestByJsonData(url,
                        jsonData, true, function() {
                            grid.getStore().reload();
                            Sgai.util.Util.showTipMsg('删除记录成功!');
                        }, function() {
                        }, btn);
                }
            })
        } else {
            Sgai.util.Util.showTipMsg('请选择待删除的记录!');
            return;
        }
    },
    /**
     * 修改选中的记录
     */
    storInEditBtnClick : function(btn, evt) {
        //结束：
        var me = this;
        var winHandle;
        var grid = this.lookupReference('storInList');
        var selected =grid.getSelectionModel();
        if (selected.getSelection().length==1) {
            if (!winHandle) {
                winHandle = Ext.create('Sgai.view.lb.storIn.StorInWin',
                    {operate:'edit',
                        ctrl:me
                    });
            }
            var form = winHandle.lookupReference('storInWinForm');
            form.loadRecord(selected.getSelection()[0]);
            winHandle.show();
        } else {
            Sgai.util.Util.showTipMsg('请选择一条要修改的记录!');
            return;
        }
    },
    /**
     * 导出Excle表
     */
    storInExcelBtnClick : function(button, evt){
        var form = this.lookupReference('storInQueryForm');
        var formParams = Sgai.util.Util.getFormParams(form);
        var grid = this.lookupReference('storInList');
        var url ='lb/storIn/exportExcel.action';
        formParams['excelName'] = "入库信息";
        formParams['excelTitle'] ='入库信息';
        Sgai.util.Util.exportExcel(url, grid,formParams);
    },

    /**
     * 去除空格
     */
    trimQH : function(str) {
        return str.replace(/(^\s*)|(\s*$)/g, "");
    }
})
