/**
 * 主界面
 */
Ext.define('MyTool.view.Viewport', {
    extend: 'Ext.Viewport',
    requires: [
    	'MyTool.view.MenuTree',
        'MyTool.view.MainPanel',
        'MyTool.view.MainController'
    ],
    frame:true,
    hideBorders: false,

    controller: 'mainController',//指定控制器

    layout: {
        type: 'border'
    },

    items: [  
        {
            xtype: 'panel',
            region: 'north',
            html:"<h2>首钢京唐X</h2>",
            height:30
        },
        {
            xtype: 'menuTree',
            region: 'west',
            width: 150,
            collapsible: true,
            frame:true,
        },
        {
            xtype: 'mainPanel',
            region: 'center',
            flex:1,
            frame:true,
        },

    ],
    

});