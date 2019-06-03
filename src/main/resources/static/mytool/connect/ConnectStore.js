Ext.define('connect.ConnectStore', {
    extend:'Ext.data.Store',
    autoLoad:true,
    pageSize:15,
    fields:[
        {name:'sid', type:'auto'},
        {name:'DBType', type:'string'},
        {name:'connectName', type:'string'},
        {name:'connectAddress', type:'string'},
        {name:'connectPort', type:'string'},
        {name:'userName', type:'string'},
        {name:'password', type:'string'},
    ],
    remoteSort:'true',

    proxy: {
        type:'ajax',
        actionMethods:{
            read:'POST'
        },
        api:
        {
           read:'/connect/findByPage',
            // create:'lb/connect/add.action',
            // update:'lb/connect/update.action',
            // destroy:'lb/connect/delete.action'
        },

        reader:
        {
            type:'json',
            rootProperty:'items',
            totalProperty:'data.totalProperty',
            successProperty:'meta.success',
            messageProperty:'meta.message'
        }
    }
});