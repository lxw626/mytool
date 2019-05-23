var store = new Ext.data.Store({
    pageSize : 15,
    remoteSort : 'true',
    fields:  [],

    proxy: {
        type: 'ajax',
        url: 'http://localhost:8888/helloJdbcTemplate',
        actionMethods : {
            read : 'POST'
        },
        reader: {
            type: 'json',
            totalProperty: 'totalProperty',
            rootProperty: 'list',
            columnNames:'columnNames'
        }
    },
    autoLoad : false,
});
Ext.define("ResultPanel", {
    extend: "Ext.grid.Panel",
    alias:'widget.resultPanel',
    id:'resultPanelId',
    region : 'center',
    tableName:'',
    columnNames:[],
    columnLines:true,
    collapsible :true,
    resizable:true,
    collapseDirection:'bottom',
    title: '查询结果',
    layout:"fit",
    selModel:{
        // 键盘导航， false则键盘操作无效
        enableKeyNav:true,
        // 选择模式 SINGLE, SIMPLE, 和 MULTI
        mode:'SINGLE',
        // 点击checkbox框选中
        checkOnly: false,
        // 在表头显示全选checkbox框
        showHeaderCheckbox: false,
        // 复选框选择模式Ext.selection.CheckboxModel
        selType:'checkboxmodel',
        width:5,
        allowDeselect:true
    },
    store :store,
    columns : [],
    dockedItems: [{
        xtype: 'pagingtoolbar',
        itemId:'resultPanelPagingtoolbar',
        store: store,   // GridPanel中使用的数据
        dock: 'bottom',
        displayInfo: true
    }],

});



