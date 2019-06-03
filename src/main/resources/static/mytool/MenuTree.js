var store = Ext.create('Ext.data.TreeStore', {
    autoLoad: true,
    fields:[
        {name:'sid', type:'auto'},
        {name:'parentSid', type:'int'},
        {name:'resId', type:'string'},
        {name:'resName', type:'string'},
        {name:'resType', type:'string'},
        {name:'resPageUrl', type:'string'},
        {name:'relAccessRul', type:'string'},
        {name:'resLevel', type:'int'},
        {name:'resSeq', type:'int'},
        {name:'resVisibility', type:'int'},
        {name:'createdBy', type:'string'},
        {name:'createdDt', type:'date'},
        {name:'updatedBy', type:'string'},
        {name:'updatedDt', type:'date'},
        {name:'version', type:'int', critical:true}
    ],
    root: {
        expanded: false
    },
    proxy: {
        type:'ajax',
        actionMethods:{read: 'POST'},
        api:
            {
                // read: 'http://localhost:8888/getLocalMenus'
                read: 'http://localhost:8888/getMysqlMenus'
                // read: 'http://localhost:8888/getOracleMenus'
            },
        reader: {
            type: 'json',
            rootProperty: 'items'
        }
    }
});
Ext.define("MenuTree", {
    extend: "Ext.tree.Panel",
    alias: 'widget.menuTree',
    title: '首钢京唐PES系统',
    rootVisible: false,
    hideHeaders:true,
    animate:true,
    useArrows: true,
    autoScroll : true,
    frame:false,
    border:true,
    store: store,
    reference:'menuTree',
    viewConfig: {
        stripeRows:false,
        forceFit: true,
        scrollOffset: 0,
        enableTextSelection:false
    },
    listeners : {
        render : 'onMenuPanelRender',
        itemclick: 'onMenuItemClick'
    },
    columns: [
        {
            xtype: 'treecolumn',
            dataIndex: 'resName',
            flex:1,
            // text: translations.resList,
            style: {
                'font-size': '20px'
            }
        }
    ]
});



