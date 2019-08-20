package com.lxw.mytool.model;

public interface QueryFormModel {
    String itemModel = "\t\t{\n" +
            "\t\t\tname : '<#fieldName>',\n" +
            "\t\t\treference : '<#fieldName>',\n" +
            "\t\t\tfieldLabel : '<#fieldLabel>',\n" +
            "\t\t},\n";
    String queryFormModel = "Ext.define('<#nameSpace>.<#coreClassName>QueryForm', {\n" +
            "\textend:'Ext.form.Panel',\n" +
            "\talias:'widget.<#coreClassName>QueryForm',\n" +
            "\tlayout:'fit', \n" +
            "\treference:'<#coreClassName>QueryForm',\n" +
            "\tlayout:{\n" +
            "\t   type:'table',\n" +
            "\t   columns:4\n" +
            "   \t},\n" +
            "   \ttitle:'查询条件',\n" +
            "   \tcollapsible : true,\n" +
            "\tframe:true,\n" +
            "\tdefaults : {\n" +
            "\t\txtype : 'textfield',\n" +
            "\t\tflex:1,\n" +
            "\t\twidth:'100%',\n" +
            "\t\tlabelAlign : 'right'\n" +
            "\t},\n" +
            "\tbodyPadding : 10,\n" +
            "\t\n" +
            "\titems:[\n" +
            "\t\t<#items>\n" +
            "\t\t{\n" +
            "\t\t\txtype:'tbfill',\n" +
            "\t\t\tcolspan : <#tbfillnum>\n" +
            "\t\t },\n" +
            "\t {\n" +
            "\t\txtype:'panel',\n" +
            "\t\tlayout:{\n" +
            "\t\t\ttype : 'hbox',//水平盒布局\n" +
            "\t\t\tpack : 'start',\n" +
            "\t\t\talign : 'stretch'\n" +
            "\t\t},\n" +
            "\t\titems:[\n" +
            "\t\t\t{\n" +
            "\t\t\t\txtype:'tbfill'\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\txtype:'button',\n" +
            "\t\t\t\ttext:'查询',\n" +
            "\t\t\t\tmargin : '0 2 0 0',\n" +
            "\t\t\t\thandler:'<#coreClassName>QueryBtnClick',\n" +
            "\t\t\t\tformBind:true\n" +
            "\t\t\t},{\n" +
            "\t\t\t\txtype:'button',\n" +
            "\t\t\t\ttext:'重置',\n" +
            "\t\t\t\tmargin : '0 0 0 2',\n" +
            "\t\t\t\thandler:'<#coreClassName>ResetBtnClick'\n" +
            "\t\t\t},\n" +
            "\t\t]\n" +
            "\t}\n" +
            "\t\t        \n" +
            "\t]\n" +
            "});\n";
}
