/**程序开始执行的位置
 * 相当于window.onload=function(){}
 * */
// alert(window.location.pathname)

    
Ext.onReady(function(){
	/**打开extjs的提示功能*/
	Ext.QuickTips.init();

	Ext.form.Field.prototype.msgTarget = "side";//title,qtip,side 
	/**启动动态加载js*/
	Ext.Loader.setConfig({
		enabled : true
	});
	
	/**开始执行应用程序*/
	Ext.application({
		 name : "core",//命名空间core.view....
		 appFolder:"resource/js/core",
		 autoCreateViewport: true,
		 controllers : ["main.MainController",
		 				"chart.ChartController"]
	});

});
