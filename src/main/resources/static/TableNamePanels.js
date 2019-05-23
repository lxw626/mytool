Ext.Loader.setConfig({enabled:true});
Ext.define("TableNamePanels", {
    extend: "Ext.panel.Panel",
    alias:'widget.tableNamePanels',
    frame: true,
    requires:[
        'TableNamePanel'
    ],
    items:[
        {
            xtype:'tableNamePanel',
            title:'xxx.xxx'
        }
    ]
});



