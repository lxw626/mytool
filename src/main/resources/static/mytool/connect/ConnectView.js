Ext.define('connect.ConnectView', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.connectView',
    layout: 'fit',
    reference: 'connectView',
    margin: '0 1 0 1',
    requires: [
        'connect.ConnectQueryForm',
        'connect.ConnectList',
        'connect.ConnectController',
        'connect.ConnectWin',
        'connect.ConnectWinController',
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
                type: 'hbox',//垂直盒布局
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
