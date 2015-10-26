

Ext.onReady(function() {

	Ext.QuickTips.init();

	Ext.form.Field.prototype.msgTarget = "side";

	// 异步验证提交的方法
	function login() {
		if(loginForm.getForm().isValid()){
			Ext.Ajax.request({
				url : BASE_PATH+"/login/submit-ajax",
				 method : 'POST',
				 async:false,
				 success : function(response) {
					var resultJson = Ext.decode(response.responseText);
					if (resultJson.success) 
					{

						//登陆成功后
						window.location = BASE_PATH+"/login/seccess";
//						PL.joinListen('/pushlet/ping');
						//PL.publish('/chat', 'action', 'enter', 'nick', ALERT_MSG.nick, 'nickName', escape(ALERT_MSG.nickName));
						
					} else {
						Ext.Msg.show({
									title : "错误信息",
									msg : resultJson.msg,
									buttons : Ext.Msg.OK,
									fn : function(btn) {
										if (btn == "ok" || btn == "cancel") {
											Ext.getCmp("userName").setValue("");
											Ext.getCmp("password").setValue("");
											Ext.fly("userName").focus(10);
										}
									},
									icon : Ext.MessageBox.ERROR
								});
					}
				},
				failure : function(response) {
					Ext.Msg.show({
								title : "错误信息",
								msg : "服务器返回<font color=red>" + response.status
										+ "</font>错误，请与技术中心联系。",
								buttons : Ext.Msg.OK,
								icon : Ext.MessageBox.ERROR
							});
				},
				params : loginForm.getForm().getValues()
			});
		}
	}
	
	var loginForm = new Ext.FormPanel({
				id:"loginForm",
				labelWidth : 50,
				bodyStyle : "padding:5px 5px 0",
				border : false,
				width :330,
				 method : 'POST',
				height : 80,
				x : 40,
				y : 8,
				defaults : {
					width : 240
				},
				standardSubmit : true,
				defaultType : "textfield",
				items : [{
							id : "userName",
							fieldLabel : "用户名",
							name : "userName",
							blankText : "请输入用户名",
							allowBlank : false,
							listeners : {
								specialkey : function(field, e) {
									if (e.getKey() == e.ENTER && this.getValue().trim()!="") {
										Ext.fly("password").focus(10);
									}
								}
							}
						}, {
							id : "password",
							fieldLabel : "密&nbsp;&nbsp;&nbsp;码",
							inputType : "password",
							allowBlank : false,
							blankText : "请输入密码",
							// tabTip:"长度1-63的可见字符串",
							name : "password",
							// 监听密码框回车事件
							listeners : {
								specialkey : function(field, e) {
									if (e.getKey() == e.ENTER) {
										login();
									}
								}
							}
						}]
			});

	var btn = new Ext.Button({
				id:"loginBtn",
				iconCls : "unlock",
				x : 10,
				y : 12,
				border : false,
				width : 50,
				height : 50,
				handler : login
			});

	btn.on({
		mouseover: function(){
			this.setIconCls("unlockGreen");
		},
		mouseout: function(){

			this.setIconCls("unlock");
		}
	});
			
	// 将这个Panel渲染到一个剧中显示的DIV中即可实时剧中显示
	// mainPanel.render("panel")
	var win = new Ext.Window({
				title : "登录窗口",
				border : true,
				closable : false,
				resizable : false,
				draggable : false,
				plain:true,
//				items : [mainPanel]
				height : 275,
				width : 460,
				layout : "border",
				items : [{
							border : false,
							region : "north",
							height : 120,
							layout : "fit",
							html : "<img src='"+BASE_PATH+"/resource/images/login/login-bg.png'></img>"
						}, {
							border : false,
							region : "east",
							width : 80,
							layout : "absolute",
							items : [btn]
						}, {
							border : false,
							region : "center",
							layout : "absolute",
							items : [loginForm]
						}, {
							border : false,
							height : 50,
							region : "south",
							layout : "absolute",
							html : "<div align=center style='line-height:20px;margin-top:8px;'>如果您帐号有问题，请与<a href='mailto:opsadmin@chart.cn' style='color: black;text-decoration: none;'>技术中心</a>联系。<br/>powered by 平台开发部</div>"
						}]
			});

//	设置窗口随浏览器大小改变而实时居中显示
	window.onresize = function(){
		win.center();
	}
			
	win.show();
	// 登录界面用户名，初始化完成就获取焦点
	Ext.fly("userName").focus(10);

    var firebugWarning = function (o) {
    
    };

    var hideMask = function (o) {
    /*    Ext.get('loading').remove();
        Ext.fly('loading-mask').animate({
            opacity:0,
            remove:true,
            callback: firebugWarning(o)
        });*/
   	   
    };

    Ext.defer(hideMask, 1000,this,['complete']);
 
});



function onJoinListenAck(event){
     //通知好友上线通知
//	PL.publish('/login/notice', 'name=aaa' +'&accountID=1' );
//	console.log("222");
//	Ext.Ajax.request({
//		  url : BASE_PATH+"/login/notice",
//		 method : 'POST',
//		 async:false,
//		success : function(response) {
			window.location = BASE_PATH+"/login/seccess";
//		},
//		failure : function(response) {
//			Ext.Msg.show({
//						title : "错误信息",
//						msg : "服务器返回<font color=red>" + response.status
//								+ "</font>错误，请与技术中心联系。",
//						buttons : Ext.Msg.OK,
//						icon : Ext.MessageBox.ERROR
//					});
//		},
//		params :{}
//	});
}
