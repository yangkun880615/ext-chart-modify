/**
 * 
 */
 Ext.onReady(function() {
			//长度验证
			Ext.apply(Ext.form.VTypes, {
			 size : function(val, field) { 
            if (val === undefined || val === null) { 
                return false; 
            } 
            var min = field.minLength; 
            var max = field.maxLength; 
//            if (min <= val && val <= max) { 
//                return true; 
//            } else { 
                this.sizeMessage = "正确大小：最小" + min + ", 最大" + max; 
                return false; 
//            } 
        }
			});

		});