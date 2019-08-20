/**
 * 主界面Controller
 * 
 */
Ext.define('MyTool.view.MainController', {
	extend : 'Ext.app.ViewController',
	alias : 'controller.mainController',
	onMenuItemClick : function(view, record, item, index, event, options){
        var me = this;
        if (record) {
            // me.redirectTo(record.getId(),true);
            var mainPanel = me.lookupReference('mainPanel');
            var menuItem = store.getNodeById(record.getId());
            me.addTabPanel(mainPanel,menuItem);
        }
	},
	addTabPanel : function(mainPanel,menuItem) {
        var xtype = menuItem.data.resPageUrl;
        var menuName = menuItem.data.resName;
        var pageName = menuItem.data.resPackage;
        var className = xtype.charAt(0).toUpperCase() + xtype.slice(1)
        var components =  mainPanel.query(xtype);
        if(components.length >0){
            mainPanel.setActiveTab(components[0]);
        }else{
            Ext.Loader.setConfig({enabled:true});
            Ext.syncRequire(pageName+"."+className,function(){
                var newTab ={
                    xtype:xtype,
                    title :  menuName,
					resSid:menuItem.id,
                    closable : true,
                }
                newTab = mainPanel.add(newTab);
                mainPanel.setActiveTab(newTab);
            });
        }
	},

    /**
     * 当点击tab标题改变时，使左侧菜单栏焦点跟随tab改变
     * @param {} tabPanel
     * @param {} newCard
     * @param {} oldCard
     * @param {} eOpts
     * wangfei 2019/04/10
     */
/*    mainPanelTabChange : function(tabPanel, newCard, oldCard, eOpts){
    	var id = newCard.resSid;
    	if (id) {
			var me = this;
	        var menuTree = me.lookupReference('menuTree');
	        var store = menuTree.getStore();
	        me.recursionExpandMenu(id, store);
    	}
    },*/
    /**
     * 递归展开主子节点
     * @param {} id
     * @param {} store
     * wangfei 2019/04/10
     */
/*    recursionExpandMenu : function(id, store) {
        var me = this;
        var menuItem = store.getNodeById(id);
        if (menuItem) {
            me.redirectTo(id,true);
        } else {
            var rootNode = store.getRootNode();
            if (rootNode && rootNode.hasChildNodes()) {
                rootNode.expand();
                me.recursionExpandMenu(id, store);
                store.load();
            }
        }
    }*/
});
