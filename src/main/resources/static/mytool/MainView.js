/**
 * 主界面
 */
// Ext.Loader.setConfig({enabled:true});
Ext.define('MainView', {
    extend: 'Ext.container.Viewport',
	alias: 'widget.mainView',
    requires: [
    	'MenuTree',
        // 'Sgai.view.Header',
        'MainPanel',
        // 'Sgai.view.EastPanel',
        'MainController'
    ],

    controller: 'mainController',//指定控制器

    layout: {
        type: 'border'
    },

    items: [  
        {
            xtype: 'panel',
            region: 'north',
            html:"<h2>首钢京唐</h2>",
            height:30
        },
        {
            xtype: 'menuTree',
            region: 'west',
            width: 230,
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
    
	// listeners : {
	// 	render : 'onMainRender',
	// 	beforerender:'onBeforeRender'
	// }
});