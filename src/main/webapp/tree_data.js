/*{

	list:[
            { id:"1",area_name:"aaa",parent_id:"1",description:"dd", leaf: true },
            { id:"2",area_name:"aaa",parent_id:"1",description:"dd", expanded: false, children: [
                { id:"3",area_name:"aaa",parent_id:"1",description:"dd", leaf: true },
                { id:"4",area_name:"aaa",parent_id:"1",description:"dd", leaf: true}
            ] },
            { id:"5",area_name:"aaa",parent_id:"1",description:"dd", leaf: true }
        ]
}*/

/*{
	"data":[
		{"id":1,"area_name":"国内","parent_id":0,"description":null,"create_time":1288062682000,"name":"国内",leaf: false},
		{"id":2,"area_name":"国外","parent_id":1,"description":null,"create_time":null,"name":"国外",leaf: false}
	],
	"success":"true",
	"totalCount":0
}*/


[
	{"id":1,"area_name":"国内","parent_id":0,"description":null,"create_time":1288062682000,"name":"国内",leaf: false,
		children:[
			{"id":11,"area_name":"nihao","parent_id":1,"description":null,"create_time":null,"name":"国外",leaf: true}
		]
	},
	{"id":2,"area_name":"国外","parent_id":1,"description":null,"create_time":null,"name":"国外",leaf: true}
]