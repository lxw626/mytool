Ext.Loader.setConfig({enabled: true});
Ext.application({
    name: 'MyTool',
    appFolder:'/mytool',
    requires : [
        'MyTool.util.Util'
    ],
    // defaultToken : 'home',
    autoCreateViewport:true,
        launch: function() {

    }
});