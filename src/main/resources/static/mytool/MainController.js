/**
 * 主界面Controller
 * 
 */
Ext.define('MainController', {
	extend : 'Ext.app.ViewController',

	alias : 'controller.mainController',
	
	routes : {
		':id' : {
			action : 'handleRoute',// 执行跳转
			before : 'beforeHandleRoute'// 路由跳转前操作
		}
	},

	onMenuItemClick : function(view, record, item, index, event, options){
		if (record) {
            var mainPanel = this.lookupReference('mainPanel')
            var xtype = record.data.resPageUrl;
            var className = xtype.charAt(0).toUpperCase() + xtype.slice(1)
            var packageName = record.data.resPackage;
            var menuName = record.data.resName;
            var components =  mainPanel.query(xtype);
            if(components.length >0){
                mainPanel.setActiveTab(components[0]);
			}else{
                Ext.Loader.setConfig({enabled:true});
                Ext.syncRequire(packageName+"."+className,function(){
                    var newTab ={
                        xtype:xtype,
                        title :  menuName,
                        closable : true,
                    }
                    newTab = mainPanel.add(newTab);
                    mainPanel.setActiveTab(newTab);
				});
			}
		}
	},
	addTabPanel : function(targetPanel,xtype) {
		var newTab = targetPanel.items.findBy(function(tab) {
					return tab.title === menuItem.get('resName');
				});

		if (!newTab) {
            // var tabObject = {
            //     xtype : menuItem.get('resPageUrl'),
            //     closable : true,
            //     title : menuItem.get('resName'),
            //     className : menuItem.get('resPageUrl'),
            //     resParams : menuItem.get('resParams'),
            //     resId : menuItem.get('resId'),
            //     resPackage:menuItem.get('resPackage'),
            //     resSid : menuItem.get('sid')
            // }
            Ext.syncRequire(["menu.MenuView"]);
            var tabObject ={
            	xtype:'menuView',
                title :  menuItem.get('resName'),
                closable : true,
			}
			newTab = targetPanel.add(tabObject);
		}
		targetPanel.setActiveTab(newTab);
	},
	
	onMainRender : function(component, options) {
		var me=this;
		var logoutBtn = me.lookupReference('logout')
        logoutBtn.setHidden(Sgai.config.Runtime.getIsLoginFromPortal());
        var changePasswordBtn = me.lookupReference('changePassword');
        changePasswordBtn.setHidden(Sgai.config.Runtime.getIsLoginFromPortal());
        
		me.appPropertiesInit();//加载系统配置参数
		me.getUserButtonPrivilege(); //加载用户按钮权限
		me.commonTypeItemsInit();//加载公用类型数据
		me.refTableDetailsInit();//加载公用类型数据
		me.systemPropertiesInit();//加载系统配置参数
		me.getUserDefinitionGridColumns();//加载用户自定义表头
	},
	onHeaderPanelRender : function(component, options) {
//		this.topMenuStore.load(this.initTopMenus);
	},
	
	onMenuPanelRender : function(component, options) {
	},
	
	onTreepanelSelect : function(selModel, record, index, options, targetPanel, targetPanelStr) {
		if (record) {
			//Sgai.util.Util.insertPageOperationLog(record.get('resId'));
			this.redirectTo(record.get('resId'));
		}
	},

	onTreepanelItemClick : function(view, record, item, index, event, options) {
		var targetPanel = this.lookupReference('mainPanel');
		this.onTreepanelSelect(view, record, index, options, targetPanel,'mainPanel');
	},
	
	onTreepanelItemRightClick : function(view, record, item, index, event, options) {
		event.stopEvent();
		var me = this;
		var contextmenu = new Ext.menu.Menu({
					itemId : 'theContextMenu',
					items : [{
						xtype : "button",
						text : "收藏",
						iconCls : "add",
						handler : function() {
							me.addToFavorite(me, view, record, item, index,
									event, options);
						}
					}, {
						xtype : "button",
						text : "对比",
						iconCls : "edit",
						handler : function() {
							me.addToEastPanel(me, view, record, item, index,
									event, options);
						}
					}]
				});
		contextmenu.showAt(event.getXY());
	},

	addToFavorite : function(me, view, record, item, index, event, options) {
		var resourceCustomWin = Ext.widget('resourcecustomwin', {
					record : record
				});
		resourceCustomWin.show();
	},
	addToEastPanel : function(me, view, record, item, index, event, options) {
		var targetPanel =  me.lookupReference('eastpanel');
        store = me.lookupReference('menuTree').getStore(),
		menuItem = store.getNodeById(record.get('id'))
		
		me.addTabPanel(targetPanel,menuItem,'eastpanel');
	},
	
    getUserButtonPrivilege:function() {
        Ext.Ajax.request({
            method : 'POST',
            url : (window.localStorage.getItem('appContextPath')==null?'':window.localStorage.getItem('appContextPath')) + 'ac/login/getUserButtonPrivilege.action',
            async : true,
            success : function(response) {
                var text = response.responseText;
                var reText = Ext.decode(response.responseText).items;
                Sgai.config.Runtime.setBtnPrivileges(reText);
            },
            failure : function(response, opts) {
                var reText = response.responseText;
                Ext.MessageBox.show({
                    title : translations.errMsgWinTitle,
                    msg : reText,
                    maxWidth : 360,
                    buttons : Ext.Msg.OK,
                    icon : Ext.MessageBox.ERROR
                });
            }
        });
    },
    
	commonTypeItemsInit:function() {   	
		Ext.Ajax.request({
			method:'POST',
		    url: 'md/md-common-type/getAllCommonTypeItems.action',
		    async: false,
		    params: {},
		    success: function(response){
		        Sgai.config.Runtime.setCommonTypeItems(Ext.decode(response.responseText));
		    }
		});
	},
	
	refTableDetailsInit:function() {   	
		Ext.Ajax.request({
			method:'POST',
		    url: 'md/reference-table-detail/getAllRefTableDetails.action',
		    async: false,
		    params: {},
		    success: function(response){
		        Sgai.config.Runtime.setRefTableDetails(Ext.decode(response.responseText));
		    }
		});
	},
	
	findAllUserInfos:function() {// 查找所有的用户的pin和中文名并缓存
		Ext.Ajax.request({
			method : 'POST',
			url : 'system/user/findAllUserInfos.action',
			async : false,
			success : function(response) {
				var text = Ext.decode(response.responseText);
				Sgai.config.Runtime.setAllUserInfo(text);
			}
		});
	},
	
	
	appPropertiesInit:function() {
        Ext.Ajax.request({
            method:'POST',
            url: (window.localStorage.getItem('appContextPath')==null?'':window.localStorage.getItem('appContextPath')) + 'ac/md/md-common-types/appPropertiesInit.action',
            async: false,
            params: {},
            success: function(response){
                var text = response.responseText;
                appProperties = "[" + text + "]";
                applicationProperties = eval('('+appProperties+')')[0];

            }
        });
    },
    
    getUserDefinitionGridColumns:function() {
        Ext.Ajax.request({
            method:'POST',
            url: 'system/user-grid-columns/read.action',
            async: true,
            params: {},
            success: function(response){
                var text = response.responseText;
                gridHeaders = Ext.decode(text).data;
            }
        });
    },
	
	getAppServerDate:function() {
		Ext.Ajax.request({
			method : 'POST',
			url : 'system/login/getCurrentDate.action',
			async : false,
			success : function(response) {
				appServerDate = response.responseText;
			}
		});
	},
	
	systemPropertiesInit:function() {
    	Ext.Ajax.request({
    		method:'GET',
		    url: 'system/login/systemPropertiesInit.action',
		    async: false, //注意这里是同步调用，异步调用会导致国际化信息初始化未完毕就进行后续界面渲染，导致错误
		    params: {},
		    success: function(response){
		        var text = response.responseText;
				sysProperties = "[" + text + "]";
				systemProperties = eval('('+sysProperties+')')[0];
		    }
		});
    },
	
    onBeforeRender:function() {
    	var me = this;
    	//加载系统用户
    	me.findAllUserInfos();
    	var loginUserTip = me.lookupReference('loginUserTip');
    	var text = '用户:'  + getSystemUserName(Sgai.config.Runtime.getUserName()) + " " ;
    	
    	loginUserTip.setHtml(text);
    	
    	var currentDate = me.lookupReference('currentDate');
    	currentDate.setHtml(Sgai.util.Util.getCurrentDataAndWeekDay());
    	
    	var currentTime = me.lookupReference('currentTime');
		task = new Ext.util.TaskRunner().start({
		    run: function(){
		    	currentTime.setHtml(Ext.Date.format(new Date(), 'G:i:s'));
		    },
		    interval: 1000
		});
	},
	
	onCollapseAllBtnClick: function(button, e, options) {
		var me = this;
		var menuTree = me.lookupReference('menuTree');
		menuTree.collapseAll();
	},
	onExpandAllBtnClick: function(button, e, options) {
		var me = this;
		var menuTree = me.lookupReference('menuTree');
		menuTree.expandAll();
	},
	onFindNodeBtnClick: function(button, e, options) {
		var me = this;
		var menuTree = me.lookupReference('menuTree');
		var nodeText = me.lookupReference('treeNodeText');
		var nodeTextValue = nodeText.value;
		if(!Ext.isEmpty(nodeTextValue)) {
			me.clearSearchResult(menuTree.getRootNode(), nodeTextValue, menuTree);
			menuTree.collapseAll();
			me.childrenSearchRecursively(menuTree.getRootNode(), nodeTextValue, menuTree);
		} else {
			me.clearSearchResult(menuTree.getRootNode(), nodeTextValue, menuTree);
			//menuTree.collapseAll();
		}
	},
    //查找节点并展开
    childrenSearchRecursively:function(node, searchStr, tree) {
    	var me = this;
    	var nodeMatched = false;
		if (node.hasChildNodes()) {	   
			var childnodes = node.childNodes;
			for(var i=0;i<childnodes.length;i++){  
				var childnode = childnodes[i];
				console.log(childnode.data);
				if (childnode.data.resName.indexOf(searchStr)>=0) {
					childnode.data.checked=true;
					nodeMatched = true;
				} else {
					childnode.data.checked = null;
				}
				if(childnode.hasChildNodes()){  					
					me.childrenSearchRecursively(childnode, searchStr, tree);    
				}
			}
			if (nodeMatched) {
				node.expand();
			}
		}
    },
    
    clearSearchResult:function(node, searchStr, tree) {
    	var me = this;
		if (node.hasChildNodes()) {	   
			var childnodes = node.childNodes;
			for(var i=0;i<childnodes.length;i++){  
				var childnode = childnodes[i];
				childnode.data.checked = null;
				if(childnode.hasChildNodes()){  					
					me.clearSearchResult(childnode, searchStr, tree);    
				}
			}
			if (node.isExpanded()) {
				node.collapse();
				node.expand();
			}
		}
    },
    mainTabChange:function(tabPanel, newCard, oldCard, eOpts){
    	var url = "system/operation-page/addBatchFromJson.action";
    	var jsonData="";
    	if(newCard.resId==null || newCard.resId==""){
    		   jsonData={'pageId':'Main' ,'pageName':newCard.title};
    	}else{
    		   jsonData={'pageId':newCard.resId ,'pageName':newCard.title};
    	}
   	  
   	    Ext.Ajax.request({
			method : 'POST',
			url : url,
			jsonData : jsonData,
			params:{},
			async : true});
			
    },
    /**
     * 当点击tab标题改变时，使左侧菜单栏焦点跟随tab改变
     * @param {} tabPanel
     * @param {} newCard
     * @param {} oldCard
     * @param {} eOpts
     * wangfei 2019/04/10
     */
    onMainPanelTabChange : function(tabPanel, newCard, oldCard, eOpts){

    	var me = this;
    	var id = newCard.resSid;
    	if (id) {
			var me = this, 
	        menuTree = me.lookupReference('menuTree'),
	        store = menuTree.getStore();
	        
	        me.recursionExpandMenu(id, store);
	
    	}	
    },
    /**
     * 递归展开主子节点
     * @param {} id
     * @param {} store
     * wangfei 2019/04/10
     */
    recursionExpandMenu : function(id, store) {
    	var me = this;
    	menuItem = store.getNodeById(id);
    	if (menuItem) {
			me.handleRoute(id);
		} else {		
    		var rootNode = menuTree.getRootNode();
			if (rootNode && rootNode.hasChildNodes()) {
				rootNode.expand();
		    	me.recursionExpandMenu(id, store);
				store.load();
			}
		}
    }
});
