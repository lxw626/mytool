Ext.define('MyTool.view.connect.ExcelUpload', {
    extend: 'Ext.window.Window',
    alias: 'widget.excelupload',
    reference: 'excelupload',
    controller: 'connectController',
    x: 200,
    y: 180,
    width: 550,
    height: 100,
    title: '上传文件',
    autoShow: false,
    closeAction: 'hide',
    autoDestroy: false,
    closable: true,
    modal: true,
    resizable: false,
    layout: 'border',

    items: [{
        xtype: 'form',
        reference: 'uploadExcelForm',
        region: 'north',
        layout: {
            type: 'table',
            columns: 0
        },
        labelAlign: 'right',
        items: [{
            xtype: 'fileuploadfield',
            reference: 'uploadExcel',
            fileUpload: true,
            labelWidth: 95,
            labelAlign: 'right',
            fieldLabel: '文件路径',
            name: 'excelFile',
            width: 400,
            buttonText: '浏览'
        },
            {
                xtype: 'button',
                text: '上传',
                margin: '0 0 0 30',
                iconCls: 'excel',
                formBind: true,
                reference: 'importExcel',
                listeners: {
                    click: 'clickUpload'
                }
            }]
    }]


});
