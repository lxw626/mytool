Ext.define('menu.MenuView', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.menuView',
    layout: 'fit',
    reference: 'menuView',
    margin: '0 1 0 1',
    requires: [
        'menu.MenuQueryForm',
        // 'MenuList',
        // 'MenuController',
        // 'MenuWin',
        // 'MenuWinController',
    ],

    // controller: 'menuController',

    dockedItems: [
        {
            xtype: 'menuQueryForm',
            // xtype: 'panel'
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
                    // xtype: 'menuList',
                    xtype: 'panel',
                    frame:true,
                    flex: 4
                }
            ]
        }
    ]
});
