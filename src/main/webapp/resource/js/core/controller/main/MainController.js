/**主控制器*/
Ext.define("core.controller.main.MainController",{
	extend : "Ext.app.Controller",
	init : function(){
	 this.control({
	 'viewport > desktop':{
	 render:function(me){
	 },
	 afterrender:function(){
//	 console.log(Ext.ComponentQuery.query("viewport accordionchart")+"223");
	 
	 }
	 
	 },
	 ' accordionchart ':{
	 render:function(me){
//		console.log(12);
	 },
	 show:function(){
	    alert(332);
	 }
	 }
	 
	 
	 });
	},
	views : ["deskTools.VideoWindow",
			"deskTools.Notepad",
			"deskTools.AccordionWindow",
			"Viewport"
		
	],
	stores : [],
	model : []
});



