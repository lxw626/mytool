Ext.define('Sgai.view.lb.storIn.StorInWinController', {
	extend: 'Ext.app.ViewController',
	alias: 'controller.storInWinController',
	
	/**
	 * 提交数据
	 * @param {} button
	 * @param {} evt
	 */
	storInWinSubmit:function(button, evt) {
		var win = this;
		var form = this.lookupReference('storInWinForm');
		var params = Sgai.util.Util.getFormParams(form);
		Ext.Msg.confirm (
			'操作提示',
			'确认提交数据？',
            function (btn)  {
                if (btn == 'no') {
                	 return;
                } else {
					if (win.getView().operate=='add') {						
						params.delFlag=0;
						var url = "lb/storIn/storIn.action";
					} else {
						var url = "lb/storIn/update.action";
					}
					var jsonData = Ext.JSON.encode(params);
					Sgai.util.Util.postAjaxRequestByJsonData(url, jsonData, true, function(){
						var parent = win.getView().ctrl;
						var parentGrid = parent.lookupReference('storInList');						
						parentGrid.getStore().reload();
						win.closeView();
					}, function(){}, button);
                }
		   }
      )
	},
	
	/**
	 * 关闭窗口
	 * @param {} btn
	 * @param {} evt
	 */
	storInWinClose:function(btn, evt) {
		this.closeView();
	}
});
