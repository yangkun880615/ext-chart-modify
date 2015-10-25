/**
 * 
 */
// Ext.require([
//
//    'Ext.form.*'
//
//]);
 Ext.apply(Ext.form.VTypes,{  
    password:function(val,field){               //val指这里的文本框值，field指这个文本框组件，大家要明白这个意思  
        if(field.confirmTo){                    //confirmTo是我们自定义的配置参数，一般用来保存另外的组件的id值  
            var pwd=Ext.get(field.confirmTo);   //取得confirmTo的那个id的值  
            return (val==pwd.getValue());  
        }  
        return true;  
    }  
});  