Ext.define("NavigatorPanel", {
    extend: "Ext.panel.Panel",
    alias:'widget.navigatorPanel',
    // frame: true,
    resizable:true,
    tbar:[
        {
            text:"新建连接",
            handler: function addConnection()
            {
                var winHandle = Ext.create('ConnectionWin', {
                    operate : 'add',
                    ctrl : this
                });
                winHandle.show();
            }
        },
        {
            text:"新建查询",
        }
    ]
});
/*function addConnection(){
    var winHandle = Ext.create('ConnectionWin', {
        operate : 'add',
        ctrl : this
    });
    winHandle.show();
}*/


