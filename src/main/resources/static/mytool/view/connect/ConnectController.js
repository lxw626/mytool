Ext.define('MyTool.view.connect.ConnectController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.connectController',
    render: function() {
        var me = this;
        me.connectQueryBtnClick();
    },
    getMaingrid: function() {
        return this.lookupReference('connectList');
    },
    // 根据表单中的条件查询数据
    connectQueryBtnClick: function() {
        var matGrid = this.getMaingrid();
        var store = matGrid.getStore();
        MyTool.util.Util.postPageForm(this.lookupReference('connectQueryForm'), this.getMaingrid());
        store.load({ //加载
            callback: function() { //回调函数
                //				matGrid.headerCt.getGridColumns()[5].autoSize();
            }
        });
    },

    // 重置表单
    connectResetBtnClick: function(button) {
        this.lookupReference('connectQueryForm').getForm().reset();
    },
    /**
     * 弹出新增记录窗口
     */
    connectAddBtnClick: function(btn, evt) {
        var winHandle;
        if (!winHandle) {
            winHandle = Ext.create('connect.ConnectWin', {
                operate: 'add',
                ctrl: this
            });
        }
        winHandle.show();
    },
    /**
     * 删除选中的数据
     */
    connectDeleteBtnClick: function(btn, evt) {
        var me = this;
        var grid = this.lookupReference('connectList');
        var selected = grid.getSelectionModel(); //查询的当前的Modael内容
        if (selected.getSelection().length > 0) {
            var sids = [];
            Ext.each(selected.getSelection(),
                function(item) {
                    sids.push(item.data.sid);
                });
            var params = {
                sids: sids
            };
            Ext.Msg.confirm('操作提示', '确认删除数据？',
                function(btn2) {
                    if (btn2 == 'no') {
                        return;
                    } else {
                        var url = "/connect/delete";
                        Ext.Ajax.request({
                            method: 'post',
                            url: url,
                            jsonData: sids,
                            success: function() {
                                grid.getStore().reload();
                                MyTool.util.Util.showTipMsg('删除记录成功!');
                            },
                            failure: function() {
                                Ext.Msg.show({
                                    title: '提示',
                                    buttons: Ext.MessageBox.OK,
                                    msg: '请求失败'
                                })
                            }
                        });
                    }
                })

        } else {
            MyTool.util.Util.showTipMsg('请选择待删除的记录!');
            return;
        }
    },
    /**
     * 修改选中的记录
     */
    connectEditBtnClick: function(btn, evt) {
        //结束：
        var me = this;
        var winHandle;
        var grid = this.lookupReference('connectList');
        var selected = grid.getSelectionModel();
        if (selected.getSelection().length == 1) {
            if (!winHandle) {
                winHandle = Ext.create('connect.ConnectWin', {
                    operate: 'edit',
                    ctrl: me
                });
            }
            var form = winHandle.lookupReference('connectWinForm');
            form.loadRecord(selected.getSelection()[0]);
            winHandle.show();
        } else {
            Ext.Msg.show({
                title: '提示',
                buttons: Ext.MessageBox.OK,
                msg: '请选择一条要修改的记录!'
            });
            return;
        }
    },
    /**
     * 导出Excle表
     */
    exportExcelBtnClick: function(button, evt) {

        var form = this.lookupReference('connectQueryForm');
        var formParams = MyTool.util.Util.getFormParams(form);
        var grid = this.lookupReference('connectList');
        var url = '/connect/exportExcel';
        formParams['excelName'] = "入库信息";
        formParams['excelTitle'] = '入库信息';
        MyTool.util.Util.exportExcel(url, grid, formParams);
    },
    /**
     * 导入Excel
     * @param button
     * @param evt
     */
    importExcelBtnClick:function(button, evt){
        var winHandle;
        if (!winHandle) {
            winHandle = Ext.create('MyTool.view.connect.ExcelUpload', {operate:'importStaff',ctrl:this});
        }
        winHandle.show();
    },
    /**
     * 导入开始上传方法
     */
    clickUpload : function() {
        var me = this;
        var form = this.lookupReference('uploadExcelForm');
        var uploadUrl= me.lookupReference('uploadExcel').getValue();
        if (uploadUrl == null || uploadUrl == '') {
            Ext.Msg.alert("提示", "请选择上传文件!");
            return;
        }
        var url = "/connect/importExcel";
        form.getForm().submit({
            url : url,
            waitMsg : '正在提交数据...',
            method : 'POST',
            submitEmptyText : false,
            success : function(form, action) {
                Ext.MessageBox.show({
                    title : '操作提示',
                    msg : Ext.JSON.decode(action.response.responseText).message,
                    buttons : Ext.MessageBox.OK,
                    icon : Ext.MessageBox.INFO
                });
                var parent = me.getView();
                var parentGrid = parent.ctrl.lookupReference('connectList');
                parentGrid.getStore().reload();
                me.closeView();
            },
            failure : function(form, action) {
                Ext.MessageBox.show({
                    title : '操作提示',
                    msg : Ext.JSON.decode(action.response.responseText).message,
                    buttons : Ext.MessageBox.OK,
                    icon : Ext.MessageBox.INFO
                });
            }
        });
    },


    /**
     * 去除空格
     */
    trimQH: function(str) {
        return str.replace(/(^\s*)|(\s*$)/g, "");
    }
})