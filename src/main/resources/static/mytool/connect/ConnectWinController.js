Ext.define('connect.ConnectWinController', {
	extend: 'Ext.app.ViewController',
	alias: 'controller.connectWinController',
	
	/**
	 * 提交数据
	 * @param {} button
	 * @param {} evt
	 */
	connectWinSubmit:function(button, evt) {
		var win = this;
		var form = this.lookupReference('connectWinForm');
		Ext.Msg.confirm ('操作提示', '确认提交数据？',
            function (btn)  {
                if (btn == 'no') {
                	 return;
                } else {
					if (win.getView().operate=='add') {						
						var url = "http://localhost:8888/connect/add";
					} else {
						var url = "http://localhost:8888/connect/update";
					}
                    button.disabled = true;
                    form.submit({
                        method:'post',
                        url:url,
                        waitTitle: "提示",
                        waitMsg: '正在提交数据，请稍后 ……',
                        success: function () {
                            var parent = win.getView().ctrl;
                            var parentGrid = parent.lookupReference('connectList');
                            parentGrid.getStore().reload();
                            win.closeView();
                        },
                        failure: function () {}
                    });
                    button.disabled = false;
                }
		   }
      )
	},
	
	/**
	 * 关闭窗口
	 * @param {} btn
	 * @param {} evt
	 */
	connectWinClose:function(btn, evt) {
		this.closeView();
	}
});
