Ext.define('connect.ConnectWin',{
    extend:'Ext.window.Window',
    alias:'widget.connectWin',
    // id:'connectWin',
    
    controller:'connectWinController',
    title:'连接管理',
    layout:'fit',
    modal:true,//遮罩效果

	listeners:{

	},
	
    items:[
       {
    	   	xtype: 'form',
    	   	fieldDefaults:{
 	           labelAlign:'right',
 	           labelWidth:100,
 	           msgTarget:'tip',
 	          margin : '5 50 5 0'
 	       	},
    	   	reference:'connectWinForm',
    	   	region:'center',
    	   	layout : 'vbox',
	       	items: [
       	        {
					xtype : 'fieldset',
					layout: {
					   type: 'table',
					   columns: 2
			       	},
			       	flex:5,
			       	scrollable: true,
					items:[
                        {
                            xtype : 'textfield',
                            name : 'dbtype',
                            reference : 'dbtype',
                            fieldLabel : '数据库类型',
                        },
                        {
                            xtype : 'textfield',
                            name : 'connectname',
                            reference : 'connectname',
                            fieldLabel : '连接名',
                        },
                        {
                            xtype : 'textfield',
                            name : 'connectaddress',
                            reference : 'connectaddress',
                            fieldLabel : '地址',
                        },
                        {
                            xtype : 'textfield',
                            name : 'connectport',
                            reference : 'connectport',
                            fieldLabel : '端口号',
                        },
                        {
                            xtype : 'textfield',
                            name : 'username',
                            reference : 'username',
                            fieldLabel : '用户名',
                        },
                        {
                            xtype : 'textfield',
                            name : 'password',
                            reference : 'password',
                            fieldLabel : '密码',
                        },
					]
				},
				{
					xtype:'hidden',
					name: 'version',
					value:'1',
					reference:'version'
				},
				{
					xtype:'hidden',
					name: 'sid',
					reference:'sid'
				}],
	       	buttons: ['->', 
				{
				    text: '保存',
				    handler:'connectWinSubmit',
				    formBind:true
				}, 
				{
				    text: '取消',
				    handler:'connectWinClose'
				}
	        ]
       	}	
    ]
});

