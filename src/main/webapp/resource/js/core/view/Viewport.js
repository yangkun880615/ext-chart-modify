/*!
 * Ext JS Library 4.0
 * Copyright(c) 2006-2011 Sencha Inc.
 * licensing@sencha.com
 * http://www.sencha.com/license
 */

Ext.define('core.view.Viewport', {
    extend: 'core.view.desk.App',
    alias: 'widget.windowsdeskview',
    requires: [
        'Ext.window.MessageBox',

        'core.view.desk.ShortcutModel',

        'core.view.deskTools.SystemStatus',
        'core.view.deskTools.VideoWindow',
        'core.view.deskTools.GridWindow',
        'core.view.deskTools.TabWindow',
        'core.view.deskTools.AccordionWindow',
        'core.view.deskTools.Notepad',
        'core.view.deskTools.BogusMenuModule',
        'core.view.deskTools.BogusModule',
//        'core.view.deskTools.Blockalanche',
        'core.view.deskTools.Settings'
    ],
        constructor: function (config) {
        var me = this;
        me.addEvents(
            'ready',
            'beforeunload'
        );

        me.mixins.observable.constructor.call(this, config);

        if (Ext.isReady) {
            Ext.Function.defer(me.init, 10, me);
        } else {
            Ext.onReady(me.init, me);
        }
    },
    init: function() {
        // custom logic before getXYZ methods get called...

        this.callParent();

        // now ready...
    },

    getModules : function(){
        return [
//            new core.view.deskTools.VideoWindow(),
//            {xtype:"videoWindow"},
            //new core.view.deskTools.Blockalanche(),
//            new core.view.deskTools.SystemStatus(),
//            new core.view.deskTools.GridWindow(),
//            new core.view.deskTools.TabWindow(),
			Ext.create("core.view.deskTools.AccordionWindow")//,
//            new core.view.deskTools.AccordionWindow()//,
//            new core.view.deskTools.Notepad(),
//            {xtype:"notepad"},
//            new core.view.deskTools.BogusMenuModule(),
//            new core.view.deskTools.BogusModule()
        ];
    },

    getDesktopConfig: function () {
        var me = this, ret = me.callParent();

        return Ext.apply(ret, {
            //cls: 'ux-desktop-black',

            contextMenuItems: [
                { text: 'Change Settings', handler: me.onSettings, scope: me }
            ],

            shortcuts:Ext.create('core.store.deskTools.ShortcutModelStore'), 
//            Ext.create('Ext.data.Store', {
//                model: 'core.view.desk.ShortcutModel',
//                data: [
////                    { name: 'Grid Window', iconCls: 'grid-shortcut', module: 'grid-win' },
//                    { name: '聊天', iconCls: 'accordion-shortcut', module: 'acc-win' }//,
////                    { name: 'Notepad', iconCls: 'notepad-shortcut', module: 'notepad' },
////                    { name: 'System Status', iconCls: 'cpu-shortcut', module: 'systemstatus'}
//                ]
//            }),

            wallpaper: 'wallpapers/Blue-Sencha.jpg',
            wallpaperStretch: false
        });
    },

    // config for the start menu
    getStartConfig : function() {
        var me = this, ret = me.callParent();

        return Ext.apply(ret, {
            title: 'Don Griffin',
            iconCls: 'user',
            height: 300,
            toolConfig: {
                width: 100,
                items: [
                    {
                        text:'Settings',
                        iconCls:'settings',
                        handler: me.onSettings,
                        scope: me
                    },
                    '-',
                    {
                        text:'Logout',
                        iconCls:'logout',
                        handler: me.onLogout,
                        scope: me
                    }
                ]
            }
        });
    },

    getTaskbarConfig: function () {
        var ret = this.callParent();

        return Ext.apply(ret, {
            quickStart: [
                { name: 'Accordion Window', iconCls: 'accordion', module: 'acc-win' }//,
//                { name: 'Grid Window', iconCls: 'icon-grid', module: 'grid-win' }
            ],
            trayItems: [
//                { xtype: 'chartweb', flex: 1 },
                { xtype: 'trayclock', flex: 1 }
                
                
            ]
        });
    },

    onLogout: function () {
        Ext.Msg.confirm('Logout', 'Are you sure you want to logout?');
    },

    onSettings: function () {
        var dlg = new core.view.deskTools.Settings({
            desktop: this.desktop
        });
        dlg.show();
    }
});
