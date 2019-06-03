Ext.define('connect.ConnectQueryForm', {
	extend:'Ext.form.Panel',
	alias:'widget.connectQueryForm',
	layout:'fit', 
	reference:'connectQueryForm',
	layout:{
	   type:'table',
	   columns:4
   	},
   	title:'查询条件',
   	collapsible : true,
	frame:true,
	defaults : {
		xtype : 'textfield',
		flex:1,
		width:'100%',
		labelAlign : 'right'
	},
	bodyPadding : 10,
	
	items:[
        {
            name : 'DBType',
			reference : 'DBType',
			fieldLabel : '数据库类型',
        },
        {
            name : 'connectName',
            reference : 'connectName',
            fieldLabel : '连接名',
        },
        {
            name : 'connectAddress',
            reference : 'connectAddress',
            fieldLabel : '地址',
        },
        {
            name : 'connectPort',
            reference : 'connectPort',
            fieldLabel : '端口号',
        },
        {
            name : 'userName',
            reference : 'userName',
            fieldLabel : '用户名',
        },
        {
            name : 'password',
            reference : 'password',
            fieldLabel : '密码',
        },
	{
		xtype:'tbfill',
		colspan : 1
	 },
	 {
		xtype:'panel',
		layout:{
			type : 'hbox',//水平盒布局
			pack : 'start',
			align : 'stretch'
		},
		items:[
			{
				xtype:'tbfill'
			},
			{
				xtype:'button',
				text:'查询',
				margin : '0 2 0 0',
				// handler:'storInQueryBtnClick',
				formBind:true
			},{
				xtype:'button',
				text:'重置',
				margin : '0 0 0 2',
				handler:'storInResetBtnClick'
					
			},
		]
	}
		        
	]
});
