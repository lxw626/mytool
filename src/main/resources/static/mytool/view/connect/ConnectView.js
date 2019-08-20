Ext.define('MyTool.view.connect.ConnectView', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.connectView',
    layout: 'fit',
    reference: 'connectView',
    margin: '0 1 0 1',
    requires: [
        'MyTool.view.connect.ConnectQueryForm',
        'MyTool.view.connect.ConnectList',
        'MyTool.view.connect.ConnectController',
        'MyTool.view.connect.ConnectWin',
        'MyTool.view.connect.ConnectWinController',
    ],

    controller: 'connectController',

    dockedItems: [
        {
            xtype: 'connectQueryForm',
        }
    ],
    items: [
        {
            xtype: 'container',
            layout: {
                type: 'hbox',
                pack: 'start',
                align: 'stretch'
            },
            items: [
                {
                    xtype: 'connectList',
                    flex: 4
                }
            ]
        }
    ]
});
