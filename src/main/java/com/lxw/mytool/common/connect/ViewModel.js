Ext.define('<#nameSpace>.<#coreClassName>View', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.<#coreClassName>View',
    layout: 'fit',
    reference: '<#coreClassName>View',
    margin: '0 1 0 1',
    requires: [
        '<#nameSpace>.<#coreClassName>QueryForm',
        '<#nameSpace>.<#coreClassName>List',
        '<#nameSpace>.<#coreClassName>Controller',
        '<#nameSpace>.<#coreClassName>Win',
        '<#nameSpace>.<#coreClassName>WinController',
    ],
    controller: '<#coreClassName>Controller',
    dockedItems: [
        {
            xtype: '<#coreClassName>QueryForm',
        }
    ],
    items: [
        {
            xtype: 'container',
            layout: {
                type: 'hbox',
                pack: 'start',
                align: 'stretch'
            },
            items: [
                {
                    xtype: '<#coreClassName>List',
                    flex: 4
                }
            ]
        }
    ]
});
