Ext.define('MyTool.util.MyUtil', {
    statics:{
        showErrorMsg : function(text) {
            return Ext.MessageBox.show({
                title : "提示",
                msg : text.replace(/^\r\n$/mg,'<br/>'),
                maxWidth : 800,
                maxHeight : 400,
                buttons : Ext.Msg.OK,
                icon : Ext.MessageBox.ERROR
            });
        },
        /**
         * 分页表单提交
         *
         * @param form
         *            表单控件引用
         * @param grid
         *            表格控件引用(绑定了remote store)
         * @param callbackFn
         *            异步请求的回调函数,不管请求的处理过程是否成功都会被调用
         */
        postPageForm : function(form, grid, callbackFn) {
            var me = this;
            var store = grid.getStore();
            var formFields = form.getForm().getFields();
            var params = {};

            formFields.each(function(field) {
                var fieldValue;
                if (Ext.ComponentQuery.is(field, 'datefield')|| Ext.ComponentQuery.is(field, 'datetimefield')) {
                    // 日期格式通过submitFormat或者显示值来获取字符串
                    if (field.submitFormat) {
                        fieldValue = Ext.Date.format(field.getValue(),
                            field.submitFormat);
                    } else {
                        fieldValue = field.getRawValue();
                    }
                } else {
                    fieldValue = field.getValue();
                }
                params[field.getName()] = (fieldValue != undefined) ? fieldValue : '';
            });

            store.on('beforeload', function(store) {
                Ext.apply(store.proxy.extraParams, params);
            });
            store.loadPage(1, {
                callback : function(records, operation, success) {
                    if (operation.exception) {
                        // me.showErrorMsg(operation.error);
                    }
                    if (callbackFn && Ext.isFunction(callbackFn)) {
                        callbackFn.apply(this, records, operation,
                            success);
                    }
                }
            });
        },
        getFormParams : function(form) {
            var componets = ['textfield', 'combobox', 'numberfield',
                'datefield', 'remotecombo', 'fileuploadfield',
                'commontypecombobox', 'datetimefield', 'checkcombo',
                'remotecheckcombo', 'hiddenfield', 'textareafield',
                'poptextfield', 'textarea', 'custDeptTxtSelector',
                'sgaiDeptTxtSelector', 'sgaiStaffTxtSelector',
                'periodcombobox'];
            var params = {

            };

            form.getForm().getFields().each(function(item, index, length) {
                var itemName = '';
                var itemType = item.getXType();
                if (Ext.Array.contains(componets, item.getXType())) {
                    if (!Ext.isEmpty(item.getName())
                        && item.getName().indexOf("-") < 0) {
                        itemName = item.getName();
                    } else if (!Ext.isEmpty(item.getItemId())) {
                        itemName = item.getItemId();
                    }
                    if (!Ext.isEmpty(itemName)) {
                        if (itemType == 'datefield' && item.submitValue) {
                            var submitFormat = item.submitFormat;
                            if (Ext.isEmpty(submitFormat)) {
                                submitFormat = 'Y-m-d H:i:s';
                            }
                            var itemValue = Ext.Date.format(item.getValue(),
                                submitFormat);
                            if (!Ext.isEmpty(itemValue)) {
                                params[itemName] = itemValue;
                            }
                        } else if (itemType == 'datetimefield') {
                            var submitFormat = item.submitFormat;
                            if (Ext.isEmpty(submitFormat)) {
                                submitFormat = 'Y-m-d H:i:s';
                            }
                            var itemValue = Ext.Date.format(item.getValue(),
                                submitFormat);
                            if (!Ext.isEmpty(itemValue)) {
                                params[itemName] = itemValue;
                            }
                        } else {
                            var itemValue = item.getValue();
                            if (!Ext.isEmpty(itemValue)) {
                                params[itemName] = itemValue;
                            }
                        }
                    }
                }
            });
            console.log(params);
            return params;
        },
        postAjaxRequestByJsonData : function(url, jsonData, isAsync,
                                             successCallbackFn, failedCallbackFn, button) {
            if (!Ext.isEmpty(button)) {
                button.disabled = true;
            }
            debugger
            isAsync = isAsync == null ? false : isAsync;
            Ext.Ajax.request({
                method : 'POST',
                url : url,
                jsonData : jsonData,
                async : isAsync,
                success : function(response) {
                    var reText = response.responseText;
                    if (reText == "")
                        return;
                    var text = Ext.decode(reText);
                    var msg = text.meta.message;
                    Ext.MessageBox.show({
                        title : "提示",
                        msg : msg,
                        maxWidth : 800,
                        maxHeight : 400,
                        buttons : Ext.Msg.OK,
                        icon : Ext.MessageBox.INFO
                    });
                    if (text.meta.success) {
                        if (successCallbackFn != null) {
                            successCallbackFn(response);
                        }
                    } else {
                        if (failedCallbackFn != null) {
                            failedCallbackFn(response);
                        }
                    }
                    if (!Ext.isEmpty(button)) {
                        button.disabled = false;
                    }
                },
                failure : function(response, opts) {
                    var reText = response.responseText;
                    util.Util.showErrorMsg(reText);
                }
            });
        },
        /**
         * 表单提交(lxw)
         * @param url
         * @param form
         * @param successCallbackFn
         * @param failedCallbackFn
         * @param button
         */
        formsubmit : function(url,form,successCallbackFn, failedCallbackFn, button) {
            if (!Ext.isEmpty(button)) {
                button.disabled = true;
            }
            form.submit({
                method:'post',
                url:url,
                waitTitle: "提示",
                waitMsg: '正在提交数据，请稍后 ……',
                success: function (response) {
                    var reText = response.responseText;
                    if (reText == "")
                        return;
                    var text = Ext.decode(reText);
                    var msg = text.meta.message;
                    Ext.MessageBox.show({
                        title : "提示",
                        msg : msg,
                        maxWidth : 800,
                        maxHeight : 400,
                        buttons : Ext.Msg.OK,
                        icon : Ext.MessageBox.INFO
                    });
                    if (text.meta.success) {
                        if (successCallbackFn != null) {
                            successCallbackFn(response);
                        }
                    } else {
                        if (failedCallbackFn != null) {
                            failedCallbackFn(response);
                        }
                    }
                    if (!Ext.isEmpty(button)) {
                        button.disabled = false;
                    }
                },
                failure : function(response, opts) {
                    var reText = response.responseText;
                    util.Util.showErrorMsg(reText);
                }
            });

        },
        showTipMsg : function(text) {
            return Ext.MessageBox.show({
                title : "提示",
                msg : text,
                maxWidth : 800,
                maxHeight : 400,
                buttons : Ext.Msg.OK,
                icon : Ext.MessageBox.INFO
            });
        },
        exportExcel : function(url, grid, params) {
            var columns = grid.columns;
            var exportCols = "";
            var exportColsDesc = "";
            var convertCol = "";

            for (var i = 0; i < columns.length; i++) {
                var column = columns[i];
                if ((column.xtype == 'gridcolumn' || column.xtype == 'datecolumn' || column.xtype == 'numbercolumn') && !column.hidden) {
                    exportCols = exportCols + column.dataIndex + ",";
                    exportColsDesc = exportColsDesc + column.text + ",";
                }
                if (!Ext.isEmpty(column.convertCode)) {
                    convertCol = convertCol + column.dataIndex + ":"
                        + column.convertCode + ",";
                }
            }
            if (exportCols.length > 0)
                exportCols = exportCols.substring(0, exportCols.length - 1);
            if (exportColsDesc.length > 0)
                exportColsDesc = exportColsDesc.substring(0,
                    exportColsDesc.length - 1);
            if (convertCol.length > 0)
                convertCol = convertCol.substring(0, convertCol.length - 1);

            // get 方式提交请求在IE下会存在URL超长问题(最大2048byte)
            // 此处将get方式改为Post方式提交，以解决此问题
            var exportForm = document.createElement("form");
            document.body.appendChild(exportForm);
            exportForm.id = Math.round(new Date().getTime()+Math.random()*10000000000000000);
            exportForm.method = "POST";
            // exportForm.target = "_SELF";
            exportForm.action = url;

            for (var attr in params) {
                var varValue = params[attr];
                var varInput = document.createElement("input");
                varInput.setAttribute('type', 'hidden');
                varInput.setAttribute('name', attr);
                varInput.setAttribute('value', varValue)
                exportForm.appendChild(varInput);
            }

            // 表头列
            var exportColsInput = document.createElement("input");
            exportColsInput.setAttribute('type', 'hidden');
            exportColsInput.setAttribute('name', 'qm.col');
            exportColsInput.setAttribute('value', exportCols)
            exportForm.appendChild(exportColsInput);

            // 表头列描述
            var exportColsDescInput = document.createElement("input");
            exportColsDescInput.setAttribute('type', 'hidden');
            exportColsDescInput.setAttribute('name', 'qm.desc');
            exportColsDescInput.setAttribute('value', exportColsDesc)
            exportForm.appendChild(exportColsDescInput);

            // 列转换
            var convertColInput = document.createElement("input");
            convertColInput.setAttribute('type', 'hidden');
            convertColInput.setAttribute('name', 'qm.convertCol');
            convertColInput.setAttribute('value', convertCol)
            exportForm.appendChild(convertColInput);

            // console.log(exportForm);
            exportForm.submit();
            document.body.removeChild(exportForm);
        },

    },


});