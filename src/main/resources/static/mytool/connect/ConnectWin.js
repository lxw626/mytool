Ext.define('Sgai.view.lb.storIn.StorInWin',{
    extend:'Ext.window.Window',
    alias:'widget.storInWin',
    id:'storInWin',
    
    controller:'storInWinController',
    title:'入库',
    resizable:false,
    closable:true,
    layout:'fit',
    plain:true,
    modal:true,
    constrainHeader:true,
	
	listeners:{

	},
	
    items:[
       {
    	   	xtype: 'form',
    	   	frame:true,
    	   	fieldDefaults:{
 	           labelAlign:'right',
 	           labelWidth:100,
 	           msgTarget:'tip',
 	          margin : '1 50 10 0'
 	       	},
    	   	reference:'storInWinForm',
    	   	region:'center',
    	   	layout : 'vbox',
	       	items: [
       	        {
					xtype : 'fieldset',
					collapsible : true,
					layout: {
					   type: 'table',
					   columns: 2
			       	},
			       	flex:5,
			       	scrollable: true,
					items:[
						{
							xtype : 'datefield',
							name : 'inDate',
							reference : 'inDate',
							format:"Y-m-d",
							submitFormat : 'Y-m-d',
							msgTarget:'side',
							allowBlank:false,
							value:new Date(),
							fieldLabel : '入库时间',
						},
						{
							xtype : 'remotecombo',
							fieldLabel : '作业部',
							name : 'plantCode',
							reference : 'plantCode',
							itemId : 'winPlantCodeItemId',
							tableName : 'MES_MD_STOR',
							displayName : 'PLANT_DESC',
							valueName : 'PLANT_CODE',
							keyword : "distinct",
							isOtherDisabledSet:true,
							isCascade:true,//是否为级联的下拉框
							otherComboItemId:'winStorCodeItemId',//需要被级联的下拉框的itemId
							otherComboForeignKey:'PLANT_CODE',//被级联的下拉框和当前下拉框的关联字段
							comboKey:'value',//被级联的下拉框和当前下拉框的关联值
							typeAhead:false,
							editable:false,
							allowBlank:false,
							listeners : {
						        change : function(field, newValue, oldVlue, eOpts) {
						        	if (newValue != null) {
						        		// 给描述赋值
										var selectRecord=field.selection;
					            		var desc=selectRecord.get('key');
					            		Ext.ComponentQuery.query("#winPlantDescItemId")[0].setValue(desc);
						        	}
						        }
							}
						},
						{
							xtype : 'textfield',
							name : 'plantDesc',
							reference : 'plantDesc',
							itemId:'winPlantDescItemId',
							hidden:true,
							fieldLabel : '作业部描述',
						},
						{
							xtype : 'remotecombo',
							fieldLabel : '库存地',
							name : 'storCode',
							reference : 'storCode',
							itemId : 'winStorCodeItemId',
							tableName : 'MES_MD_STOR',
							displayName : 'STOR_DESC',
							valueName : 'STOR_CODE',
							keyword : "distinct",
							isOtherDisabledSet : true,
							isCascade : false,// 是否为级联的下拉框
							typeAhead : false,
							editable : false,
							allowBlank:false,
							listeners : {
						        change : function(field, newValue, oldVlue, eOpts) {
						        	if (newValue != null) {
						        		// 给描述赋值
										var selectRecord=field.selection;
					            		var desc=selectRecord.get('key');
					            		Ext.ComponentQuery.query("#winStorDescItemId")[0].setValue(desc);
						        	}
						        }
							}
						},
						{
							xtype : 'textfield',
							name : 'storDesc',
							reference : 'storDesc',
							itemId:'winStorDescItemId',
							hidden:true,
							fieldLabel : '库存地描述',
							allowBlank:false,
						},
						/*{
							xtype : 'remotecombo',
							fieldLabel : '作业区',
							name : 'scopeCode',
							reference : 'scopeCode',
							itemId : 'scopeCodeItemId',
							tableName : 'VIEW_MD_ORG_STRUCTURE_INFO',
							displayName : 'SCOPE_DESC',
							valueName : 'SCOPE_CODE',
							ffilterName : 'NEED_OUTPUT',
							filterValue : '1',
							keyword : "distinct",
							isOtherDisabledSet : true,
							typeAhead : false,
							editable : false,
							isCascade : false,// 是否为级联的下拉框
						},*/
						/*{
							xtype : 'remotecombo',
							fieldLabel : '成本中心',
							name : 'costCenterCode',
							reference : 'costCenterCode',
							itemId : 'costCenterCodeItemId',
							tableName : 'VIEW_MD_ORG_STRUCTURE_INFO',
							displayName : 'COST_CENTER_DESC',
							valueName : 'COST_CENTER_CODE',
							ffilterName : 'NEED_OUTPUT',
							filterValue : '1',
							keyword : "distinct",
							isOtherDisabledSet : true,
							typeAhead : false,
							editable : false,
							isCascade : false,// 是否为级联的下拉框
						},*/
						 {
							name : 'matNr',
							reference : 'matNr',
							fieldLabel : '物料编码',
							allowBlank:false,
							maxLength : 128,
							itemId : 'matNr',
							reference : 'matNr',
							xtype : 'lookupfield',
							popTarget : 'matLookUpWin',
							triggerEvent : 'dblclick',
							initParameters:{
								'qm.masterCatalogId':'LAOBAO'
							},
							lookupConfirm : function(valueRecord) {
								// 为当期文本框赋值
								this.setValue(valueRecord.get('matNr'));
								var matDescField = this.nextSibling()
								var unitField = this.nextSibling().nextSibling();
								// 为matDesc元素赋值
								matDescField.setValue(valueRecord.get('matDesc'));
								// 为unit元素赋值
								unitField.setValue(valueRecord.get('unit'));
							},
						},
						{
							xtype : 'textfield',
							name : 'matDesc',
							reference : 'matDesc',
							fieldLabel : '物料描述',
							maxLength : 128,
							readOnly : true
						}, 
						{
							xtype : 'textfield',
							name : 'unit',
							reference : 'unit',
							fieldLabel : '单位',
						},
						{
							xtype : 'textfield',
							name : 'amount',
							reference : 'amount',
							fieldLabel : '入库量',
							allowBlank:false,
						},
						{
							fieldLabel:'数据来源',
							xtype:'remotecombo',
							name:'dataSource',
							reference:'dataSource',
							tableName:'PES_REFERENCE_TABLE_DETAIL',
							displayName:'data_1_value',
							valueName:'key_1_value',
							filterName:'reference_table_id',
							filterValue:'LB_DATA_SOURCE',//参数类型管理里面配置的参数类型
//							blankText:'不能为空',
//							allowBlank:false,
							hidden:true,
							editable:false
						},
						{
							fieldLabel:'入库类别',
							xtype:'remotecombo',
							name:'inType',
							reference:'inType',
							tableName:'PES_REFERENCE_TABLE_DETAIL',
							displayName:'data_1_value',
							valueName:'key_1_value',
							filterName:'reference_table_id',
							filterValue:'LB_STOR_TYPE',//参数类型管理里面配置的参数类型
//							blankText:'不能为空',
//							allowBlank:false,
							hidden:true,
							editable:false
						},
						{
							xtype : 'datefield',
							name : 'balTime',
							format:"Y-m-d H:i:s",
							submitFormat : 'Y-m-d H:i:s',
							msgTarget:'side',
							reference : 'balTime',
							fieldLabel : '过账时间',
							hidden:true
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
				    handler:'storInWinSubmit',
				    formBind:true
				}, 
				{
				    text: '取消',
				    handler:'storInWinClose'
				}
	        ]
       	}	
    ]
});

