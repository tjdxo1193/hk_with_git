AUIGrid.MyStockRenderer=AUIGrid.Class({tagName:"div",cssClass:"aui-grid-renderer-base aui-grid-renderer-custom",data:null,columnData:null,columnIndex:-1,rowIndex:-1,headerText:"",dataField:"",initialized:!1,__childEle:null,__childEle2:null,__childEle3:null,update:function(){var t=this.data;if(t){this.initialized||this.initialize();var e=this.element,i=this.__childEle2,l=Number(t.vars);l>0?(this.__setStyle(e,"color","#D90400"),this.__setStyle(i,"background","url('./assets/ico_up.gif') 0% 50% no-repeat")):l<0?(this.__setStyle(e,"color","#005DDE"),this.__setStyle(i,"background","url('./assets/ico_down.gif') 0% 50% no-repeat")):(this.__setStyle(e,"color","#000000"),this.__setStyle(i,"background","transparent")),this.__displayMyValues()}},destroy:function(t){this.__childEle=null,this.__childEle2=null,this.__childEle3=null,this.$super.destroy(t)},initialize:function(){if(!this.initialized){this.initialized=!0,this.setHeight(this.rowHeight-2);var t=this.element;this.__setStyle(t,"position","relative");var e=this.__childEle=document.createElement("div");e.className="my-child1";var i=this.__childEle2=document.createElement("div");i.className="my-child2";var l=this.__childEle3=document.createElement("div");l.className="my-child3",t.appendChild(e),t.appendChild(i),t.appendChild(l),e=i=l=null}},__displayMyValues:function(){var t,e;this.data&&(t=this.__childEle,e=this.data.close,null!=t.textContent?t.textContent=e:t.innerText=e,t=this.__childEle2,e=this.data.vars+"%",null!=t.textContent?t.textContent=e:t.innerText=e,t=this.__childEle3,e=this.data.gap,null!=t.textContent?t.textContent=e:t.innerText=e),t=null},__setStyle:function(t,e,i){t.style[e]=i}}).extend(AUIGrid.RendererBase);