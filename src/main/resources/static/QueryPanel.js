Ext.define("QueryPanel", {
    extend: "Ext.panel.Panel",
    alias: 'widget.queryPanel',
    id: 'queryPanelId',
    // frame: true,
    collapsible: true,
    resizable: true,
    collapseDirection: 'top',
    title: '查询条件',
    layout: "fit",
    items: [
        {
            xtype: "textareafield",
            itemId: "sqlTextAreaField",
            value: 'select * from mes_md_person'
        }
    ],
    buttons: [
        {
            text: '新增',
            handler: function () {
                var resultPanel = Ext.getCmp('resultPanelId');
                var tableName = resultPanel.tableName;
                var selected =resultPanel.getSelectionModel();
                var winHandle;
                if (!winHandle) {
                    winHandle = Ext.create('Ext.window.Window', {
                        operate : 'add',
                        ctrl : this,
                        itemId:'sqlAddWin',
                        layout:'fit',
                        items:[
                            {
                                xtype: 'form',
                                itemId:'sqlAddForm',
                                frame:true,
                                fieldDefaults:{
                                    labelAlign:'right',
                                    labelWidth:150,
                                },
                                margin : '5 10 10 5',

                                items:[
                                    {
                                        xtype : 'fieldset',
                                        layout: {
                                            type: 'table',
                                            columns: 3
                                        },
                                        scrollable: true,
                                        items:[
                                            {
                                                xtype:'textfield',
                                                name:'tableName',
                                                value:tableName,
                                            }
                                        ],
                                    }
                                ],
                            }
                        ],
                        buttons:[
                            {
                                text:'保存',
                                handler:function(){
                                    var form = Ext.ComponentQuery.query("#sqlAddForm")[0].getForm();
                                    form.submit(
                                        {
                                            url: 'http://localhost:8888/dbViewController/addOneData',
                                            method: 'POST',
                                            waitTitle: "提示",
                                            waitMsg: '正在提交数据，请稍后 ……',
                                            success: function (form, action) {
                                                Ext.Msg.alert('success', 'bbb  ' + form
                                                    + action.result.msg);
                                                // this.close();
                                            },
                                            failure: function (form, action) {
                                                var errMsg = Ext.decode(action.response.responseText).message
                                                Ext.Msg.show({
                                                    title:'新增失败',
                                                    buttons:Ext.MessageBox.OK,
                                                    msg:errMsg
                                                })
                                                // this.close();
                                            }
                                        }
                                    )
                                    // Ext.ComponentQuery.query("#sqlAddForm")[0].close();
                                }
                            },
                            {
                                text:'取消',
                                handler:function(){
                                    Ext.ComponentQuery.query("#sqlAddWin")[0].close();
                                }
                            }
                        ]
                    });
                }
                var columnNames = resultPanel.columnNames
                var fieldset = winHandle.down("fieldset")
                for(var i = 0;i<columnNames.length;i++){
                    var column = {
                        xtype:'textfield',
                        name:columnNames[i],
                        fieldLabel:columnNames[i]
                    }

                    fieldset.add(column)
                }
                if (selected.getSelection().length==1) {
                    var form = Ext.ComponentQuery.query("#sqlAddForm")[0];
                    form.loadRecord(selected.getSelection()[0]);
                }
                winHandle.show();
                // store.insert(0, {})
            }
        },
        {
            text: '修改',
            handler: function () {
                var resultPanel = Ext.getCmp('resultPanelId');
                var tableName = resultPanel.tableName;
                var selected =resultPanel.getSelectionModel();
                if (selected.getSelection().length==1) {
                    var rowId = selected.selected.items[0].data.ROW_ID;
                    var winHandle;
                    if (!winHandle) {
                        winHandle = Ext.create('Ext.window.Window', {
                            operate : 'edit',
                            ctrl : this,
                            itemId:'sqlEditWin',
                            layout:'fit',
                            items:[
                                {
                                    xtype: 'form',
                                    itemId:'sqlEditForm',
                                    frame:true,
                                    fieldDefaults:{
                                        labelAlign:'right',
                                        labelWidth:150,
                                    },
                                    margin : '5 10 10 5',

                                    items:[
                                        {
                                            xtype : 'fieldset',
                                            layout: {
                                                type: 'table',
                                                columns: 3
                                            },
                                            scrollable: true,
                                            items:[
                                                {
                                                    xtype:'textfield',
                                                    name:'tableName',
                                                    hidden:true,
                                                    value:tableName,
                                                },
                                                {
                                                    xtype:'textfield',
                                                    name:'rowId',
                                                    hidden:true,
                                                    value:rowId,
                                                }
                                            ],
                                        }
                                    ],
                                }
                            ],
                            buttons:[
                                {
                                    text:'保存',
                                    handler:function(){
                                        var form = Ext.ComponentQuery.query("#sqlEditForm")[0].getForm();
                                        form.submit(
                                            {
                                                url: 'http://localhost:8888/dbViewController/updateOneData',
                                                method: 'POST',
                                                waitTitle: "提示",
                                                waitMsg: '正在提交数据，请稍后 ……',
                                                success: function (form, action) {
                                                    Ext.Msg.alert('success', 'bbb  ' + form
                                                        + action.result.msg);
                                                    // this.close();
                                                },
                                                failure: function (form, action) {
                                                    var errMsg = Ext.decode(action.response.responseText).message
                                                    Ext.Msg.show({
                                                        title:'修改失败',
                                                        buttons:Ext.MessageBox.OK,
                                                        msg:errMsg
                                                    })
                                                    // this.close();
                                                }
                                            }
                                        )
                                        // Ext.ComponentQuery.query("#sqlAddForm")[0].close();
                                    }
                                },
                                {
                                    text:'取消',
                                    handler:function(){
                                        Ext.ComponentQuery.query("#sqlEditWin")[0].close();
                                    }
                                }
                            ]
                        });
                    }
                    var columnNames = resultPanel.columnNames
                    var fieldset = winHandle.down("fieldset")
                    for(var i = 0;i<columnNames.length;i++){
                        var column = {
                            xtype:'textfield',
                            name:columnNames[i],
                            fieldLabel:columnNames[i]
                        }

                        fieldset.add(column)
                    }
                    var form = Ext.ComponentQuery.query("#sqlEditForm")[0];
                    form.loadRecord(selected.getSelection()[0]);
                }else{
                    Ext.Msg.show({
                        title:'提示',
                        buttons:Ext.MessageBox.OK,
                        msg:'请选择要修改的数据'
                    })
                }
                winHandle.show();
                // store.insert(0, {})
            }
        },
        {
            text: '删除',
            handler: function () {
                var resultPanel = Ext.getCmp('resultPanelId');
                var tableName = resultPanel.tableName;
                var selected =resultPanel.getSelectionModel();
                if (selected.getSelection().length>0) {
                    var items = selected.selected.items;
                    var rowIds = [];
                    for(var i=0;i<items.length;i++){
                        rowIds.push(items[i].data.ROW_ID)
                    }
                    Ext.Ajax.request({
                        method:'post',
                        url:'http://localhost:8888/dbViewController/deleteData',
                        params:{
                            'tableName':tableName,
                            'rowIds':rowIds
                        },
                        success:function (response){
                            var text = response.responseText;
                            var json = Ext.JSON.decode(text);
                            var name = json.data[0].name;
                        },
                        failure:function(){
                            Ext.Msg.show({
                                title:'提示',
                                buttons:Ext.MessageBox.OK,
                                msg:'请求失败'
                            })
                        }
                    });
                }else{
                    Ext.Msg.show({
                        title:'提示',
                        buttons:Ext.MessageBox.OK,
                        msg:'请选择要删除的数据'
                    })
                }
            }
        },
        {
            text: 'Excel导入'
        },
        {
            text: 'Excel导出',
            handler: function () {
                var sqlTextAreaField = Ext.ComponentQuery.query('#sqlTextAreaField')[0];
                var sql = sqlTextAreaField.getValue();
                window.location.href = "/exportExcel?sql=" + sql;
            }
        },
        {
            text: '查询',
            handler: function () {

                var sqlTextAreaField = Ext.ComponentQuery.query('#sqlTextAreaField')[0];
                var sql = sqlTextAreaField.getValue();
                var myColumns = [];
                Ext.Ajax.request({
                    method:'post',
                    url:'dbViewController/getColumnNames?_dc='+new Date().getTime(),
                    async:false,
                    params:{
                        'sql':sql,
                    },
                    success:function (response){
                        var resultPanel = Ext.getCmp('resultPanelId');
                        var store = resultPanel.getStore();
                        var text = response.responseText;
                        var result = Ext.JSON.decode(text);
                        var columnNames = result.columnNames;
                        var tableName = result.tableName;
                        resultPanel.columnNames = columnNames;
                        resultPanel.tableName = tableName;
                        for(var i=0;i<columnNames.length;i++){
                            var myColumn = {
                                dataIndex: columnNames[i],
                                text: columnNames[i],
                            };
                            if (columnNames.length < 8) {
                                myColumn.flex = 1;
                            }else{
                                myColumn.width = 50+columnNames[i].length*8;
                            }
                            myColumns.push(myColumn);
                        }
                        resultPanel.reconfigure(myColumns);
                        var params = {};
                        params.sql = sql;
                        store.on('beforeload', function (store) {
                            store.proxy.extraParams = params;
                        });
                        store.loadPage(1, {
                            callback: function (records, operation, success) {}
                        });

                    },
                    failure:function(){
                        Ext.Msg.show({
                            title:'提示',
                            buttons:Ext.MessageBox.OK,
                            msg:'获取表字段信息失败'
                        })
                    }
                });






                /*var resultPanel = Ext.getCmp('resultPanelId');
                var store = resultPanel.getStore();

                var params = {};
                params.sql = sql;
                store.on('beforeload', function (store) {
                    store.proxy.extraParams = params;
                });
                store.loadPage(1, {
                    callback: function (records, operation, success) {
                        var myColumns = [];
                        var data = records.shift().data
                        var columnNames = [];
                        for (var key in data) {
                            columnNames.push(key);
                        }
                        for (var i = 0; i < columnNames.length; i++) {
                            if (columnNames[i] != "ROW_ID" && columnNames[i] != "id") {
                                var myColumn = {
                                    dataIndex: columnNames[i],
                                    text: columnNames[i],
                                };
                                if (columnNames.length < 8) {
                                    myColumn.flex = 1;
                                }
                                myColumns.push(myColumn);
                            }
                        }
                        resultPanel.reconfigure(myColumns);
                    }
                });*/


            }
        },
    ]
});



