/**
 * 
 */
 
 extjs表单验证  
//放在onReady的function（）{}中  
Ext.QuickTips.init(); //为组件提供提示信息功能，form的主要提示信息就是客户端验证的错误信息。  
Ext.form.Field.prototype.msgTarget='side'; //提示的方式，枚举值为  
    qtip-当鼠标移动到控件上面时显示提示  
    title-在浏览器的标题显示，但是测试结果是和qtip一样的  
    under-在控件的底下显示错误提示  
    side-在控件右边显示一个错误图标，鼠标指向图标时显示错误提示. 默认值.  
    id-[element id]错误提示显示在指定id的HTML元件中  
1.一个最简单的例子：空验证  
    //空验证的两个参数  
    1.allowBlank:false//false则不能为空，默认为true  
    2.blankText:string//当为空时的错误提示信息  
    js代码为：  
    var form1 = new Ext.form.FormPanel({  
        width       : 350,  
        renderTo    : "form1",  
        title       : "FormPanel",  
        defaults    : {xtype:"textfield",inputType:"password"},  
        items       : [{  
                id          : "blanktest",  
                fieldLabel  : "不能为空",  
                allowBlank  : false,//不允许为空  
                blankText   : "不能为空"//错误提示信息，默认为This field is required!  
        }]  
    });  
2.用vtype格式进行简单的验证。  
在此举邮件验证的例子，重写上面代码的items配置:  
    items:[{  
        fieldLabel  : "不能为空",  
        vtype       : "email",//email格式验证  
        vtypeText   : "不是有效的邮箱地址",//错误提示信息,默认值我就不说了  
        id          : "blanktest",  
        anchor      : "90%"  
    }  
你可以修改上面的vtype为以下的几种extjs的vtype默认支持的验证：  
//form验证中vtype的默认支持类型  
1.alpha     //只能输入字母，无法输入其他（如数字，特殊符号等）  
2.alphanum  //只能输入字母和数字，无法输入其他  
3.email     //email验证，要求的格式是"langsin@gmail.com"  
4.url       //url格式验证，要求的格式是[url]http://www.langsin.com[/url]  
  
3.高级自定义密码验证  
前面的验证都是extjs已经提供的，我们也可以自定义验证函数。  
//先用Ext.apply方法添加自定义的password验证函数（也可以取其他的名字）  
Ext.apply(Ext.form.VTypes,{  
    password:function(val,field){               //val指这里的文本框值，field指这个文本框组件，大家要明白这个意思  
        if(field.confirmTo){                    //confirmTo是我们自定义的配置参数，一般用来保存另外的组件的id值  
            var pwd=Ext.get(field.confirmTo);   //取得confirmTo的那个id的值  
            return (val==pwd.getValue());  
        }  
        return true;  
    }  
});  
//配置items参数  
items:[  
    {  
        fieldLabel  : "密码",  
        id          : "pass1",  
    },{  
        fieldLabel  : "确认密码",  
        id          : "pass2",  
        vtype       : "password",//自定义的验证类型  
        vtypeText   : "两次密码不一致！",  
        confirmTo   : "pass1",//要比较的另外一个的组件的id  
    }  
]  
4.使用正则表达式验证  
new Ext.form.TextField({  
    fieldLabel  : "姓名",  
    name        : "author_nam",  
    regex       : /[/u4e00-/u9fa5]/,    //正则表达式在/...../之间. [/u4e00-/u9fa5] : 只能输入中文.  
    regexText   : "只能输入中文!",  
    allowBlank  : false                 //此验证依然有效.不许为空.  
 
#####################  
Extjs 常用 vtype 列表  
Ext.form.VTypes["hostnameVal1"]     = /^[a-zA-Z][-.a-zA-Z0-9]{0,254}$/;  
Ext.form.VTypes["hostnameVal2"]     = /^[a-zA-Z]([-a-zA-Z0-9]{0,61}[a-zA-Z0-9]){0,1}([.][a-zA-Z]([-a-zA-Z0-9]{0,61}[a-zA-Z0-9]){0,1}){0,}$/;  
Ext.form.VTypes["ipVal"]            = /^([1-9][0-9]{0,1}|1[013-9][0-9]|12[0-689]|2[01][0-9]|22[0-3])([.]([1-9]{0,1}[0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])){2}[.]([1-9][0-9]{0,1}|1[0-9]{2}|2[0-4][0-9]|25[0-4])$/;  
Ext.form.VTypes["netmaskVal"]       = /^(128|192|224|24[08]|25[245].0.0.0)|(255.(0|128|192|224|24[08]|25[245]).0.0)|(255.255.(0|128|192|224|24[08]|25[245]).0)|(255.255.255.(0|128|192|224|24[08]|252))$/;  
Ext.form.VTypes["portVal"]          = /^(0|[1-9][0-9]{0,3}|[1-5][0-9]{4}|6[0-4][0-9]{3}|65[0-4][0-9]{2}|655[0-2][0-9]|6553[0-5])$/;  
Ext.form.VTypes["multicastVal"]     = /^((22[5-9]|23[0-9])([.](0|[1-9][0-9]{0,1}|1[0-9]{2}|2[0-4][0-9]|25[0-5])){3})|(224[.]([1-9][0-9]{0,1}|1[0-9]{2}|2[0-4][0-9]|25[0-5])([.](0|[1-9][0-9]{0,1}|1[0-9]{2}|2[0-4][0-9]|25[0-5])){2})|(224[.]0[.]([1-9][0-9]{0,1}|1[0-9]{2}|2[0-4][0-9]|25[0-5])([.](0|[1-9][0-9]{0,1}|1[0-9]{2}|2[0-4][0-9]|25[0-5])))$/;  
Ext.form.VTypes["usernameVal"]      = /^[a-zA-Z][-_.a-zA-Z0-9]{0,30}$/;  
Ext.form.VTypes["passwordVal1"]     = /^.{6,31}$/;  
Ext.form.VTypes["passwordVal2"]     = /[^a-zA-Z].*[^a-zA-Z]/;  
Ext.form.VTypes["hostname"]         = function(v){  
    if(!Ext.form.VTypes["hostnameVal1"].test(v)){  
        Ext.form.VTypes["hostnameText"]="Must begin with a letter and not exceed 255 characters"  
        return false;  
    }  
    Ext.form.VTypes["hostnameText"]="L[.L][.L][.L][...] where L begins with a letter, ends with a letter or number, and does not exceed 63 characters";  
    return Ext.form.VTypes["hostnameVal2"].test(v);  
}  
Ext.form.VTypes["hostnameText"]     = "Invalid Hostname"  
Ext.form.VTypes["hostnameMask"]     = /[-.a-zA-Z0-9]/;  
Ext.form.VTypes["ip"]               = function(v){  
    return Ext.form.VTypes["ipVal"].test(v);  
}  
Ext.form.VTypes["ipText"]           = "1.0.0.1 - 223.255.255.254 excluding 127.x.x.x"  
Ext.form.VTypes["ipMask"]           = /[.0-9]/;  
Ext.form.VTypes["netmask"]          = function(v){  
    return Ext.form.VTypes["netmaskVal"].test(v);  
}  
Ext.form.VTypes["netmaskText"]      = "128.0.0.0 - 255.255.255.252"  
Ext.form.VTypes["netmaskMask"]      = /[.0-9]/;  
Ext.form.VTypes["port"]             = function(v){  
    return Ext.form.VTypes["portVal"].test(v);  
}  
Ext.form.VTypes["portText"]         = "0 - 65535"  
Ext.form.VTypes["portMask"]         = /[0-9]/;  
Ext.form.VTypes["multicast"]        = function(v){  
    return Ext.form.VTypes["multicastVal"].test(v);  
}  
Ext.form.VTypes["multicastText"]    = "224.0.1.0 - 239.255.255.255"  
Ext.form.VTypes["multicastMask"]    = /[.0-9]/;  
Ext.form.VTypes["username"]         = function(v){  
    return Ext.form.VTypes["usernameVal"].test(v);  
}  
Ext.form.VTypes["usernameText"]     = "Username must begin with a letter and cannot exceed 255 characters"  
Ext.form.VTypes["usernameMask"]     = /[-_.a-zA-Z0-9]/;  
Ext.form.VTypes["password"]=function(v){  
    if(!Ext.form.VTypes["passwordVal1"].test(v)){  
        Ext.form.VTypes["passwordText"]="Password length must be 6 to 31 characters long";  
        return false;  
    }  
    Ext.form.VTypes["passwordText"]="Password must include atleast 2 numbers or symbols";  
    return Ext.form.VTypes["passwordVal2"].test(v);  
}  
Ext.form.VTypes["passwordText"]     = "Invalid Password"  
Ext.form.VTypes["passwordMask"]     = /./;  