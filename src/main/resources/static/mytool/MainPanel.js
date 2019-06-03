Ext.define('MainPanel', {
    extend: 'Ext.tab.Panel',
    alias: 'widget.mainPanel',
    id:'mainPanel',
    reference:'mainPanel',
    activeTab: 0,
    plain: true,
    items: [
        {
            xtype: 'panel',
            closable: false,
            title: '主页',
            layout:'fit',
            items : [
                {
                    xtype : 'panel'
                }
            ]
        }
    ],
});