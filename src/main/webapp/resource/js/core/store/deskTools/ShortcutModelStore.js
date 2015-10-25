/**
 * 
 */
 
  Ext.define("core.store.deskTools.ShortcutModelStore", {
  				extend:'Ext.data.Store',
                model: 'core.view.desk.ShortcutModel',
                data: [
//                    { name: 'Grid Window', iconCls: 'grid-shortcut', module: 'grid-win' },
                    { name: '聊天', iconCls: 'accordion-shortcut', module: 'acc-win' }//,
//                    { name: 'Notepad', iconCls: 'notepad-shortcut', module: 'notepad' },
//                    { name: 'System Status', iconCls: 'cpu-shortcut', module: 'systemstatus'}
                ]
            })