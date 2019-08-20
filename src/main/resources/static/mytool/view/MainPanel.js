Ext.define('MyTool.view.MainPanel', {
    extend: 'Ext.tab.Panel',
    alias: 'widget.mainPanel',
    id:'mainPanel',
    reference:'mainPanel',
    activeTab: 0,
    plain: true,
    requires:[
        'MyTool.view.connect.ConnectView'
    ],
    items: [
        {
            xtype: 'panel',
            closable: false,
            title: '主页',
            layout:'fit',
            items : [
                {
                    xtype : 'connectView'
                }
            ]
        }
    ],
   /* listeners:{
        tabchange : 'mainPanelTabChange'
    }*/
});