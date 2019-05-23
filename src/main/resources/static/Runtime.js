Ext.define('Runtime', {
    singleton: true,
    config: {
        address:'http://localhost:8888/',
    },
    constructor: function(cfg){
        this.initConfig(cfg);
    }
});