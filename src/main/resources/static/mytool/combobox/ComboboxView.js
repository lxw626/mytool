Ext.define('combobox.ComboboxView', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.comboboxView',
    layout: 'fit',
    reference: 'comboboxView',
    margin: '0 1 0 1',
    // requires: [
    //     'combobox.ComboboxQueryForm',
    //     'combobox.ComboboxList',
    //     'combobox.ComboboxController',
    //     'combobox.ComboboxWin',
    //     'combobox.ComboboxWinController',
    // ],

    // controller: 'comboboxController',

    dockedItems: [
        {
            // xtype: 'comboboxQueryForm',
            xtype: 'panel',
            title:'<span style="color:orange;" onclick="javascript:'+
            "Ext.create('Ext.window.Window', {\n" +
            "    title: '设置分摊人',\n" +
            "    id: 'sharePersonWin',\n" +
            "    height: 200,\n" +
            "    width: 400,\n" +
            "    items: [{\n" +
            "        xtype: 'fieldset',\n" +
            "        layout: {\n" +
            "            type: 'table',\n" +
            "            columns: 2\n" +
            "        },\n" +
            "        items: [{\n" +
            "            xtype: 'textfield',\n" +
            "            name: 'sharePerson',\n" +
            "            id: 'sharePersonText',\n" +
            "            allowBlank: false,\n" +
            "            fieldLabel: '分摊人',\n" +
            "        },\n" +
            "        {\n" +
            "            xtype: 'button',\n" +
            "            text: '设置分摊人',\n" +
            "            handler: function() {\n" +
            "                var sharePerson = Ext.getCmp('sharePersonText').getValue();\n" +
            "                var queryform = Ext.getCmp('apportionedTasksQueryForm');\n" +
            "                Ext.Ajax.request({\n" +
            "                    method: 'post',\n" +
            "                    url: 'other/apportionedTasks/setSharePerson.action?_dc=' + new Date().getTime(),\n" +
            "                    params: {\n" +
            "                        'sharePerson': sharePerson\n" +
            "                    },\n" +
            "                    success: function(response) {\n" +
            "                        Ext.Msg.show({\n" +
            "                            title: '提示',\n" +
            "                            buttons: Ext.MessageBox.OK,\n" +
            "                            msg: '设置分摊人成功'\n" +
            "                        });\n" +
            "                        var sharePersonWin = Ext.getCmp('sharePersonWin');\n" +
            "                        sharePersonWin.close();\n" +
            "                    },\n" +
            "                    failure: function() {\n" +
            "                        Ext.Msg.show({\n" +
            "                            title: '提示',\n" +
            "                            buttons: Ext.MessageBox.OK,\n" +
            "                            msg: '设置分摊人失败'\n" +
            "                        })\n" +
            "                    }\n" +
            "                });\n" +
            "            }\n" +
            "        }]\n" +
            "    }]\n" +
            "\n" +
            "}).show()"+
            '">当前分摊人</span>',
        }
    ],
    items: [
        {
            xtype: 'container',
            layout: {
                type: 'hbox',//垂直盒布局
                pack: 'start',
                align: 'stretch'
            },
            items: [
                {
                    // xtype: 'comboboxList',
                    xtype: 'panel',
                    flex: 4
                }
            ]
        }
    ]
});
