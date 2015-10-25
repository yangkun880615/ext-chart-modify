<title>..: 中国网络电视台 会议室管理系统 :..</title>
<!-- 兼容IE9浏览器 -->
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8" />
<!-- 自动清除缓存 -->
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate">
<!-- 指定编码 -->
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
 <!--<link rel="stylesheet" type="text/css" href="${rc.contextPath}/common/js/extJs/resources/css/ext-all.css" />-->
<#--<link  rel="stylesheet"  href="${rc.contextPath}/common/js/extJs/resources/css/ext-all-rtl.css"/>-->
<link  rel="stylesheet"  href="${rc.contextPath}/common/js/extJs/shared/css/common.css"/>
<link rel="stylesheet" type="text/css" href="${rc.contextPath}/common/js/extJs/src/ux/css/ItemSelector.css" />
<script type="text/javascript" src="${rc.contextPath}/common/js/jquery/jquery-1.9.0.js"></script>
<#--<script type="text/javascript" src="${rc.contextPath}/common/js/extJs/bootstrap.js" charset="UTF-8"></script>-->
<#--<script type="text/javascript" src="${rc.contextPath}/common/js/extJs/locale/ext-lang-zh_CN.js" charset="UTF-8"></script>-->
<script type="text/javascript">
	//定义全局常量
var BASE_PATH="${rc.contextPath}";
</script>
<link  rel="stylesheet"  href="${rc.contextPath}/common/js/extJs/shared/css/desktop.css"/>
<script type="text/javascript" src="${rc.contextPath}/resource/js/core/view/top-option-toolsbar/include-ext.js" charset="utf-8"></script>
<script type="text/javascript" src="${rc.contextPath}/resource/js/core/view/top-option-toolsbar/options-toolsbar.js" charset="utf-8"></script>
<script type="text/javascript" src="${rc.contextPath}/resource/js/core/view/common/password.js" charset="utf-8"></script>
<script type="text/javascript" src="${rc.contextPath}/common/js/pushlet/ajax-pushlet-client.js"></script>

<script type="text/javascript">

	//定义全局常量
	String.prototype.replaceAll = function(reallyDo, replaceWith, ignoreCase) {  
    if (!RegExp.prototype.isPrototypeOf(reallyDo)) {  
        return this.replace(new RegExp(reallyDo, (ignoreCase ? "gi": "g")), replaceWith);  
   		 } else {  
        return this.replace(reallyDo, replaceWith);  
   		 }
    }
    
    //为String类添加trim函数  
		String.prototype.trim = function() {  
		    var reg = /^\s+(.*?)\s+$/;  
		    return this.replace(reg, "$1");  
		}
    //扩展Array方法 包含
    Array.prototype.contains = function(obj) { 

	  var i = this.length; 
	  while (i--) { 
	    if (this[i] === obj) { 
	      return true; 
	    } 
    if(typeof(obj)=="number"){
   	if (this[i] == obj) {
 	  return true; 
   	 } 
    }
	  } 
	  return false; 
	}
	Array.prototype.indexOf = function(val) {              
    for (var i = 0; i < this.length; i++) {  
        if (this[i] == val) return i;  
    }  
    return -1;  
};
	
	//扩展数组异常元素
	Array.prototype.remove = function(val) {  
   var index = this.indexOf(val);  
    if (index > -1) {  
        this.splice(index, 1);
    }  
}; 
	
	//扩展数组异常元素index
	Array.prototype.removeIndex=function(dx)
　{
　　if(isNaN(dx)||dx>this.length){return false;}
　　for(var i=0,n=0;i<this.length;i++)
　　{
　　　　if(this[i]!=this[dx])
　　　　{
　　　　　　this[n++]=this[i]
　　　　}
　　}
　　this.length-=1
　}
	/**
	 * 校验表单域非空
	 * 
	 * @param	values
	 * 				表单的值
	 * @param	fieldName
	 * 				字段id
	 */
	function validateField(fieldId){
		var v = Ext.getCmp(fieldId).getValue();
		if(typeof(v) == "string"){
			v = v.trim();
		}else if(typeof(v) == "number"){
		}else if(typeof(v) == "boolean"){
		}
		if(v==""){
			return true;
		}else{
			return false;
		}
	}
function getDayLocalTime(nS) {   

    return getDateYMD(new Date(nS).toLocaleString().substr(0,9));
    }

function getDateYMD(date){ 
	var dateStr=date.replace(/年|月/g, "-").replace(/日|下午/g, " ").replace(/上午/g, "0");
	//转换日期 兼容ie 防止出现NAN
	str = dateStr.split('-');
	var dateValue = new Date();
	dateValue.setUTCFullYear(str[0], str[1] - 1, str[2]);
	dateValue.setUTCHours(0, 0, 0, 0);
	if(dateValue=="Invalid Date")//火狐
	dateValue=new Date(dateStr);
	
	var yesterday_milliseconds=dateValue.getTime();     
	var yesterday = new Date();     
	    yesterday.setTime(yesterday_milliseconds);     
	  
	var strYear = yesterday.getFullYear();  
	var strDay = yesterday.getDate();  
	var strMonth = yesterday.getMonth()+1;
	if(strMonth<10)  
	{  
		strMonth="0"+strMonth;  
	}
	if(strDay<10)  
	{  
		strDay="0"+strDay;  
	} 
	datastr = strYear+"-"+strMonth+"-"+strDay;
	return datastr;
  }
	/**
	 * 校验出错后的提示信息
	 * 
	 * @param	fieldName
	 * 				字段id
	 * @param	labelName
	 * 				字段标签
	 * @param	fieldType
	 * 				输入域的类型	"select" or "input"
	 */
	function validateMsg(fieldId,labelName,fieldType){
		Ext.Msg.show({
						title : '错误信息',
						msg : "'<font color=red>"+labelName+"</font>' 不能为空，请"+(fieldType=="select" ? "选择":"填写")+"！",
						buttons : Ext.Msg.OK,
						fn : function(btn) {
							if (btn == 'ok' || btn == 'cancel') {
								Ext.getCmp(fieldId).setValue("");
								Ext.get(fieldId).focus(10);
							}
						},
						icon : Ext.MessageBox.ERROR
					});
	}
	
	/**
	 * 表格字段渲染函数。
	 * 	v==true返回html标签，v=false返回html标签，这里没有定义样式原值返回 
	 * 	如返回:<div class='trueCls'></div>
	 * 	如返回:<div class='falseCls'></div>
	 */
	function trueOrFalseCss(v){
		if(typeof(v)=="string"){
			if(v == "true"){
				v = true;
			}else if(v == "false"){
				v = false;
			}
		}
		return v?"<img src='/js/ext-js/resources/images/custom/true.png' title='是'/>":"<img src='/js/ext-js/resources/images/custom/false.png' title='否'/>";
	}
	
	/**
	 * 表格字段渲染函数：
	 * 启用：1
	 * 禁用：2
	 * 停用：3
	 * 返回各自对应的html标签
	 */
	function statusCss(v){
		switch(v){
			case 1:
				return "<img src='/js/ext-js/resources/images/custom/play.png' title='启用'/>";
			case 2:
				return "<img src='/js/ext-js/resources/images/custom/ban.png' title='禁用'/>";
			case 3:
				return "<img src='/js/ext-js/resources/images/custom/pause.png' title='停用'/>";
			case '1':
				return "<img src='/js/ext-js/resources/images/custom/play.png' title='启用'/>";
			case '2':
				return "<img src='/js/ext-js/resources/images/custom/ban.png' title='禁用'/>";
			case '3':
				return "<img src='/js/ext-js/resources/images/custom/pause.png' title='停用'/>";
			default:
				return "";
		}
	}
	
	/**
	 * 日志信息中任务执行的状态 ：新任务、处理中、警告、失败、完成
	 */
	 function processStatusCss(v){
		switch(v){
			case "done":
				return '<img src="/js/ext-js/resources/images/custom/true.png" title="完成"/>';
			case "new":
				return '<img src="/images/new.gif" title="新任务"/>';
			case "processing":
				return '<img src="/images/process2.gif" title="处理中"/>';
			case "error":
				return '<img src="/js/ext-js/resources/images/custom/false.png" title="失败"/>';
			case "warning":
				return '<img src="/images/check_warning.gif" title="警告"/>';
		}
	}
	
	/**
	 * Ajax请求回调函数：请求状态成功。在这个函数里还要通过json信息来判断请求逻辑是否成功。
	 * 
	 * 注意：返回的json中需要包含成功和失败的信息 success:true 或 success:false
	 */
	function successFn(response){
		var result = Ext.decode(response.responseText);
		if(result.success){
			Ext.Msg.alert(
				'信息',
				result.msg,
				function(){
						var currentGrid = Ext.getCmp('currentGrid');
						currentGrid.getStore().reload();
				}
			);
		}else{
			Ext.Msg.show({
				title : '错误信息',
				msg : result.msg,
				buttons : Ext.Msg.OK,
				icon : Ext.MessageBox.ERROR
			});
		}
	}
	
	
	/**
	 * Ajax请求回调函数：请求状态失败。
	 */
	function failureFn(response){
		Ext.Msg.show({
			title : '错误信息',
			msg : '服务器返回<font color=red>' + response.status
					+ '</font>错误，请与技术中心联系。',
			buttons : Ext.Msg.OK,
			icon : Ext.MessageBox.ERROR
		});
	}
	
	/**
	 *	form表单的取消按钮
	 */
	 function getCancelBtn(winId){
		 return {
				text:'取消',
				handler:function(){
					Ext.getCmp(winId).close();
				}
			};
	}
	/**
	 *  返回上页
	 */
	function backPage() {
		history.go(-1);
	}
	/**
	 * 点击验证码图片，重新生成新的验证码图片
	 */
	function imgChange(){
 		var img = document.getElementById("codeImg");
 		img.src='/imageCode.do?date'+new Date().valueOf();
 	}
	/**
	 * 同步加载数据
	 */
	function getData(url,data){
		data = data || {};
		return $.ajax({
			url:url,
			data:data,
			async:false
		}).responseText;
	}
	/**
	 * 处理提交emptyText的值的问题
	 */
	function getValuesAbandonEmptyText(form){
		var submitValues = form.form.getValues();
		for (var param in submitValues) {  
            if (form.form.findField(param) && form.form.findField(param).emptyText == submitValues[param]) {  
                submitValues[param] = '';  
        	}  
		}
		return submitValues;
	}

			Ext.override(Ext.data.TreeStore, { 
						load: function(options) { 
				
						options = options || {}; 
						options.params = options.params || {}; 


						var me = this, 
						node = options.node || me.tree.getRootNode(), 
						root; 


						// If there is not a node it means the user hasnt defined a rootnode yet. In this case lets just 
						// create one for them. 
					if (!node) { 
						node = me.setRootNode({ 
						expanded: true 
						}); 
						} 


						if (me.clearOnLoad) { 

						node.removeAll(false); 
						} 


						Ext.applyIf(options, { 
						node: node 
						}); 
						options.params[me.nodeParam] = node ? node.getId() : 'root'; 


						if (node) { 
						node.set('loading', true); 
						} 


						return me.callParent([options]); 
						} 
						});
</script>
