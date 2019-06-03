Ext.define('menu.MenuQueryForm', {
	extend:'Ext.form.Panel',
	alias:'widget.menuQueryForm',
	layout:'fit', 
	reference:'menuQueryForm',
	layout:{
	   type:'table',
	   columns:4
   	},
   	title:'菜单查询条件',
   	collapsible : true,
	defaults : {
		xtype : 'textfield',
		flex:1,
		width:'100%',
		labelAlign : 'right'
	},
	bodyPadding : 10,
	
	items:[
        {
            name : 'menuName',
			reference : 'menuName',
			fieldLabel : '菜单名称',
        },
        {
            name : 'menuName',
            reference : 'menuName',
            fieldLabel : '菜单地址',
        },
        {
            name : 'menuName',
            reference : 'menuName',
            fieldLabel : '菜单地址',
        },
        {
            name : 'menuName',
            reference : 'menuName',
            fieldLabel : '菜单地址',
        },
        {
            name : 'menuName',
            reference : 'menuName',
            fieldLabel : '菜单地址',
        },
	{
		xtype:'tbfill',
		colspan : 2
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
