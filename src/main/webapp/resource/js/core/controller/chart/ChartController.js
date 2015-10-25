/**
 * 
 */
 
 /**主控制器*/
Ext.define("core.controller.chart.ChartController",{
	extend : "Ext.app.Controller",
	init : function(){
	 this.control({
	 'accordionchart':{
	 render:function(me){

	 },
	 afterrender:function(){
	 
		 }
	 }
	 
	 });
	},
	views : [
	        "desk.Desktop",
			"deskTools.AccordionWindow"
	],
	stores : [],
	model : ["deskTools.ShortcutModelStore"]
});



