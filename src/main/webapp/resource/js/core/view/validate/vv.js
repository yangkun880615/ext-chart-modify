/**
 * 
 */
 
 Ext.onReady(function() {  
  
    //fieldset的验证  
    Ext.QuickTips.init(); //支持tips提示  
    Ext.form.Field.prototype.msgTarget = 'side'; //提示的方式，枚举值为"qtip","title","under","side",id(元素id)  
  
    /* 
    ①例: 
    1.allowBlank:false//false则不能为空，默认为true 
    2.blankText:string//当为空时的错误提示信息 
    */  
    var form1 = new Ext.form.FormPanel({  
        width: 350,  
        frame: true,  
        renderTo: "form1",  
        labelWidth: 80,  
        title: "form1",  
        bodyStyle: "padding:5px 5px 0",  
        defaults: { width: 150, xtype: "textfield" },  
        items: [  
        {  
            fieldLabel: "ID",  
            allowBlank: false, //不允许为空  
            blankText: "不能为空，请填写", //错误提示信息，默认为This field is required!              
            id: "blanktest",  
            anchor: "90%"  
        },  
        {  
            fieldLabel: "密码",  
            allowBlank: false,             //不允许为空  
            blankText: "不能为空，请填写", //错误提示信息，默认为This field is required!  
            minLength: 6,                  //最大位数  
            minLengthText: "不能少于6位",  
            maxLength: 12,                 //最小位数  
            maxLengthText: "不能大于12位",  
            inputType: "password",  
            id: "password",  
            anchor: "90%"  
        }  
        ]  
    });  
  
    /* 
    vtype格式进行简单的验证。 
    //form验证中vtype的默认支持类型 
    1.alpha //只能输入字母，无法输入其他（如数字，特殊符号等） 
    2.alphanum//只能输入字母和数字，无法输入其他 
    3.email//email验证，要求的格式是"langsin@gmail.com" 
    4.url//url格式验证，要求的格式是http://www.*** 
    */  
    //②vtype类型验证  
    var form2 = new Ext.form.FormPanel({  
        width: 350,  
        frame: true,  
        renderTo: "form2",  
        labelWidth: 80,  
        title: "form2",  
        bodyStyle: "padding:5px 5px 0",  
        defaults: { width: 150, xtype: "textfield" },  
        items: [  
            { fieldLabel: "字母",  
                vtype: "alpha", //email格式验证  
                vtypeText: "只能输入字母", //错误提示信息,默认值我就不说了  
                id: "alpha",  
                anchor: "90%"  
            },  
            { fieldLabel: "字母和数字",  
                vtype: "alphanum", //email格式验证  
                vtypeText: "只能输入字母和数字", //错误提示信息,默认值我就不说了  
                id: "alphanum",  
                anchor: "90%"  
            },  
            { fieldLabel: "邮箱地址",  
                vtype: "email", //email格式验证  
                vtypeText: "不是有效的邮箱地址", //错误提示信息,默认值我就不说了  
                id: "email",  
                anchor: "90%"  
            },  
            { fieldLabel: "url地址",  
                vtype: "url", //email格式验证  
                vtypeText: "不是有效的url地址", //错误提示信息,默认值我就不说了  
                id: "url",  
                anchor: "90%"  
            }  
  
            ]  
    });  
  
    //③自定义验证  
    //添加自己定的验证方法，实现密码的二次确认  
  
    /* 
    向Ext添加验证事件 
    其中， 
    ComparePW是vtype要引用的名称， 
    判断函数function的返回值为真或假 
    WarningMsg是默认情况下的提示内容，     
    WarningMsg名称可以自由设定，只要不和ComparePW相同即可 
    如果使用时，在vtypeText定义了内容则WarningMsg内容自动被替换     
    */  
    Ext.apply(Ext.form.VTypes, {  
        //val指这里的文本框值，field指这个文本框组件  
        ComparePW: function(val, field) {  
            //自定义属性  
            if (field.Compareid) {  
                var pwd = Ext.get(field.Compareid); //取得Compareid的那个id的值  
                return (val == pwd.getValue());                  
            }  
            return true;  
        },  
        WarningMsg: "密码不一致！"   
    });  
  
    var form3 = new Ext.form.FormPanel({  
        width: 350,  
        frame: true,  
        renderTo: "form3",  
        labelWidth: 80,  
        title: "form3",  
        bodyStyle: "padding:5px 5px 0",  
        defaults: { width: 150, xtype: "textfield", inputType: "password" },  
        items: [  
            {   
                fieldLabel: "密码",  
                id: "pass1",  
                anchor: "90%"  
            },   
            {  
                fieldLabel: "确认密码",  
                id: "pass2",  
                vtype: "ComparePW", //自定义的验证类型名称  
                vtypeText: "两次密码不一致，,请确认两次输入相同！",  
                Compareid: "pass1", //自定义属性，用于保存第一次输入框的ID  
                anchor: "90%"  
            }  
        ]  
    });  
});  