/**  
* Project ：              ext-chart-modify  
* FileName ：           com.chart.cn.util.Entity2TreeInter.java 
* Description：
* @version:     1.0
* Create at ：        2014-9-18 上午11:26:57  
*
* Modification History : 
* Date                    Author       Version         Description
*------------------------------------------------------
* 2014-9-18 上午11:26:57         yk        1.0              
*/  

package com.chart.cn.util;

import java.util.List;

public interface Entity2TreeInter<T> {


	public Integer   getId();
	public String getTreeId();
    public String getTreePId();
    public String getText();
    public boolean getExpanded();
    public String getCls();
    public boolean getLeaf();
    public String gethrefTarget();
    public String getIcon();
    public String getIconcls();
    public String getHtml();
    public List<T> getChildren() ;
    
   // public boolean getChecked(); 

}
  


