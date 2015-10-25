//按钮是否显示
//按钮权限
var codekeylist='';

 
	if(getUserName().toLocaleUpperCase()=='ADMIN')
{
	  codekeylist='all';
	
}
else
{
Ext.Ajax.request
({
	url :BASE_PATH + "/user/getCodekeyList",
	method : 'POST',  
	async : false, 
	success : function(response) 
	{
		codekeylist = response.responseText;
    	alert(codekeylist);
	}			
 });
  
}


function getcodeKeyList(){return codekeylist;}


//获取按钮的状态getResult
function hasRes(codekey)
{
	
	
 var codekeylist=getcodeKeyList();
 var flag=true;   

 
 if(codekeylist==']')
 {
    
	 flag=true;
 }
 else if(codekeylist=='all')
 {   
	
	  flag=false;
 }
 else
 {
     
     var arry=eval('(' + codekeylist + ')');
	   for(var i=0;i<arry.length;i++)
	   { 
		     
		    if(arry[i]==codekey)
		    	{
		    	
		    	flag=false;
		    
		    	}  
	   }  
 
 }
 return flag;
}

function getUserName()
{
	var UserName='';
 Ext.Ajax.request
 ({
	url :BASE_PATH + "/user/getCurrentUserName",
	method : 'POST', 
	async : false, 
	success : function(response) 
	{
		UserName = response.responseText;
  	
	}			
 });
return UserName;
}


