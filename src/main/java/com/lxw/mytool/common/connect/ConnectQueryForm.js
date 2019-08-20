Ext.define('<#nameSpace>.<#coreClassName>QueryForm', {
	extend:'Ext.form.Panel',
	alias:'widget.<#coreClassName>QueryForm',
	layout:'fit', 
	reference:'<#coreClassName>QueryForm',
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
		<#items>
        {
            name : 'dbtype',
			reference : 'dbtype',
			fieldLabel : '数据库类型',
        },
		{
			xtype:'tbfill',
			colspan : <#tbfillnum>
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
				handler:'<#coreClassName>QueryBtnClick',
				formBind:true
			},{
				xtype:'button',
				text:'重置',
				margin : '0 0 0 2',
				handler:'<#coreClassName>ResetBtnClick'
			},
		]
	}
		        
	]
});
