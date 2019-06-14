Ext.define('connect.ConnectStore', {
    extend:'Ext.data.Store',
    autoLoad:true,
    pageSize:3,
    fields:[
        {name:'sid', type:'auto'},
        {name:'dbtype', type:'string'},
        {name:'connectname', type:'string'},
        {name:'connectaddress', type:'string'},
        {name:'connectport', type:'string'},
        {name:'username', type:'string'},
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
            rootProperty:'data.items',
            totalProperty:'data.totalProperty',
            successProperty:'meta.success',
            messageProperty:'meta.message'
        }
    }
});