Ext.define('connect.ConnectList', {
    extend: 'Ext.grid.Panel',
    alias: 'widget.connectList',
    layout: 'fit',
    reference: 'connectList',
    region: 'center',
    autoScroll: true,
    columnLines: true,
    store: Ext.create('connect.ConnectStore', {storeId: 'connectStore'}),
    selModel: {
        // 键盘导航， false则键盘操作无效
        enableKeyNav: true,
        // 选择模式 SINGLE, SIMPLE, 和 MULTI
        mode: 'SINGLE',
        // 点击checkbox框选中
        checkOnly: false,
        // 在表头显示全选checkbox框
        showHeaderCheckbox: false,
        // 复选框选择模式Ext.selection.CheckboxModel
        selType: 'checkboxmodel',
        allowDeselect: true
    },
    viewConfig: {
        forceFit: true,
        scrollOffset: 0,
        enableTextSelection: true
    },
    columns: [
        {
            xtype: 'rownumberer',
            width: 50,
            align: 'center',
            text: '序号'
        },
        {
            text: '数据库类型',
            filter: 'list',
            dataIndex: 'DBType',
            align: 'center',
            flex: 1,
        },
        {
            text: '连接名',
            filter: 'list',
            flex: 1,
            align: 'center',
            dataIndex: 'connectName',
        },
        {
            text: '地址',
            filter: 'list',
            width: 200,
            align: 'center',
            dataIndex: 'connectAddress',
        },

        {
            text: '端口号',
            align: 'center',
            filter: 'list',
            flex: 1,
            dataIndex: 'connectPort'
        },
        {
            text: '用户名',
            filter: 'list',
            flex: 1,
            align: 'center',
            dataIndex: 'userName',
        },
        {
            text: '密码',
            align: 'center',
            filter: 'list',
            flex: 1,
            dataIndex: 'password',
        }

    ],
    dockedItems: [
        {
            xtype: 'pagingtoolbar',
            border: 1,
            style: {
                borderColor: '#9cc5f8',
                borderStyle: 'solid'
            },
            store: 'connectStore',
            dock: 'top',
            displayInfo: true,
            items: [
                {
                    xtype: 'button',
                    text: '新增',
                    reference: 'menuAddBtn',
                    // handler: 'menuAddBtnClick'
                },
                {
                     xtype:'button',
                    text:'修改',
                    formBind:true,
                    reference:'connectEditBtn',
                    // handler:'connectEditBtnClick',
                },
                {
                     xtype:'button',
                    text:'删除',
                    formBind:true,
                    reference:'connectDeleteBtn',
                    // handler:'connectDeleteBtnClick',
                },
                {
                    xtype: 'button',
                    iconCls: 'btn_page_operate',
                    connect: Ext.create('Ext.menu.Menu', {
                        width: 50,
                        margin: '0 0 10 0',
                        floating: true,
                        renderTo: Ext.getBody(),
                        items: [{
                            text: '导出excel',
                            listeners: {
                                // click: 'connectExcelBtnClick'
                            }
                        }
                        ]
                    })
                }],
           /* plugins: [
                {
                    ptype: 'pagingtoolbar'
                }
            ]*/

        }
    ]
});
