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
