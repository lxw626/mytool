Ext.define('MyApp.view.Viewport', {
	extend:'Ext.Viewport',
	frame:true,
    requires:[
    	'MyApp.view.MyPanel'
	],
    items: [{
        items: [
			{
				xtype:'panel',
				title:'mmp'
			},{
                xtype:'myPanel',
			}
        ]
    }]

});
