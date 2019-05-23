var store = Ext.create('Ext.data.TreeStore', {
    root: {
        expanded: true,
        children: [
            { text: "10.188.26.19", leaf: true },
            { text: "10.188.26.31", expanded: true, children: [
                    { text: "MES_MD_PERSON", leaf: true },
                    { text: "MES_MD_PERSON_MAT_BOOK", leaf: true}
                ] },
            { text: "10.188.26.57", leaf: true }
        ]
    }
});
// var tableNameStore = new Ext.data.Store({
//     fields: ['sid','tableName'],
//     proxy: {
//         type: 'ajax',
//         url: 'http://localhost:8888/getTableNames',
//         reader: {
//             type: 'json',
//             totalProperty: 'totalCount',
//             root: 'data'
//         }
//     },
//     autoLoad: true
// });
Ext.define("TableNamePanel", {
    extend: "Ext.tree.Panel",
    alias:'widget.tableNamePanel',
    // frame: true,
    title: 'Simple Tree',
    store: store,
    rootVisible: false,
});



