Ext.define('menu.MenuList', {
	extend:'Ext.grid.Panel',
	alias:'widget.menuList',
	layout:'fit',
	reference:'menuList',
	region:'center',
	autoScroll:true,   
	columnLines:true,
	border:true,
	store : Ext.create('menu.MenuStore', {storeId : 'menuStore'}),
	selModel:{
		// 键盘导航， false则键盘操作无效
		enableKeyNav:true, 
		// 选择模式 SINGLE, SIMPLE, 和 MULTI
		mode:'SINGLE', 
		// 点击checkbox框选中
		checkOnly: false, 
		// 在表头显示全选checkbox框
		showHeaderCheckbox: false,
		// 复选框选择模式Ext.selection.CheckboxModel
		selType:'checkboxmodel',
		allowDeselect:true
	},

	
	viewConfig:{
		forceFit: true,
	 	scrollOffset: 0,
		enableTextSelection:true
	},
	
	columns: [
    	{
            xtype: 'rownumberer',
            width:50,
            align:'center',
            text:'序号'
        },
		{
			text : '凭证号',
			filter:'list',
			dataIndex:'orderId',
			align:'center',
			flex:1,
		},
		{
			text : '入库时间',
			align:'center',
			filter:'list',
			renderer : Ext.util.Format.dateRenderer('Y-m-d'),
			dataIndex:'inDate',
			width:120,
		},
		{
			text : '作业部',
			filter:'list',
			flex:1,
			align:'center',
			dataIndex:'plantCode',
			renderer : function(value) {
		    	return getOrgStructureValues('PLANT', value);
			   }
		},
		{
			text : '作业部描述',
			filter:'list',
			width:90,
			align:'center',
			dataIndex:'plantDesc',
		},
		
		/*{
			text : '成本中心',
			filter:'list',
			dataIndex:'costCenterCode',
			renderer : function(value) {
		    	return getOrgStructureValues('SCOPE', value);
			   }
		},*/
		{
			text : '库存地',
			align:'center',
			filter:'list',
			flex:1,
			dataIndex:'storCode'
		},
		{
			text : '库存地描述',
			filter:'list',
			width:90,
			align:'center',
			dataIndex:'storDesc',
		},
		{
			text : '物料编码',
			align:'center',
			filter:'list',
			dataIndex:'matNr',
		},
		{
			text : '物料描述',
			align:'center',
			filter:'list',
			dataIndex:'matDesc',
			
		},
		
		{
			text : '单位',
			align:'center',
			filter:'list',
			flex:1,
			dataIndex:'unit',
			renderer : function(value) {
		    	return getRefTableDetailValues('UNIT', value);
		    }
		},
		{
			text : '入库量',
			filter:'list',
			flex:1,
			align:'right',
			dataIndex:'amount',
			summaryType:  function (records) {
                var total = 0;
                for (var i = 0; i < records.length; i++) {
                	var field = records[i].get('amount');
                	if(typeof(field)=="undefined"){
                		field = 0;
                	}else{
                		if(isNaN(field)){
    	                	return '<span style=\'font-weight:1000;\'>计算错误</span>';
    	                }else{
    	                	total += Number(field);
    	                	total = Math.round(total*100)/100; 
    	                }
                	}
                }
                return '<span style=\'font-weight:1000;\'>合计:'+total+'</span>';
            }
		},
		{
			text : '数据来源',
			filter:'list',
			align:'center',
			flex:1,
			hidden:true,
			dataIndex:'dataSource',
			renderer : function(value) {
		    	return getRefTableDetailValues('LB_DATA_SOURCE', value);
		   }
		},
		{
			text : '入库类别',
			filter:'list',
			align:'center',
			flex:1,
			hidden:true,
			dataIndex:'inType',
			renderer : function(value) {
		    	return getRefTableDetailValues('LB_STOR_TYPE', value);
		   }
		},
		{
			text : '过账时间',
			filter:'list',
			align:'center',
			flex:1,
			dataIndex:'balTime',
			renderer : Ext.util.Format.dateRenderer('Y-m-d H:i:s'),
		},
		{
			text : '备注',
			align:'center',
			filter:'list',
			dataIndex:'notes',
		},
    ],
    dockedItems:[
	   {
		   xtype:'pagingtoolbar',
		   border : 1,
			style : {
				borderColor : '#9cc5f8',
				borderStyle : 'solid'
			},
		   store:'storInStore',
		   dock:'top',
		   displayInfo:true,
		   items:[{
			xtype:'button',
			text:translations.add,
			reference:'storInAddBtn',
			privilegeCode:'TLGLLB0401',
			handler:'storInAddBtnClick'
		},
		/*{
	     	xtype:'button',
	    	text:translations.update,
	    	formBind:true,
	    	reference:'storInEditBtn',
	    	privilegeCode:'MCSM0802',
	    	handler:'storInEditBtnClick',
	    },*/
	    /*{
	     	xtype:'button',
	    	text:translations.del,
	    	formBind:true,
	    	reference:'storInDeleteBtn',
	    	privilegeCode:'MCSM0803',
	    	handler:'storInDeleteBtnClick',
	    	disabled:false       	
	    },*/{
	    		xtype: 'button',
				iconCls: 'btn_page_operate',
				privilegeCode:'TLGLLB0405',
				menu:Ext.create('Ext.menu.Menu', {
					width: 50,
					margin: '0 0 10 0',
					floating: true, 
					renderTo: Ext.getBody(),
					items: [{
						text: '导出excel',
						listeners : {
							click : 'storInExcelBtnClick'
						}
					}/*
						 * ,{ text: '其它' }
						 */
					]
				})
			}],
		   plugins:[{
			   ptype:'pagingtoolbarresizer'
		   }]
		   
	   }
    	           ]
});
