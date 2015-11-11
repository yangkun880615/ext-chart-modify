Ext.define('core.view.deskTools.AccordionWindow', {
    extend: 'core.view.desk.Module',
	alias:"widget.accordionchart",
    requires: [
        'Ext.data.TreeStore',
        'Ext.layout.container.Accordion',
        'Ext.toolbar.Spacer',
        'Ext.tree.Panel'
    ],

    id:'acc-win',

    init : function(){
        this.launcher = {
            text: '霸王聊天',
            iconCls:'accordion'
        };
    },

    createTree : function(){
        var tree = Ext.create('Ext.tree.Panel', {
            id:'im-tree',
            alias:"widget.chartusertree",
            title: '在线用户',
            rootVisible:false,
            lines:false,
            autoScroll:true,
            tools:[{
                type: 'refresh',
                handler: function(c, t) {
                    tree.setLoading(true, tree.body);
                    var root = tree.getRootNode();
                    root.collapseChildren(true, false);
                    Ext.Function.defer(function() { // mimic a server call
                        tree.setLoading(false);
                        root.expand(true, true);
                    }, 1000);
                }
            }],
            store: Ext.create('Ext.data.TreeStore', {
                proxy: {
                    type: 'ajax',
                    url:BASE_PATH+"/user/view-friends",
             		reader:{
            			type:"json"
            		}
                },
                root: {
    	           	expanded:true
    	           }
//                root: {
//                    text:'Online',
//                    expanded: true,
//                    children:[{
//                        text:'我的好友',
//                        expanded:true,
//                        children:[
//                            {id:1, text:'Brian', iconCls:'user', leaf:true },
//                            {id:2, text:'Kevin', iconCls:'user', leaf:true },
//                            {id:3, text:'Mark', iconCls:'user', leaf:true },
//                            {id:4, text:'Matt', iconCls:'user', leaf:true },
//                            {id:5, text:'Michael', iconCls:'user', leaf:true },
//                            {id:6, text:'Mike Jr', iconCls:'user', leaf:true },
//                            {id:7, text:'Mike Sr', iconCls:'user', leaf:true },
//                            {id:8, text:'JR', iconCls:'user', leaf:true },
//                            {id:9, text:'Rich', iconCls:'user', leaf:true },
//                            {id:10,text:'Nige', iconCls:'user', leaf:true },
//                            { id:11,text:'Zac', iconCls:'user', leaf:true }
//                        ]
//                    },{
//                        text:'我的家人',
//                        expanded:true,
//                        children:[
//                            { text:'Kiana', iconCls:'user-girl', leaf:true },
//                            { text:'Aubrey', iconCls:'user-girl', leaf:true },
//                            { text:'Cale', iconCls:'user-kid', leaf:true }
//                        ]
//                    }]
//                }
            }),
             	count:5,
                listeners:{
               	render:function(){             
					
										
               	},
                itemclick:function( me, record, item, index, e, eOpts ) {
                	me.fireEvent("itemdblclick",me,record, item, index, e, eOpts);
                },	
                itemdblclick :function( me, record, item, index, e, eOpts ){
                	if(record.data.iconCls=='user'||record.data.iconCls=='user-girl'||record.data.iconCls=='user-kid'){
                	//弹出聊天对话框
        var chartDialog=Ext.ComponentQuery.query('[name=chat_dialog'+record.data.id+"]")[0];

	        if(!chartDialog){
	        	this.count=this.count+35;
	       
	        var page8_jDialog1_obj = new Ext.window.Window({
			name: 'chat_dialog'+record.data.id,
			x:30+this.count,
			y:50+this.count,
			width: 487,
			height: 465,
			title: '和'+record.data.text+'聊天',
			maximizable: true,
			layout: 'border',
			closeAction: 'hide',
			modal: false,
			items: [
				{
					xtype: 'toolbar',
					name: 'page8_jToolbar1',
					height: 29,
					region: 'north',
					border:false,
					items: [
						{
							xtype: 'button',
							name: 'page8_jButton1',
							width: 89,
							height: 21,
							text: '查看资料',
							icon: 'images/jzoom.png'
						},
						{
							xtype: 'button',
							name: 'page8_jButton2',
							width: 89,
							height: 21,
							text: '加为好友',
							icon: 'images/jgroup.png'
						}
					]
				},
				{
					xtype: 'panel',
					name: 'page8_jExtPanel1',
					border: false,
					split: true,
					region: 'center',
					layout: 'border',
					items: [
						{
							xtype: 'toolbar',
							name: 'page8_jToolbar2',
							height: 29,
							region: 'south',
							items: [
								{
									xtype: 'button',
									name: 'page8_jButton5',
									width: 87,
									height: 21,
									text: '聊天记录',
									icon: 'images/jchart.png'
								},
								{
									xtype: 'button',
									name: 'page8_jButton6',
									width: 87,
									height: 21,
									text: '清除记录',
									icon: 'images/jchatclear.png'
								},
								{
									xtype: 'button',
									name: 'page8_jButton7',
									width: 82,
									height: 21,
									text: '发送文件',
									icon: 'images/jemail_edit.png'
								}
							]
						},
						{
							xtype: 'htmleditor',
							name: 'message_html',
							height: 104,
							region: 'south',
							defaultValue: '',
							hideLabel: true
						},
						{
							xtype: 'form',
							name: 'chartArea'+record.data.id,
							region: 'center',
							hideLabel: true,
							html:'<ul style="margin: 5px auto; padding:0px;display:table;" id="areaTab">'+
							'<li id="chart_message" style="overflow-y:scroll;width:443px;float: left;height: 220px;list-style-type: none;border-right:#0066cc 1px solid;border-top:#0066cc 1px solid;border-left:#0066cc 1px solid;'+
							'border-bottom: #0066cc 1px solid;text-align: left;line-height: 20px;"></li></ul>'
						}
					]
				},
				{
					xtype: 'panel',
					name: 'page8_jExtPanel2',
					height: 31,
					bodyStyle:'background: transparent',
					border: false,
					region: 'south',
					bodyPadding: 5,
					layout: {type:'hbox', align:'middle', pack:'end'},
					listeners:{
						render:function(){ 
									PL.userId=record.data.id;//向用户id 发送消息的
								    PL.joinListen('/message/world');
						}
					},
					items: [
						{
							xtype: 'button',
							name: 'page8_jButton3',
							width: 77,
							height: 22,
							margins:'0 5 0 0',
							text: '发送',
							listeners:{
								click:function(me){
					             	Ext.Ajax.request({
										method : 'POST',
										async: false,
									    url:  "./chart",
									    params: {
									    	
									    	message:Ext.ComponentQuery.query("htmleditor[name=message_html]")[0].getValue(),
									    	userId: record.data.id,
									    	userName:record.data.text
									    },
										success: function(response){
											if(response.responseText==-1)//空信息
												return;
											if(response.responseText == 0){
												if($("#chart_message").html().indexOf("该用户未登陆")<=0){
													//从数据库中去 看看
													$("#chart_message").append("<div class='text-success message'><span class='text-info'>该用户未登陆</div>");
												}
											}
											if(response.responseText == 1){
												
											$("#chart_message").append("<div class='text-success message'><span class='text-info'>【"+PL.userSessionName+"】说：</span>"+Ext.ComponentQuery.query("htmleditor[name=message_html]")[0].getValue()+"</div>");
											//清空文本框的值
											Ext.ComponentQuery.query("htmleditor[name=message_html]")[0].setValue(""); 	
											}
										},
										failure : function(response) {
											
										}
									});
									
								}
							}
						},
						{
							xtype: 'button',
							name: 'page8_jButton4',
							width: 77,
							height: 22,
							text: '关闭'
						}
					]
				}
			]
			}).show();}
			else{
			chartDialog.show();
			}
                	}
                
                }
                }
        });

        return tree;
    },

    createWindow : function(){
        var desktop = this.app.getDesktop();
        var win = desktop.getWindow('acc-win');
        if (!win) {
            win = desktop.createWindow({
                id: 'acc-win',
                title: 'Accordion Window',
                width: 250,
                height: 400,
                iconCls: 'accordion',
                animCollapse: false,
                constrainHeader: true,
                bodyBorder: Ext.themeName !== 'neptune',
                tbar: {
                    xtype: 'toolbar',
                    ui: 'plain',
                    items: [{
                        tooltip:{title:'Rich Tooltips', text:'Let your users know what they can do!'},
                        iconCls:'connect'
                    },
                    '-',
                    {
                        tooltip:'Add a new user',
                        iconCls:'user-add'
                    },
                    ' ',
                    {
                        tooltip:'Remove the selected user',
                        iconCls:'user-delete'
                    }]
                },

                layout: 'accordion',
                border: false,

                items: [
                    this.createTree(),
                    {
                        title: 'Settings',
                        html:'<p>Something useful would be in here.</p>',
                        autoScroll:true
                    },
                    {
                        title: 'Even More Stuff',
                        html : '<p>Something useful would be in here.</p>'
                    },
                    {
                        title: 'My Stuff',
                        html : '<p>Something useful would be in here.</p>'
                    }
                ]
            });
        }

        return win;
    }
});


function chartDialogShow(id,name){
	
   return new Ext.window.Window({
		name: 'chat_dialog'+id,
		x:30,
		y:50,
		width: 487,
		height: 465,
		title: '和'+name+'聊天',
		maximizable: true,
		layout: 'border',
		closeAction: 'hide',
		modal: false,
		items: [
			{
				xtype: 'toolbar',
				name: 'page8_jToolbar1',
				height: 29,
				region: 'north',
				border:false,
				items: [
					{
						xtype: 'button',
						name: 'page8_jButton1',
						width: 89,
						height: 21,
						text: '查看资料',
						icon: 'images/jzoom.png'
					},
					{
						xtype: 'button',
						name: 'page8_jButton2',
						width: 89,
						height: 21,
						text: '加为好友',
						icon: 'images/jgroup.png'
					}
				]
			},
			{
				xtype: 'panel',
				name: 'page8_jExtPanel1',
				border: false,
				split: true,
				region: 'center',
				layout: 'border',
				items: [
					{
						xtype: 'toolbar',
						name: 'page8_jToolbar2',
						height: 29,
						region: 'south',
						items: [
							{
								xtype: 'button',
								name: 'page8_jButton5',
								width: 87,
								height: 21,
								text: '聊天记录',
								icon: 'images/jchart.png'
							},
							{
								xtype: 'button',
								name: 'page8_jButton6',
								width: 87,
								height: 21,
								text: '清除记录',
								icon: 'images/jchatclear.png'
							},
							{
								xtype: 'button',
								name: 'page8_jButton7',
								width: 82,
								height: 21,
								text: '发送文件',
								icon: 'images/jemail_edit.png'
							}
						]
					},
					{
						xtype: 'htmleditor',
						name: 'message_html',
						height: 104,
						region: 'south',
						defaultValue: '',
						hideLabel: true
					},
					{
						xtype: 'form',
						name: 'chartArea'+id,
						region: 'center',
						hideLabel: true,
						html:'<ul style="margin: 5px auto; padding:0px;display:table;" id="areaTab">'+
						'<li id="chart_message" style="overflow-y:scroll;width:443px;float: left;height: 220px;list-style-type: none;border-right:#0066cc 1px solid;border-top:#0066cc 1px solid;border-left:#0066cc 1px solid;'+
						'border-bottom: #0066cc 1px solid;text-align: left;line-height: 20px;"></li></ul>'
					}
				]
			},
			{
				xtype: 'panel',
				name: 'page8_jExtPanel2',
				height: 31,
				bodyStyle:'background: transparent',
				border: false,
				region: 'south',
				bodyPadding: 5,
				layout: {type:'hbox', align:'middle', pack:'end'},
				listeners:{
					render:function(){ 
								PL.userId=id;//向用户id 发送消息的
							    PL.joinListen('/message/world');
					}
				},
				items: [
					{
						xtype: 'button',
						name: 'page8_jButton3',
						width: 77,
						height: 22,
						margins:'0 5 0 0',
						text: '发送',
						listeners:{
							click:function(me){
				             	Ext.Ajax.request({
									method : 'POST',
									async: false,
								    url:  "./chart",
								    params: {
								    	
								    	message:Ext.ComponentQuery.query("htmleditor[name=message_html]")[0].getValue(),
								    	userId: id,
								    	userName:name
								    },
									success: function(response){
										if(response.responseText==-1)//空信息
											return;
										if(response.responseText == 0){
											if($("#chart_message").html().indexOf("该用户未登陆")<=0){
												//从数据库中去 看看
												$("#chart_message").append("<div class='text-success message'><span class='text-info'>该用户未登陆</div>");
											}
										}
										if(response.responseText == 1){
											
										$("#chart_message").append("<div class='text-success message'><span class='text-info'>【"+PL.userSessionName+"】说：</span>"+Ext.ComponentQuery.query("htmleditor[name=message_html]")[0].getValue()+"</div>");
										//清空文本框的值
										Ext.ComponentQuery.query("htmleditor[name=message_html]")[0].setValue(""); 	
										}
									},
									failure : function(response) {
										
									}
								});
								
							}
						}
					},
					{
						xtype: 'button',
						name: 'page8_jButton4',
						width: 77,
						height: 22,
						text: '关闭'
					}
				]
			}
		]
		});
	
}

function onData(event) {
	console.log(event.getEvent()+event.getSubject());
	if(event.getSubject() == "singleChart"){
		  var chartDialog=Ext.ComponentQuery.query('[name=chat_dialog'+event.get("fromId")+']')[0];
	     if(!chartDialog){//判断是否存在聊天的窗体，不存在就弹出聊天窗体
	    	 chartDialogShow(event.get("fromId"),event.get("from")).show();
	        }
	     else{
	    	 if(chartDialog.isHidden()){
	    	  chartDialog.show();
	    	 }
	     }
		$("#chart_message").append("<div class='text-success message'><span class='text-info'>【"+event.get("from")+"】说11：</span>"+event.get("message")+"</div>");
	}//用户上线，并且账号id 不是自己，而且是自己的好友 群发
	if (event.getSubject() == '/user/login'&&(event.get('accountID')!=PL.userSession)&&isMyFrends(event.get('accountID'))){//监听用户上线，然后群发信息给自己的好友说好友上线了
		console.log(event.get('accountID'),PL.userSession); 
		var t=new Ext.ToolTip({
		   		target: 'mainUI',
		   		dismissDelay: 15000 ,
		   		html: PL.userSession+'上线了'
				});
		   console.log(Ext.ComponentQuery.query("trayclock")[0].getXY());
			//通知用户上线
		   t.showAt([Ext.ComponentQuery.query("trayclock")[0].getX(),Ext.ComponentQuery.query("trayclock")[0].getY()-60]);
		}
	

//     离开  
//     PL.leave();  
}

//function noticeFriends (loginId){//建立通信
//	Ext.Ajax.request({
//		method : 'POST',
//		async: false,
//	    url:  "./chart/notice-friends",
//	    params: {
//	    	//登陆的用户
//	    	loginId:loginId
//	    },
//		success: function(response){
//		
//			if(response.responseText == 1){
//			}
//		},
//		failure : function(response) {
//			
//		}
//	});
//}

//是否是好友
//function isMyFrends(loginId){
//	return true;
//	Ext.Ajax.request({
//		method : 'POST',
//		async: false,
//	    url:  "./chart/is-friends",
//	    params: {
//	    	//登陆的用户
//	    	loginId:loginId
//	    },
//		success: function(response){
//		
//			if(response.responseText == 1){
//				return true;
//			}
//			return false
//		},
//		failure : function(response) {
//			return false
//		}
//	});
//}

function onJoinListenAck(event){//用户登陆上线通知
	PL.publish('/user/login', 'name=admin&accountID='+PL.userSession );//accountID 是自己登陆的id
}

// Pushlet Event Callback from Server
function onEvent(event) {
//	console.log(event.getSubject() );

//	console.log("22323");
//	if (eventType == 'join-listen-ack') {//join-listen 发送完毕之后
//		PL.state = PL.STATE_LISTENING;
//		PL.sessionId = event.get('p_id');
//		PL._setStatus('join-listen-ack');
//		PL._doCallback(event, window.onJoinListenAck);
		//通知他的好友好同事 ,某某某登陆了
//	}
//    //如果是登陆的消息 并且不是自己发出的 并且 树中没有这个id的节点
//    if (event.getSubject() == '/user/login' && Ext.getCmp('im-tree').getNodeById(event.get('p_from')) == null) {
//        if(event.get('name') == accountID ){
//        	//如果获得的登录名为当前使用者
//        	new Ext.tree.TreeSorter(Ext.getCmp('im-tree'),{property:"text",dir:"asc"},accountID);
//        	if(Ext.getCmp('im-tree').getNodeById(event.get('name')) != null){
//        		Ext.getCmp('im-tree').getNodeById(event.get('name')).remove();
//        	}
//            //就添加节点
//            var node4add=new Ext.tree.TreeNode({
//                        id : event.get('p_from'),
//                        text : event.get('name'),
//                        iconCls : 'user',
//                        leaf : true,
//                        disabled:false
//                    });
//            //给节点添加一个时间戳属性，以后用来判断是否在线  这个属性会不断刷新  长时间不刷新就说明掉了
//            node4add.aliveTS=event.get('p_time');
//            node4add.accountID=event.get('accountID');
//            Ext.getCmp('im-tree').root.appendChild(node4add);
//            //new Ext.tree.LocaleTreeSorter(Ext.getCmp('im-tree'),{folderSort:true});
//            new Ext.tree.TreeSorter(Ext.getCmp('im-tree'),{property:"text",dir:"asc"},accountID);
//            var t=new Ext.ToolTip({
//        		target: 'mainUI',
//        		dismissDelay: 15000 ,
//        		html: event.get('name')+'['+event.get('accountID')+']上线了'
//    		});
//    		t.showAt([50,80+Ext.getCmp('im-tree').root.indexOf(node4add)*20]);
//            
//            //Ext.get('message').set({value : event.get('name')});
//        }
//        
//    	//添加验证用户登录的JS
//    	//当该用户不在线上，再判断是否属于好友
//		if(Ext.getCmp('im-tree').getNodeById(event.get('name')) != null && event.get('name') != accountID){
//			//alert(event.get('p_from'));
//			var offFriend = Ext.getCmp('im-tree').getNodeById(event.get('name'));
//			offFriend.remove();
//			var onFriend=new Ext.tree.TreeNode({
//                    id : event.get('p_from'),
//                    text : event.get('name'),
//                    iconCls : 'user',
//                    leaf : true,
//                    disabled:false
//                });
//			
//			 //给节点添加一个时间戳属性，以后用来判断是否在线  这个属性会不断刷新  长时间不刷新就说明掉了
//			onFriend.aliveTS=event.get('p_time');
//			onFriend.accountID=event.get('accountID');
//			Ext.getCmp('im-tree').root.appendChild(onFriend);
//            new Ext.tree.TreeSorter(Ext.getCmp('im-tree'),{property:"text",dir:"asc"},accountID);
//            
//            var t=new Ext.ToolTip({
//        		target: 'mainUI',
//        		dismissDelay: 10000 ,
//        		html: event.get('name')+'['+event.get('accountID')+']上线了'
//    		});
//    		t.showAt([50,80+Ext.getCmp('im-tree').root.indexOf(onFriend)*20]);
//			//alert(Ext.getCmp('im-tree').getNodeById(event.get('p_from')).id);
//		}
//    }else if(event.getSubject() == '/user/login' 
//            && Ext.getCmp('im-tree').getNodeById(event.get('p_from')) != null ){
//            //如果是登陆的消息 并且不是自己发出的 并且 树中有这个id的节点  就更新他的在线时间戳
//            Ext.getCmp('im-tree').getNodeById(event.get('p_from')).aliveTS=event.get('p_time');
//    }
//    
//    //如果是聊天的信息   
//    if (event.getSubject() == '/user/chat') {
//        var node = Ext.getCmp('im-tree').getNodeById(event.get('p_from'));
//        if(node==null){
//        	var backId = event.get('p_from');
//        	PL.publish('/user/chat','p_to='+ backId+ '&message='+ "<font color='red'>对方尚未加您为好友，无法收到您的消息</font>");
//        	return false;
//        }
//        //打开聊天窗口
//        showChatWindow(node);
//        var now = new Date();
//        var time = " " + now.getHours()
//                                + ":" + now.getMinutes() + ":"
//                                + now.getSeconds() + "";
//        //解码回车符  消息在发送的时候对回车符进行了替换 现在替换回来 否则在经过服务器的时候回车符会丢失
//        var smessage = event.get('message');
//        var reg = new RegExp(PL.sessionId, "g"); // 创建正则RegExp对象
//        smessage = smessage.replace(reg, '\n');
//        //把新消息添加到历史信息栏
//        var ShowMessage = "<span style='color:blue;font-weight:bold;font-size:14'>"+node.text+"</span><span style='color:blue'>" + '     ' + time + '</span><br/>' + smessage + '<br/><br/>';
//        document.getElementById('cw_hw_' + node.id).innerHTML += ShowMessage;
//        
//        //Ext.get('cw_hw_' + node.id).dom.value += node.text
//          //      + '  ' + time + '   \n' + smessage + '\n\n';
//    }
//    
//    //如果是用户管理（添加或删除模块)
//    if(event.getSubject() == '/user/friend'){
//    	//先处理添加功能
//    	var request_name = event.get("name");
//    	var request_id = event.get("p_from");
//    	var manage_status = event.get("manage_status");
//    	//add_request-请求添加;add_submit-添加请求同意;add_fail-添加请求拒绝
//    	//del_request-申请删除;
//    	if(manage_status == "add_request"){//请求为申请加为好友
//    		if(confirm("用户"+request_name+"申请加您为好友，是否同意请求，并添加它为好友？" )){
//    			//后台添加好友信息
//    			//添加节点
//    			addNewFriendNode(request_id,request_name,event);
//    			//发送消息
//    			PL.publish("/user/friend","p_to=" + request_id+ "&manage_status=add_submit&name="+accountID);
//    		}else{
//    			PL.publish("/user/friend","p_to=" + request_id+ "&manage_status=add_fail&name="+accountID);
//    		}
//    	}
//    	if(manage_status == "add_fail"){//添加好友被拒绝
//    		var failMessage = "添加用户"+request_name+"成为好友失败，对方拒绝了您的请求！";
//    		//alert(failMessage);
//    		Ext.MessageBox.alert("添加失败", failMessage);
//    	}
//    	if(manage_status == "add_submit"){//添加好友成功
//    		//后台添加好友信息
//    		//添加节点
//    		addNewFriendNode(request_id,request_name,event);
//    		//发消息提醒
//    		var successMessage = "添加用户"+request_name+"成为好友成功，对方将出现在你的好友列表中！";
//    		Ext.MessageBox.alert("添加成功", successMessage);
//    		//alert(successMessage);
//    	}
//    	if(manage_status == "del_request"){//对方请求删除好友
//    		var delMesaage = "用户"+request_name+"已将您从好友列表中删除，您是否也将其删除？";
//    		if(confirm(delMesaage)){
//    			//后台删除好友信息
//    			//删除节点
//    			var delNode = Ext.getCmp('im-tree').getNodeById(request_id);
//    			Ext.getCmp('im-tree').root.removeChild(delNode);
//    		}else{
//    		}
//    	}
//    }
}