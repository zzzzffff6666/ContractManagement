(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-59fd1149"],{"0e31":function(e,a,r){"use strict";r("1539")},1539:function(e,a,r){},"20dd":function(e,a,r){"use strict";r.r(a);var t=function(){var e=this,a=e.$createElement,r=e._self._c||a;return r("div",[r("el-form",{ref:"addcForm",attrs:{model:e.addcForm,rules:e.rules,"label-position":"left","label-width":"100px"}},[r("el-form-item",{attrs:{label:"客户名称:",prop:"name"}},[r("el-input",{model:{value:e.addcForm.name,callback:function(a){e.$set(e.addcForm,"name",a)},expression:"addcForm.name"}})],1),r("el-form-item",{attrs:{label:"电话:",prop:"userPhone"}},[r("el-input",{model:{value:e.addcForm.userName,callback:function(a){e.$set(e.addcForm,"userName",a)},expression:"addcForm.userName"}})],1),r("el-form-item",{attrs:{label:"地址",prop:"addr"}},[r("el-input",{model:{value:e.addcForm.addr,callback:function(a){e.$set(e.addcForm,"addr",a)},expression:"addcForm.addr"}})],1),r("el-form-item",{attrs:{label:"传真",prop:"fax"}},[r("el-input",{model:{value:e.addcForm.fax,callback:function(a){e.$set(e.addcForm,"fax",a)},expression:"addcForm.fax"}})],1),r("el-form-item",{attrs:{label:"邮箱",prop:"email"}},[r("el-input",{model:{value:e.addcForm.email,callback:function(a){e.$set(e.addcForm,"email",a)},expression:"addcForm.email"}})],1),r("el-form-item",{attrs:{label:"银行名称",prop:"bankname"}},[r("el-input",{model:{value:e.addcForm.bankname,callback:function(a){e.$set(e.addcForm,"bankname",a)},expression:"addcForm.bankname"}})],1),r("el-form-item",{attrs:{label:"银行账号",prop:"bankaccount"}},[r("el-input",{model:{value:e.addcForm.bankaccount,callback:function(a){e.$set(e.addcForm,"bankaccount",a)},expression:"addcForm.bankaccount"}})],1),r("el-form-item",{attrs:{label:"* 备注",prop:"mark"}},[r("el-input",{attrs:{type:"textarea",autosize:{minRows:8,maxRows:20}},model:{value:e.addcForm.mark,callback:function(a){e.$set(e.addcForm,"mark",a)},expression:"addcForm.mark"}})],1),r("el-form-item",[r("el-row",[r("el-col",{attrs:{span:15}},[r("el-button",{attrs:{type:"primary"},on:{click:e.postContract}},[e._v("确定")])],1),r("el-col",{attrs:{span:9}},[r("el-button",{attrs:{type:"danger"},on:{click:e.resetContract}},[e._v("重置")])],1)],1)],1)],1)],1)},n=[],o=(r("b0c0"),{name:"addcustomer",data:function(){return{addcForm:{name:"",userPhone:"",addr:"",fax:"",email:"",bankname:"",bankaccount:"",mark:""},nullContract:{name:"",userPhone:"",addr:"",fax:"",email:"",bankname:"",bankaccount:"",mark:""},rules:{name:[{required:!0,message:"请输入合同名称",trigger:"blur"}],userPhone:[{required:!0,message:"请输入客户姓名",trigger:"blur"}],addr:[{required:!0,message:"请输入客户住址",trigger:"blur"}],fax:[{required:!0,message:"请输入客户传真",trigger:"blur"}],email:[{required:!0,message:"请输入客户邮箱",trigger:"blur"}],bankname:[{required:!0,message:"请输入客户银行名称",trigger:"blur"}],bankaccount:[{required:!0,message:"请输入客户银行账户",trigger:"blur"}]}}},methods:{resetContract:function(){var e=this;this.$confirm("此操作将清空当前信息，是否继续？","提示",{confirmButtonText:"确定",cancelButtonText:"取消",type:"warning"}).then((function(){e.addcForm=e.cleanJson(e.addcForm),e.$notify({title:"成功",message:"清空成功！",type:"success"})}))},postContract:function(){var e=this;this.$refs["addcForm"].validate((function(a){a?e.$axios({url:"/customer/add",method:"post",data:{name:e.addcForm.name,userPhone:e.addcForm.userPhone,addr:e.addcForm.addr,fax:e.addcForm.fax,email:e.addcForm.email,bankname:e.addcForm.bankname,bankaccount:e.addcForm.bankaccount,mark:e.addcForm.mark},transformRequest:[function(e){var a="";for(var r in e)a+=encodeURIComponent(r)+"="+encodeURIComponent(e[r])+"&";return a}],headers:{"Content-Type":"application/x-www-form-urlencoded"}}).then((function(e){console.log(e)})):e.$notify({title:"失败",message:"提交失败！",type:"error"})}))},cleanJson:function(e){for(var a in e)e[a]="";return e}}}),c=o,d=(r("0e31"),r("3c28"),r("2877")),m=Object(d["a"])(c,t,n,!1,null,"74034c0d",null);a["default"]=m.exports},"3c28":function(e,a,r){"use strict";r("ebf3")},b0c0:function(e,a,r){var t=r("83ab"),n=r("9bf2").f,o=Function.prototype,c=o.toString,d=/^\s*function ([^ (]*)/,m="name";t&&!(m in o)&&n(o,m,{configurable:!0,get:function(){try{return c.call(this).match(d)[1]}catch(e){return""}}})},ebf3:function(e,a,r){}}]);
//# sourceMappingURL=chunk-59fd1149.0862b325.js.map