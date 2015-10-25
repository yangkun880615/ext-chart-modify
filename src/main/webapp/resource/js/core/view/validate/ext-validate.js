Ext.onReady(function() {
	
	

			Ext.apply(Ext.form.VTypes, {
						//密码验证框
						password : function(val, field) { //val指这里的文本框值，field指这个文本框组件，大家要明白这个意思  
							if (field.confirmTo) { //confirmTo是我们自定义的配置参数，一般用来保存另外的组件的id值  
								var pwd = Ext.get(field.confirmTo); //取得confirmTo的那个id的值  
								return (val == pwd.getValue());
							}
							return true;
						},
						userNameValidateText : '用户名不可重复',
						userNameValidate : function(val, field) {
						 var flag=false;
						 var idValue=Ext.getCmp("userId").getValue();
						Ext.Ajax.request({
							url : BASE_PATH+"/user/check-user-name",
							async: false,
							method : 'POST',
							success : function(response) {
								var result = Ext.decode(response.responseText);
									if(result==0)
										flag= true;//没有重名
									else
										flag= false;
							},
							failure : function(response) {
								flag=false;
							userAction.showErroMessage(response);
							},
							params :{"userName":val,
								      "id":id
								     }
						});
						return flag;
						}
					});

		});