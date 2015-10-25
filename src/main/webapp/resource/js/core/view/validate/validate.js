/**
 * ExtJs 自定义验证器
 */
 //中文按2个字节 英文数字1个字节

function countCharacters(str){
      var totalCount = 0;
      
      for (var i=0; i<str.length; i++) {
          var c = str.charCodeAt(i);
          if ((c >= 0x0001 && c <= 0x007e) || (0xff60<=c && c<=0xff9f)) {
              totalCount++;
          }else {    
              totalCount+=2;
          }
          
      }
      return totalCount;
     
 }

 
 //自定义验证开始
Ext.apply(Ext.form.field.VTypes, {
     //验证名字不超过32个字节
    serverNameLength:  function(v) {
         if(countCharacters(v)>32){return false;}else{return true;}
     },
     serverNameLengthText: '服务器别名长度不能超过32个字节',
     
     //验证ip不重复
    IPAddress:  function(v) {
             
             var flag=false;
             Ext.Ajax.request({
                 url: "serverInfoAction.do?m=validateIp",
                  async:false,//这一项必须加上，否则后台返回true,flag的值也为false
                 params: {ipStr:v},
                 method: "POST",
                 success: function(response) {
                    var text = response.responseText;
                    var responseArray = Ext.JSON.decode(text);
                    
                            if(responseArray.success=='true'){
                            //alert("true");
                                flag = true; //0
                            }
                     
                 }
             });
                 return flag;
         
     },
     IPAddressText: 'ip地址已存在',
     IPAddressMask: /[\d\.]/i,
     
     
     //用于编辑的时候，ip地址验证唯一性，此时要保存原来的值，如果和原来的值相等，也要返回true
     IPAddressUpdateValidate:  function(v) {
             var flag=false;
             
             if(oldIp==v){
                 flag=true;
             }else{
             Ext.Ajax.request({
                 url: "serverInfoAction.do?m=validateIp",
                  async:false,//这一项必须加上，否则后台返回true,flag的值也为false
                 params: {ipStr:v},
                 method: "POST",
                 success: function(response) {
                    var text = response.responseText;
                    var responseArray = Ext.JSON.decode(text);
                    
                            if(responseArray.success=='true'){
                            //alert("true");
                                flag = true; //0
                            }
                     
                 }
             });
             }
                 return flag;
     },
     IPAddressUpdateValidateText: 'ip地址已存在',
        appNameAddValidate:function(val,field)   
                   {  var nametemp;
                                 Ext.Object.each(field, function(key, value, myself) {
     //alert(key + ":" + value);
                                      if (key === 'name') {
                                         nametemp = value; 
                                         return false;// stop the iteration
                                     }
                                 });
                                 var flag=1;
                                 if(countTemp == 1){
                                      flag = 1;
                                 }else{
                                     
                                     var ary=new Array();
                                     
                                     for(var m=0;m<countTemp;m++){
                                          var tem = addForm.getForm().findField('appName'+(m+1)).getValue();
                                          
                                         if( nametemp != addForm.getForm().findField('appName'+(m+1)).getName()){
                                             ary[m]=tem;
                                         }
                                         
                                     }
                                     
                                     for(var i=0;i<ary.length;i++){
                                     
                                         if(ary[i]==val){
                                             flag = 0;
                                             break;
                                         }
                                     } 
                                     
                                     
                                     
                                     

                                     
                                 }
                                 
                                 if(flag == 0){
                                     
                                     return false;
                                 }else{
                                 
                                     return true;
                                 }
                                 
                   }, 
     appNameAddValidateText:'应用名称不可重复',
     
       appNameUpdateValidate:function(val,field)   
                   {  var nametemp;

                              //循环遍历field,取出该对象的name属性，并得到name值 
                                Ext.Object.each(field, function(key, value, myself) {
                                    //alert(key + ":" + value);
                                      if (key === 'name') {
                                         nametemp = value; 
                                         return false;// stop the iteration
                                     } 
                                 });
                                 var flag=1;
                                 if(clickCountUpdate == 1){
                                      flag = 1;
                                 }else{
                                     
                                     var ary=new Array();
                                     
                                     for(var m=0;m<clickCountUpdate;m++){
                                          var tem = updateForm.getForm().findField('appName'+(m+1)).getValue();
                                          
                                         if( nametemp != updateForm.getForm().findField('appName'+(m+1)).getName()){
                                             ary[m]=tem;
                                         }
                                         
                                     }
                                     
                                     for(var i=0;i<ary.length;i++){
                                     
                                         if(ary[i]==val){
                                             flag = 0;
                                             break;
                                         }
                                     } 
                                     
                                     
                                     
                                     

                                     
                                 }
                                 
                                 if(flag == 0){
                                     
                                     return false;
                                 }else{
                                 
                                     return true;
                                 }
                                 
                   }, 
     appNameUpdateValidateText:'应用名称不可重复',
          
      portValidate:function(val,field)   
                    {  var nametemp;
                                 Ext.Object.each(field, function(key, value, myself) {
     //alert(key + ":" + value);
                                      if (key === 'name') {
                                         nametemp = value; 
                                         return false;// stop the iteration
                                     }
                                 });
                                 var flag=1;
                                 if(countTemp == 1){
                                      flag = 1;
                                 }else{
                                     
                                     var ary=new Array();
                                     
                                     for(var m=0;m<countTemp;m++){
                                          var tem = addForm.getForm().findField('appPort'+(m+1)).getValue();
                                          
                                         if( nametemp != addForm.getForm().findField('appPort'+(m+1)).getName()){
                                             ary[m]=tem;
                                         }
                                         
                                     }
                                     
                                     for(var i=0;i<ary.length;i++){
                                     
                                         if(ary[i]==val){
                                             flag = 0;
                                             break;
                                         }
                                     } 
                                     
                                     
                                     
                                     

                                     
                                 }
                                 
                                 if(flag == 0){
                                     
                                     return false;
                                 }else{
                                 
                                     return true;
                                 }
                                 
                   }, 
     portValidateText:'端口不可重复',
     
         portUpdateValidate:function(val,field)   
                          {  var nametemp;
                                 Ext.Object.each(field, function(key, value, myself) {
     //alert(key + ":" + value);
                                      if (key === 'name') {
                                         nametemp = value; 
                                         return false;// stop the iteration
                                     }
                                 });
                                 var flag=1;
                                 if(clickCountUpdate == 1){
                                      flag = 1;
                                 }else{
                                     
                                     var ary=new Array();
                                     
                                     for(var m=0;m<clickCountUpdate;m++){
                                          var tem = updateForm.getForm().findField('appPort'+(m+1)).getValue();
                                          
                                         if( nametemp != updateForm.getForm().findField('appPort'+(m+1)).getName()){
                                             ary[m]=tem;
                                         }
                                         
                                     }
                                     
                                     for(var i=0;i<ary.length;i++){
                                     
                                         if(ary[i]==val){
                                             flag = 0;
                                             break;
                                         }
                                     } 
                                     
                                     
                                     
                                     

                                     
                                 }
                                 
                                 if(flag == 0){
                                     
                                     return false;
                                 }else{
                                 
                                     return true;
                                 }
                                 
                   }, 
     portUpdateValidateText:'端口不可重复'
     
     
 });

 
 Ext.onReady(function() { 
    Ext.data.validations.lengthMessage = "错误的长度"; 
    Ext.apply(Ext.data.validations, { 
        size : function(config, value) { 
            if (value === undefined || value === null) { 
                return false; 
            } 
 
            var min = config.min; 
            var max = config.max; 
            if (min <= value && value <= max) { 
                return true; 
            } else { 
                this.sizeMessage = "正确大小：最小" + min + ", 最大" + max; 
                return false; 
            } 
        }, 
        sizeMessage : 'size大小出现错误' 
    }); 
 
    Ext.define("person", { 
        extend : "Ext.data.Model", 
        fields : [ { 
            name : 'name', 
            type : 'string' 
        }, { 
            name : 'gender', 
            type : 'string' 
        }, { 
            name : 'age', 
            type : 'int' 
        }, { 
            name : 'phone', 
            type : 'string' 
        }, { 
            name : 'email', 
            type : 'auto', 
            defaultValue : true 
        } 
 
        ], 
        validations : [ { 
            type : 'length', 
            field : 'name', 
            min : 2, 
            max : 6 
        }, { 
            type : 'inclusion', 
            field : 'gender', 
            list : [ 'Male', 'Female' ] 
        }, { 
            type : 'size', 
            field : 'age', 
            min : 0, 
            max : 150 
        } ] 
    }); 
 
    var p = Ext.create("person", { 
        name : 'sina.com', 
        age : -20, 
        phone : '15057100000', 
        email : 'aa@sina.com' 
    }); 
    var errors = p.validate(); 
    var errorInfo = []; 
    errors.each(function(o) { 
        errorInfo.push(o.field + ", " + o.message) 
    }); 
 
    alert(errorInfo.join("\n")); 
}); 