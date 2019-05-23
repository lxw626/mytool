Ext.define("ConnectionWin", {
    extend: "Ext.window.Window",
    alias:'widget.connectionWin',
    // frame: false,
    width:500,
    height:400,

    items:[
        {
            xtype:'form',
            fieldDefaults:{
                xtype:'textfield',
                labelAlign:'right',
                labelWidth:100,
                height:30,
                width:400,
                margin : '20'
            },
            items:[
                {
                    xtype:'textfield',
                    fieldLabel:'数据库类型',
                },
                {
                    xtype:'textfield',
                    fieldLabel:'IP地址',
                },
                {
                    xtype:'textfield',
                    fieldLabel:'用户名',
                },
                {
                    xtype:'textfield',
                    fieldLabel:'密码',
                },
                {
                    xtype:'textfield',
                    fieldLabel:'服务名',
                },
                {
                    xtype:'textfield',
                    fieldLabel:'端口号',
                },
            ]
        }

    ]
});




