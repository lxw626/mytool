package com.lxw.mytool.model;

public interface ViewModel4MyTool {
    String viewModel = "Ext.define('<#nameSpace>.<#coreClassName>View', {\n" +
            "    extend: 'Ext.panel.Panel',\n" +
            "    alias: 'widget.<#coreClassName>View',\n" +
            "    layout: 'fit',\n" +
            "    reference: '<#coreClassName>View',\n" +
            "    margin: '0 1 0 1',\n" +
            "    requires: [\n" +
            "        '<#nameSpace>.<#coreClassName>QueryForm',\n" +
            "        '<#nameSpace>.<#coreClassName>List',\n" +
            "        '<#nameSpace>.<#coreClassName>Controller',\n" +
            "        '<#nameSpace>.<#coreClassName>Win',\n" +
            "        '<#nameSpace>.<#coreClassName>WinController',\n" +
            "    ],\n" +
            "    controller: '<#coreClassName>Controller',\n" +
            "    dockedItems: [\n" +
            "        {\n" +
            "            xtype: '<#coreClassName>QueryForm',\n" +
            "        }\n" +
            "    ],\n" +
            "    items: [\n" +
            "        {\n" +
            "            xtype: 'container',\n" +
            "            layout: {\n" +
            "                type: 'hbox',\n" +
            "                pack: 'start',\n" +
            "                align: 'stretch'\n" +
            "            },\n" +
            "            items: [\n" +
            "                {\n" +
            "                    xtype: '<#coreClassName>List',\n" +
            "                    flex: 4\n" +
            "                }\n" +
            "            ]\n" +
            "        }\n" +
            "    ]\n" +
            "});\n";
}
